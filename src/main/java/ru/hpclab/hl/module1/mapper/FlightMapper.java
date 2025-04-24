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

        FlightDTO dto = new FlightDTO();
        dto.setId(entity.getId());
        dto.setFlightNumber(entity.getFlightNumber());
        dto.setDeparture(entity.getDeparture());
        dto.setDestination(entity.getDestination());
        dto.setDepartureTime(entity.getDepartureTime());
        dto.setCapacity(entity.getCapacity());
        dto.setAvailableSeats(entity.getCapacity()); // Инициализируем availableSeats = capacity
        return dto;
    }
}