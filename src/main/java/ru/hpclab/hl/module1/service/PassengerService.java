package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.PassengerDTO;
import ru.hpclab.hl.module1.entity.PassengerEntity;
import ru.hpclab.hl.module1.mapper.PassengerMapper;
import ru.hpclab.hl.module1.repository.PassengerRepository;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final ObservabilityService observabilityService;

    public PassengerService(PassengerRepository passengerRepository,
                            ObservabilityService observabilityService) {
        this.passengerRepository = passengerRepository;
        this.observabilityService = observabilityService;
    }

    public List<PassengerDTO> getAllPassengers() {
        observabilityService.start("getAllPassengersDB");
        try {
            return passengerRepository.findAll().stream()
                    .map(PassengerMapper::toDTO)
                    .collect(Collectors.toList());
        } finally {
            observabilityService.stop("getAllPassengersDB");
        }
    }

    public PassengerDTO getPassengerById(Long id) {
        observabilityService.start("getPassengerByIdDB");
        try {
            return passengerRepository.findById(id)
                    .map(PassengerMapper::toDTO)
                    .orElse(null);
        } finally {
            observabilityService.stop("getPassengerByIdDB");
        }
    }

    public PassengerDTO createOrUpdatePassenger(PassengerDTO passengerDTO) {
        observabilityService.start("savePassengerDB");
        try {
            PassengerEntity entity = PassengerMapper.toEntity(passengerDTO);
            entity = passengerRepository.save(entity);
            return PassengerMapper.toDTO(entity);
        } finally {
            observabilityService.stop("savePassengerDB");
        }
    }

    public void clearAll() {
        observabilityService.start("clearAllPassengersDB");
        try {
            passengerRepository.deleteAll();
        } finally {
            observabilityService.stop("clearAllPassengersDB");
        }
    }
}