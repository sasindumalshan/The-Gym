package lk.ijse.theGym.to;

public class Customer {
    private String customer_id;
    private String fist_name;
    private String  last_name;
    private String address_street;
    private String address_city;
    private String address_lene;
    private String e_mail;
    private String birthday;
    private String nic;
    private String contact_number;
    private String gender;
    private String package_id;
    private String package_activate_date;
    private String date;

    public Customer() {
    }

    public Customer(String customer_id, String fist_name, String last_name, String address_street, String address_city, String address_lene, String e_mail, String birthday, String nic, String contact_number, String gender, String package_id, String package_activate_date, String date) {
        this.customer_id = customer_id;
        this.fist_name = fist_name;
        this.last_name = last_name;
        this.address_street = address_street;
        this.address_city = address_city;
        this.address_lene = address_lene;
        this.e_mail = e_mail;
        this.birthday = birthday;
        this.nic = nic;
        this.contact_number = contact_number;
        this.gender = gender;
        this.package_id = package_id;
        this.package_activate_date = package_activate_date;
        this.date = date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFist_name() {
        return fist_name;
    }

    public void setFist_name(String fist_name) {
        this.fist_name = fist_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        this.address_street = address_street;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_lene() {
        return address_lene;
    }

    public void setAddress_lene(String address_lene) {
        this.address_lene = address_lene;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackage_activate_date() {
        return package_activate_date;
    }

    public void setPackage_activate_date(String package_activate_date) {
        this.package_activate_date = package_activate_date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id='" + customer_id + '\'' +
                ", fist_name='" + fist_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address_street='" + address_street + '\'' +
                ", address_city='" + address_city + '\'' +
                ", address_lene='" + address_lene + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nic='" + nic + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", gender='" + gender + '\'' +
                ", package_id='" + package_id + '\'' +
                ", package_activate_date='" + package_activate_date + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
