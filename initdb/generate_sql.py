import random
from datetime import datetime, timedelta

def generate_aviacassa_sql(filename, num_flights=10, num_passengers=20, num_bookings=30):
    cities = ["Moscow", "Saint Petersburg", "Kazan", "Novosibirsk", "Yekaterinburg",
              "Sochi", "Vladivostok", "Kaliningrad", "Krasnodar", "Ufa"]

    # Специальные направления для тестирования
    popular_routes = [
        ("Moscow", "Kazan"),
        ("Saint Petersburg", "Kazan"),
        ("Moscow", "Sochi"),
        ("Saint Petersburg", "Sochi")
    ]

    seat_classes = ["Economy", "Business", "First"]
    first_names = ["Ivan", "Petr", "Sergey", "Andrey", "Alexey", "Anna", "Maria", "Elena", "Olga", "Tatiana"]
    last_names = ["Ivanov", "Petrov", "Sidorov", "Smirnov", "Kuznetsov", "Popov", "Volkov", "Fedorov", "Morozov", "Nikolaev"]

    with open(filename, 'w', encoding='utf-8') as f:
        f.write("BEGIN TRANSACTION;\n")
        f.write("""
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
        """)
        f.write("COMMIT;\n")

        f.write("\nBEGIN TRANSACTION;\n")

        # Генерация рейсов - специальные направления
        test_date = datetime.now() + timedelta(days=7)  # Фиксированная дата для тестирования
        for i, (departure, destination) in enumerate(popular_routes):
            for j in range(2):  # 2 рейса на каждое направление
                flight_number = f"SU {100 + i*10 + j}"
                departure_time = test_date.replace(hour=random.randint(6, 23), minute=random.randint(0, 59))
                capacity = random.choice([100, 150, 200])

                f.write(
                    f"INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) "
                    f"VALUES ('{flight_number}', '{departure}', '{destination}', '{departure_time}', {capacity});\n"
                )

        # Генерация случайных рейсов
        for i in range(num_flights - len(popular_routes)*2):
            flight_number = f"SU {random.randint(200, 999)}"
            departure, destination = random.sample(cities, 2)
            departure_time = datetime.now() + timedelta(days=random.randint(1, 30))
            capacity = random.choice([100, 150, 200, 250, 300])

            f.write(
                f"INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) "
                f"VALUES ('{flight_number}', '{departure}', '{destination}', '{departure_time}', {capacity});\n"
            )

        # Остальной код генерации пассажиров и бронирований остается без изменений
        # ... [код генерации пассажиров и бронирований] ...

        f.write("COMMIT;\n")

generate_aviacassa_sql("./scripts_python/aviacassa_init.sql")