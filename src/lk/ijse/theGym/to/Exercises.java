package lk.ijse.theGym.to;

public class Exercises {
    String id;
    String  exercises;

    public Exercises() {
    }

    public Exercises(String id, String exercises) {
        this.id = id;
        this.exercises = exercises;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Exercises{" +
                "id='" + id + '\'' +
                ", exercises='" + exercises + '\'' +
                '}';
    }
}
