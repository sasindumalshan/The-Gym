package lk.ijse.theGym.to;

public class ScheduleDetails {
    String exercises_id;
    String steps;
    String schedule_id;

    public ScheduleDetails() {
    }

    public ScheduleDetails(String exercises_id, String steps, String schedule_id) {
        this.exercises_id = exercises_id;
        this.steps = steps;
        this.schedule_id = schedule_id;
    }

    public String getExercises_id() {
        return exercises_id;
    }

    public void setExercises_id(String exercises_id) {
        this.exercises_id = exercises_id;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    @Override
    public String toString() {
        return "ScheduleDetails{" +
                "exercises_id='" + exercises_id + '\'' +
                ", steps='" + steps + '\'' +
                ", schedule_id='" + schedule_id + '\'' +
                '}';
    }
}
