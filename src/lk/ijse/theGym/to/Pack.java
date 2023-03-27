package lk.ijse.theGym.to;

public class Pack {
    private double package_price;
    private String package_type;
    private int package_duration;
    private String package_Id;

    public Pack() {
    }

    public Pack(double package_price, String package_type, int package_duration, String package_Id) {
        this.package_price = package_price;
        this.package_type = package_type;
        this.package_duration = package_duration;
        this.package_Id = package_Id;
    }

    public double getPackage_price() {
        return package_price;
    }

    public void setPackage_price(double package_price) {
        this.package_price = package_price;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public int getPackage_duration() {
        return package_duration;
    }

    public void setPackage_duration(int package_duration) {
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
        return "Pack{" +
                "package_price=" + package_price +
                ", package_type='" + package_type + '\'' +
                ", package_duration=" + package_duration +
                ", package_Id='" + package_Id + '\'' +
                '}';
    }
}
