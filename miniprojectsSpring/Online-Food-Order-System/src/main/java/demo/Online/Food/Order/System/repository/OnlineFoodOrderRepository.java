package demo.Online.Food.Order.System.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.Online.Food.Order.System.model.OnlineFoodOrder;

public interface OnlineFoodOrderRepository
        extends JpaRepository<OnlineFoodOrder, Long> {

    // Search food item
    List<OnlineFoodOrder> findByFoodItemContainingIgnoreCase(
            String foodItem);

    // Search customer name
    List<OnlineFoodOrder> findByCustomerNameIgnoreCase(
            String customerName);

    // Search price range
    List<OnlineFoodOrder> findByPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice);

    // Search minimum quantity
    List<OnlineFoodOrder> findByQuantityGreaterThanEqual(
            Integer quantity);
}