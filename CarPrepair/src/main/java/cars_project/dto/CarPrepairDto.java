package cars_project.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CarPrepairDto {
    @Id
    private String vin;
    private String brand;
    private String model;
    private boolean bumper;
    private boolean windscreen;
    private boolean clean;
    private int state;
    private boolean arpooved;
    List<CarParts> carPartsList;
}
