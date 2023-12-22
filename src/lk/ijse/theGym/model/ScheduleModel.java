package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.dto.ExerciseDTO;
import lk.ijse.theGym.dto.ScheduleDTO;
import lk.ijse.theGym.dto.ScheduleDetailsDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleModel {

    public static boolean placeSchedule(ScheduleDTO scheduleDTO, List<ScheduleDetailsDTO> scheduleDetailsDTOS) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (ScheduleModel.save(scheduleDTO)) {
                if (ScheduleDetailsModel.setDetails(scheduleDetailsDTOS)) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            connection.rollback();
            throwables.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean save(ScheduleDTO scheduleDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO schedule VALUES (?,?,?)",
                scheduleDTO.getCustomer_id(),
                scheduleDTO.getSchedule_id(),
                scheduleDTO.getCoach_id()
        );
    }

    public static List<String> findIdOrderByLength() throws SQLException, ClassNotFoundException {
        return setStringList(CrudUtil.crudUtil("SELECT schedule_id FROM schedule ORDER BY LENGTH(schedule_id)"));
    }

    public static boolean isExistId(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM exercises WHERE exercises_id=?", id);
        return set.next();
    }

    private static List<String> setStringList(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public static List<ExerciseDTO> findExercise() throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT * FROM exercises ORDER BY LENGTH(exercises_id )"));
    }

    private static List<ExerciseDTO> setDTOs(ResultSet resultSet) throws SQLException {
        List<ExerciseDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            ExerciseDTO exerciseDTO = new ExerciseDTO();
            exerciseDTO.setExercises_id(resultSet.getString(1));
            exerciseDTO.setExercises(resultSet.getString(2));
        }
        return list;
    }

}
