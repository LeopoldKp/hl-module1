//package ru.hpclab.hl.module1.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.hpclab.hl.module1.Application;
//import ru.hpclab.hl.module1.model.Parcel;
//import ru.hpclab.hl.module1.repository.ParcelRepository;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = Application.class)
//@AutoConfigureMockMvc
//public class ParcelControllerTest {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ParcelRepository parcelRepository;
//
//    @BeforeEach
//    public void init() {
//        parcelRepository.clear();
//    }
//
//    @Test
//    public void getParcelById_should_returnParcel_when_parcelExists() throws Exception {
//        Parcel parcel = new Parcel(1L, 5.5, "30x20x10", "123 Main St");
//        parcelRepository.add(parcel);
//        String expectedJson = objectMapper.writeValueAsString(parcel);
//
//        mvc.perform(get("/parcels/" + parcel.getId()).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(expectedJson));
//    }
//}