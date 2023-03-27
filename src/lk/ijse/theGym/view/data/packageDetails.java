package lk.ijse.theGym.view.data;

public class packageDetails {
    private String id;
    private String packageName;
    private String price;
    private String packageDuration;

    public packageDetails() {
    }

    public packageDetails(String id, String packageName, String price, String packageDuration) {
        this.id = id;
        this.packageName = packageName;
        this.price = price;
        this.packageDuration = packageDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "packageDetails{" +
                "id='" + id + '\'' +
                ", packageName='" + packageName + '\'' +
                ", price='" + price + '\'' +
                ", packageDuration='" + packageDuration + '\'' +
                '}';
    }

    public String getPackageDuration() {
        return packageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        this.packageDuration = packageDuration;
    }
}
