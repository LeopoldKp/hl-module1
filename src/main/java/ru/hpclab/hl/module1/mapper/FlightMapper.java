package ru.hpclab.hl.module1.mapper;

import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.entity.FlightEntity;

public class FlightMapper {

    private FlightMapper() {
    }

    public static FlightEntity toEntity(FlightDTO dto) {
        if (dto == null) {
            return null;
        }

        FlightEntity entity = new FlightEntity();
        entity.setId(dto.getId());
        entity.setFlightNumber(dto.getFlightNumber());
        entity.setDeparture(dto.getDeparture());
        entity.setDestination(dto.getDestination());
        entity.setDepartureTime(dto.getDepartureTime());
        entity.setCapacity(dto.getCapacity());
        return entity;
    }

    public static FlightDTO toDTO(FlightEntity entity) {
        if (entity == null) {
            return null;
        }

        return new FlightDTO(
                entity.getId(),
                entity.getFlightNumber(),
                entity.getDeparture(),
                entity.getDestination(),
                entity.getDepartureTime(),
                entity.getCapacity(),
                0 // availableSeats будет установлен в сервисе
        );
    }
}