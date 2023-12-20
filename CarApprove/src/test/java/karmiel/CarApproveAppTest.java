package karmiel;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Test;
import karmiel.dto.CarDTO;
import karmiel.kafka.KafkaProducer;
import karmiel.service.CarProcessingService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class CarApproveAppTest{

@Mock
private KafkaProducer kafkaProducer;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CarProcessingService carProcessingService;


    void testCarAproove() throws Exception {
        String carDTOAsString = {number: 103665,\n" +
                "  \"vin\": \"730497G047YWNE18R\",\n" +
                "  \"brand\": \"Fiat\",\n" +
                "  \"model\": \"911 \",\n" +
                "  \"bumper\": true,\n" +
                "  \"windscreen\": true,\n" +
                "  \"clean\": true,\n" +
                "  \"state\": 7,\n" +
                "  \"color\": \"Green\",\n" +
                "  \"years\": 1954,\n" +
                "  \"mileage\": 241600,\n" +
                "  \"price\": 9912\n" +
                "};

        CarDTO carDTO = new CarDTO(  103665,
                "730497G047YWNE18R",
                 "Fiat",
                "911 ",
                 true,
                 true,
                 true,
                7,
                 "Green",
                1954,
                241600,
                 9912);

        when(objectMapper.readValue(carDTOAsString, CarDTO.class)).thenReturn(carDTO);

        carProcessingService.processCar(carDTOAsString);


        verify(kafkaProducer, times(1)).sendApprovalMessage(any());
        verify(kafkaProducer, never()).sendRejectionMessage(any());
    }

}
