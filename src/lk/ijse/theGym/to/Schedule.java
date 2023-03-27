package lk.ijse.theGym.to;

public class Schedule {
    String customer_id;
    String schedule_id;
    String coach_id;

    public Schedule() {
    }

    public Schedule(String customer_id, String schedule_id, String coach_id) {
        this.customer_id = customer_id;
        this.schedule_id = schedule_id;
        this.coach_id = coach_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "customer_id='" + customer_id + '\'' +
                ", schedule_id='" + schedule_id + '\'' +
                ", coach_id='" + coach_id + '\'' +
                '}';
    }
}
