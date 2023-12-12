package cars_project.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private String orderNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Date orderDate, String orderNumber) {
        this.orderDate = orderDate;
        this.orderNumber = orderNumber;
    }

    public void addOrderItem(String partType, int maxQuantity, int currentQuantity) {
        int orderQuantity = Math.max(maxQuantity - currentQuantity, 0);

        if (orderQuantity > 0) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPartType(partType);
            orderItem.setQuantity(orderQuantity);
            orderItems.add(orderItem);
        }
    }

//    public List<OrderItem> getOrderItems() {
//        return orderItems;
//    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderDate, order.orderDate) && Objects.equals(orderNumber, order.orderNumber) && Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, orderNumber, orderItems);
    }
}
