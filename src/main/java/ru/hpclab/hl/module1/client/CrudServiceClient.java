package ru.hpclab.hl.module1.client;

import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.util.List;

public interface CrudServiceClient {
    List<BookingDTO> getAllBookings();
    FlightDTO getFlightById(Long id);  // Новый метод
}