package lk.ijse.theGym.model;

import lk.ijse.theGym.to.Exercises;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseController {
    public static ResultSet getNextId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT exercises_id FROM exercises ORDER BY LENGTH(exercises_id ),exercises_id");
    }

    public static boolean setExercises(Exercises exercises) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO exercises VALUES (?,?)",
                exercises.getId(),
                exercises.getExercises()
        );
    }
}
