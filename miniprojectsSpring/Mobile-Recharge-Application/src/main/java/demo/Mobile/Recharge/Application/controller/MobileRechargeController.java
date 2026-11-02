package demo.Mobile.Recharge.Application.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import demo.Mobile.Recharge.Application.model.MobileRecharge;
import demo.Mobile.Recharge.Application.service.MobileRechargeService;

@RestController
@RequestMapping("/api/recharges")
public class MobileRechargeController {

    private final MobileRechargeService service;

    public MobileRechargeController(MobileRechargeService service) {
        this.service = service;
    }

    // Add recharge
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MobileRecharge addRecharge(
            @Valid @RequestBody MobileRecharge recharge) {

        return service.addRecharge(recharge);
    }

    // Get all recharges
    @GetMapping
    public List<MobileRecharge> getAllRecharges() {

        return service.getAllRecharges();
    }

    // Get recharge by ID
    @GetMapping("/{id}")
    public MobileRecharge getRechargeById(@PathVariable Long id) {

        return service.getRechargeById(id);
    }

    // Update recharge
    @PutMapping("/{id}")
    public MobileRecharge updateRecharge(
            @PathVariable Long id,
            @Valid @RequestBody MobileRecharge recharge) {

        return service.updateRecharge(id, recharge);
    }

    // Delete recharge
    @DeleteMapping("/{id}")
    public String deleteRecharge(@PathVariable Long id) {

        return service.deleteRecharge(id);
    }

    // Search by mobile number
    @GetMapping("/search/mobile")
    public List<MobileRecharge> searchByMobileNumber(
            @RequestParam String mobileNumber) {

        return service.searchByMobileNumber(mobileNumber);
    }

    // Search by customer name
    @GetMapping("/search/customer")
    public List<MobileRecharge> searchByCustomerName(
            @RequestParam String customerName) {

        return service.searchByCustomerName(customerName);
    }

    // Search by operator
    @GetMapping("/search/operator")
    public List<MobileRecharge> searchByOperator(
            @RequestParam String operator) {

        return service.searchByOperator(operator);
    }
}