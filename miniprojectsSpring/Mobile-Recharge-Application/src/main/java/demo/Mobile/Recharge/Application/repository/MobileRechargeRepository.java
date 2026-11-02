package demo.Mobile.Recharge.Application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.Mobile.Recharge.Application.model.MobileRecharge;

public interface MobileRechargeRepository
        extends JpaRepository<MobileRecharge, Long> {

    List<MobileRecharge> findByMobileNumber(String mobileNumber);

    List<MobileRecharge> findByCustomerNameContainingIgnoreCase(String customerName);

    List<MobileRecharge> findByOperator(String operator);
}