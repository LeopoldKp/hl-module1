import requests
from faker import Faker
import random
from datetime import datetime, timedelta
import argparse
import aiohttp
import asyncio

BASE_URL = "http://localhost:8080"
fake = Faker()

def create_passenger():
    passenger_data = {
        "fullName": fake.name(),
        "passportNumber": fake.unique.bothify(text='??#########').upper(),
        "contactInfo": fake.phone_number()
    }
    response = requests.post(f"{BASE_URL}/passengers", json=passenger_data)
    if response.status_code == 200:
        return response.json()
    print(f"Ошибка создания пассажира: {response.status_code} - {response.text}")
    return None

def create_flight():
    cities = ["Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург",
              "Казань", "Сочи", "Владивосток", "Калининград"]

    departure = random.choice(cities)
    destination = random.choice([c for c in cities if c != departure])
    departure_date = fake.date_between(start_date="+1d", end_date="+30d")
    capacity = random.randint(100, 300)

    flight_data = {
        "flightNumber": fake.unique.bothify(text='??###').upper(),
        "departure": departure,
        "destination": destination,
        "departureTime": departure_date.strftime("%Y-%m-%d") + "T00:00:00",
        "capacity": capacity,
        "availableSeats": capacity
    }

    response = requests.post(
        f"{BASE_URL}/flights",
        json=flight_data,
        headers={'Content-Type': 'application/json'}
    )

    if response.status_code == 200:
        flight = response.json()
        print(f"Создан рейс: {flight['flightNumber']} {flight['departure']}-{flight['destination']} на {flight['departureTime']}")
        return flight
    print(f"Ошибка создания рейса: {response.status_code} - {response.text}")
    return None

async def create_booking(session, flight_id, passenger_id):
    seat_classes = ["ECONOMY", "BUSINESS", "FIRST"]
    booking_data = {
        "flightId": flight_id,
        "passengerId": passenger_id,
        "seatClass": random.choice(seat_classes),
        "seatNumber": fake.bothify(text='?##').upper(),
        "bookingTime": datetime.now().strftime("%Y-%m-%dT%H:%M:%S")
    }

    async with session.post(
            f"{BASE_URL}/bookings",
            json=booking_data,
            headers={'Content-Type': 'application/json'}
    ) as response:
        if response.status == 200:
            return await response.json()
        print(f"Ошибка создания бронирования: {response.status} - {await response.text()}")
        return None

async def generate_data(passengers_count, flights_count, bookings_count):
    # Создаем пассажиров
    passengers = []
    for _ in range(passengers_count):
        passenger = create_passenger()
        if passenger:
            passengers.append(passenger)
            print(f"Создан пассажир: {passenger['fullName']}")

    # Создаем рейсы
    flights = []
    for _ in range(flights_count):
        flight = create_flight()
        if flight:
            flights.append(flight)

    # Создаем бронирования асинхронно
    async with aiohttp.ClientSession() as session:
        tasks = []
        for _ in range(bookings_count):
            if not passengers or not flights:
                print("Недостаточно данных для создания бронирований")
                break

            passenger = random.choice(passengers)
            flight = random.choice(flights)
            tasks.append(create_booking(session, flight['id'], passenger['id']))

        results = await asyncio.gather(*tasks)
        successful = sum(1 for r in results if r is not None)
        print(f"\nУспешно создано бронирований: {successful}/{bookings_count}")

def main():
    parser = argparse.ArgumentParser(description='Генерация тестовых данных для авиакассы')
    parser.add_argument('--passengers', type=int, default=10, help='Количество пассажиров')
    parser.add_argument('--flights', type=int, default=5, help='Количество рейсов')
    parser.add_argument('--bookings', type=int, default=20, help='Количество бронирований')

    args = parser.parse_args()
    asyncio.run(generate_data(args.passengers, args.flights, args.bookings))
    print("\nГенерация данных завершена.")

if __name__ == "__main__":
    main()