package ru.hpclab.hl.module1.mapper;

import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.entity.BookingEntity;
import ru.hpclab.hl.module1.entity.FlightEntity;
import ru.hpclab.hl.module1.entity.PassengerEntity;

public class BookingMapper {

    private BookingMapper() {
    }

    public static BookingEntity toEntity(BookingDTO dto, FlightEntity flight, PassengerEntity passenger) {
        if (dto == null) {
            return null;
        }

        BookingEntity entity = new BookingEntity();
        entity.setId(dto.getId());
        entity.setFlight(flight);
        entity.setPassenger(passenger);
        entity.setSeatClass(dto.getSeatClass());
        entity.setSeatNumber(dto.getSeatNumber());
        entity.setBookingTime(dto.getBookingTime());
        return entity;
    }

    public static BookingDTO toDTO(BookingEntity entity) {
        if (entity == null) {
            return null;
        }

        return new BookingDTO(
                entity.getId(),
                entity.getFlight().getId(),
                entity.getPassenger().getId(),
                entity.getSeatClass(),
                entity.getSeatNumber(),
                entity.getBookingTime()
        );
    }
}