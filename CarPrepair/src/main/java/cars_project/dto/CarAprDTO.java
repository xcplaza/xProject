package cars_project.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarAprDTO {
    @Id
    private String vin;
    private String brand;
    private String model;
    private boolean bumper;
    private boolean windscreen;
    private boolean clean;
    private int state;
    private boolean arpooved;

}
