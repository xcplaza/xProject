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
public class CarNotReadyDto {
    @Id
    private int number;
    private String vin;
    private String brand;
    private String model;
    private boolean bumper;
    private boolean windscreen;
    private boolean clean;
    private int state;
    private String color;
    private int years;
    private int mileage;
    private int price;
    List<String> carPartsList;
}
