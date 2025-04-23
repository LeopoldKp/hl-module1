package ru.hpclab.hl.module1.mapper;

import ru.hpclab.hl.module1.dto.PassengerDTO;
import ru.hpclab.hl.module1.entity.PassengerEntity;

public class PassengerMapper {

    private PassengerMapper() {
    }

    public static PassengerEntity toEntity(PassengerDTO dto) {
        if (dto == null) {
            return null;
        }

        PassengerEntity entity = new PassengerEntity();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setContactInfo(dto.getContactInfo());
        return entity;
    }

    public static PassengerDTO toDTO(PassengerEntity entity) {
        if (entity == null) {
            return null;
        }

        return new PassengerDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getPassportNumber(),
                entity.getContactInfo()
        );
    }
}