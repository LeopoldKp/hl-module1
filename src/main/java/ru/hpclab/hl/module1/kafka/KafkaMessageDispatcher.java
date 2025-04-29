package ru.hpclab.hl.module1.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.dto.PassengerDTO;
import ru.hpclab.hl.module1.service.BookingService;
import ru.hpclab.hl.module1.service.FlightService;
import ru.hpclab.hl.module1.service.PassengerService;

@Component
@RequiredArgsConstructor
public class KafkaMessageDispatcher {
    private final PassengerService passengerService;
    private final FlightService flightService;
    private final BookingService bookingService;
    private final ObjectMapper objectMapper;

    public void dispatch(KafkaMessage message) {
        switch (message.getEntity()) {
            case PASSENGER -> handlePassenger(message);
            case FLIGHT -> handleFlight(message);
            case BOOKING -> handleBooking(message);
        }
    }

    private void handlePassenger(KafkaMessage message) {
        PassengerDTO dto = deserializePayload(message.getPayload(), PassengerDTO.class);
        switch (message.getOperation()) {
            case CREATE -> passengerService.createOrUpdatePassenger(dto);
            case UPDATE -> passengerService.createOrUpdatePassenger(dto);
            case DELETE -> passengerService.clearAll();
            case CLEAR -> passengerService.clearAll();
        }
    }

    private void handleFlight(KafkaMessage message) {
        FlightDTO dto = deserializePayload(message.getPayload(), FlightDTO.class);
        switch (message.getOperation()) {
            case CREATE -> flightService.createFlight(dto);
            case UPDATE -> flightService.createFlight(dto);
            case DELETE -> flightService.clearAll();
            case CLEAR -> flightService.clearAll();
        }
    }

    private void handleBooking(KafkaMessage message) {
        BookingDTO dto = deserializePayload(message.getPayload(), BookingDTO.class);
        switch (message.getOperation()) {
            case CREATE -> bookingService.createBooking(dto);
            case UPDATE -> throw new UnsupportedOperationException("Booking update not supported");
            case DELETE -> throw new UnsupportedOperationException("Booking delete not supported");
            case CLEAR -> throw new UnsupportedOperationException("Booking clear not supported");
        }
    }

    private <T> T deserializePayload(JsonNode payload, Class<T> clazz) {
        try {
            return objectMapper.treeToValue(payload, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize payload", e);
        }
    }
}