package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.Exercises;
import lk.ijse.theGym.to.Schedule;
import lk.ijse.theGym.to.ScheduleDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleController {
    public static ResultSet getAll() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM exercises ORDER BY LENGTH(exercises_id )");
    }

    public static boolean update(Exercises exercises) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE exercises SET exercises=? WHERE exercises_id=?",
                exercises.getExercises(),
                exercises.getId()
                );
    }

    public static boolean remove(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM exercises WHERE exercises_id=?",id);
    }

    public static boolean exsist(String id) throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT * FROM exercises WHERE exercises_id=?",id);
        return set.next() ;
    }

    public static void setData(Exercises exercises) throws SQLException, ClassNotFoundException {
        CrudUtil.crudUtil("INSERT INTO exercises VALUES (?,?)",
                exercises.getId(),
                exercises.getExercises()
                );
    }

    public static ResultSet getIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT schedule_id FROM schedule ORDER BY LENGTH(schedule_id)");
    }

    public static boolean setSchedule(Schedule schedule, ArrayList<ScheduleDetails> scheduleDetails) throws SQLException {
        Connection connection=null;
        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (ScheduleController.setSchedule(schedule)){
                if (ScheduleDetailsController.setDetails(scheduleDetails,schedule)){
                   connection.commit();
                    System.out.println("tranceAction ok");
                    return true;

                }else {
                    System.out.println("SD RollBack");
                    connection.rollback();
                }
            }else {
                System.out.println("S RollBack");
                connection.rollback();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean setSchedule(Schedule schedule) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO schedule VALUES (?,?,?)",
                schedule.getCustomer_id(),
                schedule.getSchedule_id(),
                schedule.getCoach_id()
                );
    }
}
