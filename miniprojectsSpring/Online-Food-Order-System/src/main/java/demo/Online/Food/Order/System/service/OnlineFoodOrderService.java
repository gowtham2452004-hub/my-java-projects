package demo.Online.Food.Order.System.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import demo.Online.Food.Order.System.model.OnlineFoodOrder;

import demo.Online.Food.Order.System.repository.OnlineFoodOrderRepository;

import demo.Online.Food.Order.System.exception.OnlineFoodOrderNotFoundException;

@Service
public class OnlineFoodOrderService {

    private final OnlineFoodOrderRepository repository;

    public OnlineFoodOrderService(
            OnlineFoodOrderRepository repository) {

        this.repository = repository;

    }

    // 1. Add new order
    public OnlineFoodOrder addOrder(OnlineFoodOrder order) {

        order.setOrderId(null);

        return repository.save(order);

    }

    // 2. Get all orders
    public List<OnlineFoodOrder> getAllOrders() {

        return repository.findAll();

    }

    // 3. Get order by ID
    public OnlineFoodOrder getOrderById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new OnlineFoodOrderNotFoundException(
                                "Order not found with ID: " + id));

    }

    // 4. Update complete order
    public OnlineFoodOrder updateOrder(
            Long id, OnlineFoodOrder request) {

        OnlineFoodOrder order = getOrderById(id);

        order.setCustomerName(request.getCustomerName().trim());

        order.setFoodItem(request.getFoodItem().trim());

        order.setQuantity(request.getQuantity());

        order.setPrice(request.getPrice());

        return repository.save(order);

    }

    // 5. Delete order
    public String deleteOrder(Long id) {

        OnlineFoodOrder order = getOrderById(id);

        repository.delete(order);

        return "Order deleted successfully. ID: " + id;

    }

    // 6. Partial update quantity
    public OnlineFoodOrder updateQuantity(Long id, Integer quantity) {

        if (quantity == null || quantity < 1) {

            throw new IllegalArgumentException(
                    "Quantity must be at least 1");

        }

        OnlineFoodOrder order = getOrderById(id);

        order.setQuantity(quantity);

        return repository.save(order);

    }

    // 7. Search by food item
    public List<OnlineFoodOrder> searchByFoodItem(String foodItem) {

        checkNotBlank(foodItem, "Food item cannot be blank");

        return repository.findByFoodItemContainingIgnoreCase(
                foodItem.trim());

    }

    // 8. Search by customer
    public List<OnlineFoodOrder> searchByCustomer(String name) {

        checkNotBlank(name, "Customer name cannot be blank");

        return repository.findByCustomerNameIgnoreCase(name.trim());

    }

    // 9. Search by price range
    public List<OnlineFoodOrder> searchByPrice(
            BigDecimal min, BigDecimal max) {

        if (min == null || max == null
                || min.signum() < 0
                || max.compareTo(min) < 0) {

            throw new IllegalArgumentException(
                    "Enter a valid price range");

        }

        return repository.findByPriceBetween(min, max);

    }

    // 10. Search by minimum quantity
    public List<OnlineFoodOrder> searchByQuantity(Integer quantity) {

        if (quantity == null || quantity < 1) {

            throw new IllegalArgumentException(
                    "Quantity must be at least 1");

        }

        return repository.findByQuantityGreaterThanEqual(quantity);

    }

    // 11. Total orders and revenue
    public Map<String, Object> getOrderSummary() {

        List<OnlineFoodOrder> orders = repository.findAll();

        BigDecimal revenue = orders.stream()
                .map(OnlineFoodOrder::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                "totalOrders", orders.size(),
                "totalRevenue", revenue
        );

    }

    // Check empty values
    private void checkNotBlank(String value, String message) {

        if (value == null || value.isBlank()) {

            throw new IllegalArgumentException(message);

        }
    }
}