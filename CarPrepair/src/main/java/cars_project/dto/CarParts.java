package cars_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarParts {
    boolean bumper;
    boolean windScreen;

    public CarParts(String bumper) {
    }
}
