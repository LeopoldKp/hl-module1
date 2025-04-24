-- Создание таблицы пассажиров
CREATE TABLE t_passenger (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    passport_number VARCHAR(255) NOT NULL UNIQUE,
    contact_info VARCHAR(255)
);

-- Создание таблицы рейсов (с полем departure_date вместо departure_time)
CREATE TABLE t_flight (
    id BIGSERIAL PRIMARY KEY,
    flight_number VARCHAR(255) NOT NULL,
    departure VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    departure_date DATE NOT NULL,  -- Изменено на DATE
    capacity INTEGER NOT NULL
);

-- Создание таблицы бронирований
CREATE TABLE t_booking (
    id BIGSERIAL PRIMARY KEY,
    booking_time TIMESTAMP NOT NULL,
    seat_class VARCHAR(255) NOT NULL,
    seat_number VARCHAR(255) NOT NULL,
    flight_id BIGINT NOT NULL REFERENCES t_flight(id) ON DELETE CASCADE,
    passenger_id BIGINT NOT NULL REFERENCES t_passenger(id) ON DELETE CASCADE
);