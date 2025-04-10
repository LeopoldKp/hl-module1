
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
        
BEGIN TRANSACTION;
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 644', 'Ufa', 'Moscow', '2025-04-21 06:53:48.154134', 250);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 580', 'Kazan', 'Yekaterinburg', '2025-04-25 06:53:48.154179', 300);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 904', 'Krasnodar', 'Ufa', '2025-05-08 06:53:48.154207', 200);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 612', 'Krasnodar', 'Saint Petersburg', '2025-04-18 06:53:48.154228', 250);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 389', 'Vladivostok', 'Ufa', '2025-04-26 06:53:48.154251', 200);
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Morozov Tatiana', '1000 276962', 'phone: +79957547923, email: morozov.tatiana@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Smirnov Maria', '9182 960363', 'phone: +79747430073, email: smirnov.maria@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Petrov Anna', '5316 432261', 'phone: +79211252533, email: petrov.anna@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Ivanov Tatiana', '9893 270865', 'phone: +79699672687, email: ivanov.tatiana@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Morozov Alexey', '6583 357725', 'phone: +79787912131, email: morozov.alexey@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Morozov Ivan', '8856 135766', 'phone: +79585508688, email: morozov.ivan@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Smirnov Andrey', '1997 304995', 'phone: +79051847281, email: smirnov.andrey@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Ivanov Alexey', '9102 524015', 'phone: +79859200130, email: ivanov.alexey@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Petrov Tatiana', '7057 715035', 'phone: +79809364095, email: petrov.tatiana@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Smirnov Alexey', '1637 923477', 'phone: +79527207670, email: smirnov.alexey@example.com');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (3, 5, 'First', '29C', '2025-04-01 06:53:48.154375');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (2, 5, 'Economy', '17E', '2025-04-04 06:53:48.154391');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (5, 3, 'First', '30D', '2025-03-31 06:53:48.154404');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (1, 6, 'Economy', '26B', '2025-04-04 06:53:48.154417');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (5, 2, 'First', '15D', '2025-04-01 06:53:48.154431');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (2, 8, 'Business', '22C', '2025-03-31 06:53:48.154445');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (3, 7, 'Economy', '1A', '2025-04-01 06:53:48.154458');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (4, 10, 'Economy', '23D', '2025-04-07 06:53:48.154471');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (5, 9, 'Economy', '4A', '2025-04-02 06:53:48.154513');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (3, 4, 'Business', '8F', '2025-04-05 06:53:48.154527');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (2, 8, 'Economy', '6F', '2025-04-08 06:53:48.154541');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (4, 10, 'Economy', '19D', '2025-03-31 06:53:48.154554');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (3, 5, 'Business', '7E', '2025-04-05 06:53:48.154568');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (4, 8, 'First', '30C', '2025-04-07 06:53:48.154580');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (4, 7, 'First', '6C', '2025-03-31 06:53:48.154594');
COMMIT;
