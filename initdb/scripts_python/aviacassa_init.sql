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
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 100', 'Moscow', 'Kazan', '2025-04-24 21:53:57.404831', 150);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 101', 'Moscow', 'Kazan', '2025-04-24 19:54:57.404831', 200);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 110', 'Saint Petersburg', 'Kazan', '2025-04-24 07:04:57.404831', 200);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 111', 'Saint Petersburg', 'Kazan', '2025-04-24 19:33:57.404831', 200);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 120', 'Moscow', 'Sochi', '2025-04-24 20:43:57.404831', 200);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 121', 'Moscow', 'Sochi', '2025-04-24 17:08:57.404831', 150);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 130', 'Saint Petersburg', 'Sochi', '2025-04-24 17:25:57.404831', 100);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 131', 'Saint Petersburg', 'Sochi', '2025-04-24 20:38:57.404831', 100);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 712', 'Yekaterinburg', 'Ufa', '2025-04-20 11:21:57.405231', 200);
INSERT INTO t_flight (flight_number, departure, destination, departure_time, capacity) VALUES ('SU 252', 'Saint Petersburg', 'Kaliningrad', '2025-05-14 11:21:57.405276', 300);
COMMIT;
