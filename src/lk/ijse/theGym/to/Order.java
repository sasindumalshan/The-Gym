package lk.ijse.theGym.to;

public class Order {
    private String order_id;
    private String time;
    private String date;
    private double final_total;
    private String customer_id;

    public Order(String order_id, String time, String date, double final_total, String customer_id) {
        this.setOrder_id(order_id);
        this.setTime(time);
        this.setDate(date);
        this.setFinal_total(final_total);
        this.setCustomer_id(customer_id);
    }

    public Order() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getFinal_total() {
        return final_total;
    }

    public void setFinal_total(double final_total) {
        this.final_total = final_total;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", final_total='" + final_total + '\'' +
                ", customer_id='" + customer_id + '\'' +
                '}';
    }
}
