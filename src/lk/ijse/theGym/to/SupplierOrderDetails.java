package lk.ijse.theGym.to;

public class SupplierOrderDetails {
    private String order_id;
    private String item_code;
    private String qut;
    private String price;

    public SupplierOrderDetails() {
    }

    public SupplierOrderDetails(String order_id, String item_code, String qut, String price) {
        this.order_id = order_id;
        this.item_code = item_code;
        this.qut = qut;
        this.price = price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getQut() {
        return qut;
    }

    public void setQut(String qut) {
        this.qut = qut;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SupplierOrderDetails{" +
                "order_id='" + order_id + '\'' +
                ", item_code='" + item_code + '\'' +
                ", qut='" + qut + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
