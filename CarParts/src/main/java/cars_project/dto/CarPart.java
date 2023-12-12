package cars_project.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class CarPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private int quantity;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CarPart carPart = (CarPart) o;
        return quantity == carPart.quantity && Objects.equals(id, carPart.id) && Objects.equals(type, carPart.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, quantity);
    }
}

