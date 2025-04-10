import random
from datetime import datetime, timedelta

def generate_aviacassa_sql(filename, num_flights=5, num_passengers=10, num_bookings=15):
    # Данные для генерации
    cities = ["Moscow", "Saint Petersburg", "Kazan", "Novosibirsk", "Yekaterinburg",
              "Sochi", "Vladivostok", "Kaliningrad", "Krasnodar", "Ufa"]

    seat_classes = ["Economy", "Business", "First"]

    # Генерация случайных имен пассажиров
    first_names = ["Ivan", "Petr", "Sergey", "Andrey", "Alexey", "Anna", "Maria", "Elena", "Olga", "Tatiana"]
    last_names = ["Ivanov", "Petrov", "Sidorov", "Smirnov", "Kuznetsov", "Popov", "Volkov", "Fedorov", "Morozov", "Nikolaev"]

    with open(filename, 'w', encoding='utf-8') as f:
        # Создание таблиц
        f.write("""
        BEGIN TRANSACTION;

        CREATE TABLE t_flight (
            id BIGSERIAL PRIMARY KEY,
            flight_number VARCHAR(20) NOT NULL,
            departure VARCHAR(100) NOT NULL,
            destination VARCHAR(100) NOT NULL,
            departure_time TIMESTAMP NOT NULL,
            capacity INTEGER NOT NULL
        );

        CREATE TABLE t_passenger (
            id BIGSERIAL PRIMARY KEY,
            full_name VARCHAR(255) NOT NULL,
            passport_number VARCHAR(50) NOT NULL UNIQUE,
            contact_info VARCHAR(255)
        );

        CREATE TABLE t_booking (
            id BIGSERIAL PRIMARY KEY,
            flight_id BIGINT NOT NULL REFERENCES t_flight(id) ON DELETE CASCADE,
            passenger_id BIGINT NOT NULL REFERENCES t_passenger(id) ON DELETE CASCADE,
            seat_class VARCHAR(20) NOT NULL,
            seat_number VARCHAR(10) NOT NULL,
            booking_time TIMESTAMP NOT NULL
        );

        COMMIT;
        """)

        # Вставка тестовых данных
        f.write("\nBEGIN TRANSACTION;\n")

        # Генерация рейсов
        for i in range(1, num_flights + 1):
            flight_number = f"SU {random.randint(100, 999)}"
            departure, destination = random.sample(cities, 2)
            departure_time = datetime.now() + timedelta(days=random.randint(1, 30))
            capacity = random.choice([100, 150, 200, 250, 300])

            f.write(
                f"INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) "
                f"VALUES ('{flight_number}', '{departure}', '{destination}', '{departure_time}', {capacity});\n"
            )

        # Генерация пассажиров
        for i in range(1, num_passengers + 1):
            full_name = f"{random.choice(last_names)} {random.choice(first_names)}"
            passport_number = f"{random.randint(1000, 9999)} {random.randint(100000, 999999)}"
            contact_info = f"phone: +7{random.randint(900, 999)}{random.randint(1000000, 9999999)}, email: {full_name.lower().replace(' ', '.')}@example.com"

            f.write(
                f"INSERT INTO t_passenger (full_name, passport_number, contact_info) "
                f"VALUES ('{full_name}', '{passport_number}', '{contact_info}');\n"
            )

        # Генерация бронирований
        for i in range(1, num_bookings + 1):
            flight_id = random.randint(1, num_flights)
            passenger_id = random.randint(1, num_passengers)
            seat_class = random.choice(seat_classes)
            seat_number = f"{random.randint(1, 30)}{random.choice(['A', 'B', 'C', 'D', 'E', 'F'])}"
            booking_time = datetime.now() - timedelta(days=random.randint(1, 10))

            f.write(
                f"INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) "
                f"VALUES ({flight_id}, {passenger_id}, '{seat_class}', '{seat_number}', '{booking_time}');\n"
            )

        f.write("COMMIT;\n")

# Генерация SQL-файла
generate_aviacassa_sql("./scripts_python/aviacassa_init.sql")