package demo.Mobile.Recharge.Application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class MobileRecharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rechargeId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
        regexp = "[0-9]{10}",
        message = "Mobile number must contain 10 digits"
    )
    private String mobileNumber;

    @NotBlank(message = "Operator is required")
    private String operator;

    @NotNull(message = "Amount is required")
    @Min(value = 10, message = "Minimum recharge amount is 10")
    private Integer amount;

    @NotBlank(message = "Plan type is required")
    private String planType;

    public MobileRecharge() {
    }

    public Long getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }
}