package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.PassengerDTO;
import ru.hpclab.hl.module1.entity.PassengerEntity;
import ru.hpclab.hl.module1.mapper.PassengerMapper;
import ru.hpclab.hl.module1.repository.PassengerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(PassengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PassengerDTO getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .map(PassengerMapper::toDTO)
                .orElse(null);
    }

    public PassengerDTO createOrUpdatePassenger(PassengerDTO passengerDTO) {
        PassengerEntity entity = PassengerMapper.toEntity(passengerDTO);
        entity = passengerRepository.save(entity);
        return PassengerMapper.toDTO(entity);
    }
}