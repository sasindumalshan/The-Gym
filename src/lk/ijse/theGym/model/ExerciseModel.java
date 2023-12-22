package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.ExerciseDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseModel {

    public static boolean save(ExerciseDTO exerciseDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO exercises VALUES (?,?)",
                exerciseDTO.getExercises_id(),
                exerciseDTO.getExercises()
        );
    }

    public static boolean update(ExerciseDTO exerciseDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE exercises SET exercises=? WHERE exercises_id=?",
                exerciseDTO.getExercises(),
                exerciseDTO.getExercises_id()
        );
    }

    public static boolean remove(String exercisesId) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM exercises WHERE exercises_id=?", exercisesId);
    }

    public static List<String> findIdOrderByLength() throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT exercises_id FROM exercises ORDER BY LENGTH(exercises_id ),exercises_id"));
    }

    private static List<String> setString(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

}
