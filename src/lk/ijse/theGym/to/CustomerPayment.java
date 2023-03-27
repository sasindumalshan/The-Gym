package lk.ijse.theGym.to;

public class CustomerPayment {
    private String monthly_fees;
    private String date;
    private String month;
    private String customer_id;
    private String year;

    public CustomerPayment(String monthly_fees, String date, String month, String customer_id, String year) {
        this.monthly_fees = monthly_fees;
        this.date = date;
        this.month = month;
        this.customer_id = customer_id;
        this.year = year;
    }

    public CustomerPayment() {
    }

    public String getMonthly_fees() {
        return monthly_fees;
    }

    public void setMonthly_fees(String monthly_fees) {
        this.monthly_fees = monthly_fees;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "CustomerPayment{" +
                "monthly_fees='" + monthly_fees + '\'' +
                ", date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
