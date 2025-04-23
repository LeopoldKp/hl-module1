//package ru.hpclab.hl.module1.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import ru.hpclab.hl.module1.model.Parcel;
//import ru.hpclab.hl.module1.repository.ParcelRepository;
//
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {ParcelServiceTest.ParcelServiceTestConfiguration.class})
//public class ParcelServiceTest {
//
//    @Autowired
//    private ParcelService parcelService;
//
//    @Autowired
//    private ParcelRepository parcelRepository;
//
//    @Test
//    public void testCreateAndGetParcels() {
//
//        Parcel parcel = new Parcel(1L, 5.5, "30x20x10", "123 Main St");
//
//        Parcel savedParcel = parcelService.addParcel(parcel);
//
//        Assertions.assertEquals(parcel.getWeight(), savedParcel.getWeight());
//        Mockito.verify(parcelRepository, Mockito.times(1)).add(parcel);
//
//        List<Parcel> parcelList = parcelService.getAllParcels();
//
//        Assertions.assertEquals("30x20x10", parcelList.get(0).getDimensions());
//        Assertions.assertEquals("50x40x30", parcelList.get(1).getDimensions());
//        Mockito.verify(parcelRepository, Mockito.times(1)).getAll();
//    }
//
//    @Configuration
//    static class ParcelServiceTestConfiguration {
//
//        @Bean
//        ParcelRepository parcelRepository() {
//            ParcelRepository parcelRepository = mock(ParcelRepository.class);
//
//            when(parcelRepository.add(any())).thenAnswer(invocation -> invocation.getArgument(0));
//
//            when(parcelRepository.getAll()).thenReturn(Arrays.asList(
//                    new Parcel(1L, 5.5, "30x20x10", "123 Main St"),
//                    new Parcel(2L, 10.2, "50x40x30", "456 Elm St")
//            ));
//
//            return parcelRepository;
//        }
//
//        @Bean
//        ParcelService parcelService(ParcelRepository parcelRepository) {
//            return new ParcelService(parcelRepository);
//        }
//    }
//
//}