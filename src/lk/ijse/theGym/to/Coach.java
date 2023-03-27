package lk.ijse.theGym.to;

public class Coach {
    private String coach_id;
    private String fist_name;
    private String last_name;
    private String address_street;
    private String address_city;
    private String address_lene;
    private String e_mail;
    private String birthday;
    private String nic;
    private String contact_number;
    private String gender;
    private String Register_date;
    private String salary_id;
    private String coach_type;

    public Coach() {
    }

    public Coach(String coach_id, String fist_name, String last_name, String address_street, String address_city, String address_lene, String e_mail, String birthday, String nic, String contact_number, String gender, String register_date, String salary_id, String coach_type) {
        this.coach_id = coach_id;
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
        Register_date = register_date;
        this.salary_id = salary_id;
        this.coach_type = coach_type;
    }

    public String getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
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

    public String getRegister_date() {
        return Register_date;
    }

    public void setRegister_date(String register_date) {
        Register_date = register_date;
    }

    public String getSalary_id() {
        return salary_id;
    }

    public void setSalary_id(String salary_id) {
        this.salary_id = salary_id;
    }

    public String getCoach_type() {
        return coach_type;
    }

    public void setCoach_type(String coach_type) {
        this.coach_type = coach_type;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "coach_id='" + coach_id + '\'' +
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
                ", Register_date='" + Register_date + '\'' +
                ", salary_id='" + salary_id + '\'' +
                ", coach_type='" + coach_type + '\'' +
                '}';
    }
}
