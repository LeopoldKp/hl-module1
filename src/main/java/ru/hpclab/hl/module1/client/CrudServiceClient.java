package ru.hpclab.hl.module1.client;

import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.util.List;

public interface CrudServiceClient {
    List<FlightDTO> searchFlights(String date, String departure, String destination);
    List<BookingDTO> getAllBookings();
}