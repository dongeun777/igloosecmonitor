package igloosec.monitor.vo;

public class ResourceVo {

    private String id;
    private String date;
    private String MeterCategory;
    private String SubscriptionName;
    private String quantity;
    private String unitOfMeasure;
    private String costInBillingCurrency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeterCategory() {
        return MeterCategory;
    }

    public void setMeterCategory(String meterCategory) {
        MeterCategory = meterCategory;
    }

    public String getSubscriptionName() {
        return SubscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        SubscriptionName = subscriptionName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getCostInBillingCurrency() {
        return costInBillingCurrency;
    }

    public void setCostInBillingCurrency(String costInBillingCurrency) {
        this.costInBillingCurrency = costInBillingCurrency;
    }
}
