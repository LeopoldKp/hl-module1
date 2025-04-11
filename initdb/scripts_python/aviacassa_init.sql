
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
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 714', 'Ufa', 'Moscow', '2025-05-06 08:02:13.085096', 300);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 802', 'Yekaterinburg', 'Kazan', '2025-04-16 08:02:13.085183', 250);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 303', 'Novosibirsk', 'Moscow', '2025-04-22 08:02:13.085237', 100);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 206', 'Ufa', 'Yekaterinburg', '2025-04-20 08:02:13.085294', 200);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 667', 'Ufa', 'Krasnodar', '2025-04-28 08:02:13.085339', 300);
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Petrov Maria', '5376 388038', 'phone: +79009829592, email: petrov.maria@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Smirnov Tatiana', '1266 475314', 'phone: +79695283002, email: smirnov.tatiana@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Smirnov Sergey', '5440 273780', 'phone: +79447645593, email: smirnov.sergey@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Petrov Maria', '3984 999069', 'phone: +79691719608, email: petrov.maria@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Nikolaev Elena', '7024 550383', 'phone: +79699390922, email: nikolaev.elena@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Kuznetsov Petr', '4091 138447', 'phone: +79577531323, email: kuznetsov.petr@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Morozov Tatiana', '6385 885931', 'phone: +79215974670, email: morozov.tatiana@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Kuznetsov Ivan', '4994 925673', 'phone: +79466631426, email: kuznetsov.ivan@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Petrov Petr', '2020 956905', 'phone: +79469456079, email: petrov.petr@example.com');
INSERT INTO t_passenger (full_name, passport_number, contact_info) VALUES ('Fedorov Tatiana', '1479 176817', 'phone: +79001135267, email: fedorov.tatiana@example.com');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (1, 8, 'Economy', '7F', '2025-04-05 08:02:13.085554');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (5, 7, 'Economy', '4A', '2025-04-04 08:02:13.085583');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (1, 4, 'Business', '26E', '2025-04-10 08:02:13.085603');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (5, 5, 'First', '12C', '2025-04-10 08:02:13.085621');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (1, 3, 'Business', '2E', '2025-04-03 08:02:13.085639');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (3, 8, 'Business', '2A', '2025-04-10 08:02:13.085656');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (5, 10, 'First', '6E', '2025-04-04 08:02:13.085672');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (4, 9, 'Business', '29D', '2025-04-07 08:02:13.085689');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (2, 4, 'Economy', '28B', '2025-04-10 08:02:13.085708');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (3, 2, 'Business', '15E', '2025-04-04 08:02:13.085725');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (2, 3, 'First', '24F', '2025-04-02 08:02:13.085742');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (2, 6, 'Business', '12F', '2025-04-06 08:02:13.085761');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (2, 4, 'Business', '26E', '2025-04-10 08:02:13.085780');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (5, 1, 'Business', '20E', '2025-04-01 08:02:13.085796');
INSERT INTO t_booking (flight_id, passenger_id, seat_class, seat_number, booking_time) VALUES (1, 10, 'First', '16F', '2025-04-08 08:02:13.085811');
COMMIT;
