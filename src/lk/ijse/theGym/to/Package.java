package lk.ijse.theGym.to;

public class Package {
    private String package_price;
    private String package_type;
    private String package_duration;
    private String package_Id;

    public Package() {
    }

    public Package(String package_price, String package_type, String package_duration, String package_Id) {
        this.package_price = package_price;
        this.package_type = package_type;
        this.package_duration = package_duration;
        this.package_Id = package_Id;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getPackage_duration() {
        return package_duration;
    }

    public void setPackage_duration(String package_duration) {
        this.package_duration = package_duration;
    }

    public String getPackage_Id() {
        return package_Id;
    }

    public void setPackage_Id(String package_Id) {
        this.package_Id = package_Id;
    }

    @Override
    public String toString() {
        return "Package{" +
                "package_price='" + package_price + '\'' +
                ", package_type='" + package_type + '\'' +
                ", package_duration='" + package_duration + '\'' +
                ", package_Id='" + package_Id + '\'' +
                '}';
    }
}
