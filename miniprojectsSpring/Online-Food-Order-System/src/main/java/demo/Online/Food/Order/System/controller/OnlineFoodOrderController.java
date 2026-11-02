package demo.Online.Food.Order.System.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import demo.Online.Food.Order.System.model.OnlineFoodOrder;
import demo.Online.Food.Order.System.service.OnlineFoodOrderService;

@RestController
@RequestMapping("/api/online-food-orders")
public class OnlineFoodOrderController {

    private final OnlineFoodOrderService service;

    public OnlineFoodOrderController(
            OnlineFoodOrderService service) {

        this.service = service;

    }

    // 1. Add new order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OnlineFoodOrder addOrder(
            @Valid @RequestBody OnlineFoodOrder order) {

        return service.addOrder(order);

    }

    // 2. Get all orders
    @GetMapping
    public List<OnlineFoodOrder> getAllOrders() {

        return service.getAllOrders();

    }

    // 3. Get order by ID
    @GetMapping("/{id}")
    public OnlineFoodOrder getOrderById(
            @PathVariable Long id) {

        return service.getOrderById(id);

    }

    // 4. Update complete order
    @PutMapping("/{id}")
    public OnlineFoodOrder updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OnlineFoodOrder order) {

        return service.updateOrder(id, order);

    }

    // 5. Delete order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {

        return service.deleteOrder(id);

    }

    // 6. Partial update quantity
    @PatchMapping("/{id}/quantity")
    public OnlineFoodOrder updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        return service.updateQuantity(id, quantity);

    }

    // 7. Search by food item
    @GetMapping("/search/food")
    public List<OnlineFoodOrder> searchByFoodItem(
            @RequestParam String foodItem) {

        return service.searchByFoodItem(foodItem);

    }

    // 8. Search by customer name
    @GetMapping("/search/customer")
    public List<OnlineFoodOrder> searchByCustomer(
            @RequestParam String name) {

        return service.searchByCustomer(name);

    }

    // 9. Search by price range
    @GetMapping("/search/price")
    public List<OnlineFoodOrder> searchByPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        return service.searchByPrice(min, max);

    }

    // 10. Search by minimum quantity
    @GetMapping("/search/quantity")
    public List<OnlineFoodOrder> searchByQuantity(
            @RequestParam Integer quantity) {

        return service.searchByQuantity(quantity);

    }

    // 11. Order summary
    @GetMapping("/summary")
    public Map<String, Object> getOrderSummary() {

        return service.getOrderSummary();

    }
}