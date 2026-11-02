package demo.Online.Food.Order.System.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "online_food_orders")
public class OnlineFoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String customerName;

    @NotBlank(message = "Food item cannot be empty")
    @Size(max = 100, message = "Food item cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String foodItem;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.01",
            message = "Price must be greater than zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public OnlineFoodOrder() {

    }

    public OnlineFoodOrder(Long orderId, String customerName,
            String foodItem, Integer quantity, BigDecimal price) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Transient
    public BigDecimal getTotalAmount() {

        return price.multiply(BigDecimal.valueOf(quantity));

    }
}