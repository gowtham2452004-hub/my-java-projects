package demo.Mobile.Recharge.Application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import demo.Mobile.Recharge.Application.model.MobileRecharge;
import demo.Mobile.Recharge.Application.repository.MobileRechargeRepository;

@Service
public class MobileRechargeService {

    private final MobileRechargeRepository repository;

    public MobileRechargeService(MobileRechargeRepository repository) {
        this.repository = repository;
    }

    // Add new recharge
    public MobileRecharge addRecharge(MobileRecharge recharge) {

        return repository.save(recharge);
    }

    // Get all recharges
    public List<MobileRecharge> getAllRecharges() {

        return repository.findAll();
    }

    // Get recharge by ID
    public MobileRecharge getRechargeById(Long id) {

        MobileRecharge recharge = repository.findById(id).orElse(null);

        if (recharge == null) {
            throw new RuntimeException("Recharge not found with ID: " + id);
        }

        return recharge;
    }

    // Update recharge
    public MobileRecharge updateRecharge(Long id, MobileRecharge recharge) {

        MobileRecharge oldRecharge = getRechargeById(id);

        oldRecharge.setCustomerName(recharge.getCustomerName());
        oldRecharge.setMobileNumber(recharge.getMobileNumber());
        oldRecharge.setOperator(recharge.getOperator());
        oldRecharge.setAmount(recharge.getAmount());
        oldRecharge.setPlanType(recharge.getPlanType());

        return repository.save(oldRecharge);
    }

    // Delete recharge
    public String deleteRecharge(Long id) {

        MobileRecharge recharge = getRechargeById(id);

        repository.delete(recharge);

        return "Recharge deleted successfully";
    }

    // Search by mobile number
    public List<MobileRecharge> searchByMobileNumber(String mobileNumber) {

        return repository.findByMobileNumber(mobileNumber);
    }

    // Search by customer name
    public List<MobileRecharge> searchByCustomerName(String customerName) {

        return repository.findByCustomerNameContainingIgnoreCase(customerName);
    }

    // Search by operator
    public List<MobileRecharge> searchByOperator(String operator) {

        return repository.findByOperator(operator);
    }
}