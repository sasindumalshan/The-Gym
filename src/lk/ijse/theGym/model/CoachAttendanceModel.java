package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.CoachAttendanceDTO;
import lk.ijse.theGym.dto.projection.AttendanceProjection;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoachAttendanceModel {

    public static boolean save(CoachAttendanceDTO coachAttendanceDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO coach_attendance VALUES (?,?,?)",
                coachAttendanceDTO.getDate(),
                coachAttendanceDTO.getTime(),
                coachAttendanceDTO.getCoach_id());
    }

    public static ArrayList<AttendanceProjection> findAttendanceByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT coach_attendance.date,coach_attendance.time,coach_attendance.coach_id,coach.fist_name,coach.last_name FROM coach INNER JOIN coach_attendance ON coach.coach_id = coach_attendance.coach_id WHERE coach_attendance.date=?";
        return setProjection(CrudUtil.crudUtil(sql, date));
    }

    public static ArrayList<AttendanceProjection> findAttendanceByCoachId(String coachId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT coach_attendance.date,coach_attendance.time,coach_attendance.coach_id,coach.fist_name,coach.last_name FROM coach INNER JOIN coach_attendance ON coach.coach_id = coach_attendance.coach_id WHERE coach_attendance.coach_id=?";
        return setProjection(CrudUtil.crudUtil(sql, coachId));
    }

    public static ArrayList<AttendanceProjection> findAttendanceByDateAndFistNameLikeOrLastNameOrDateLikeOrCoachId(String date, String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT coach_attendance.date,coach_attendance.time,coach_attendance.coach_id,coach.fist_name,coach.last_name FROM coach INNER JOIN coach_attendance ON coach_attendance.coach_id = coach.coach_id WHERE coach_attendance.date=? AND coach.fist_name LIKE ? OR coach_attendance.date=? AND coach.last_name LIKE ? OR coach_id LIKE ?";
        return setProjection(CrudUtil.crudUtil(sql, date, searchText, date, searchText, searchText));
    }

    public static String countAttendanceByDate() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM coach_attendance WHERE date=?", DateTimeUtil.dateNow());
        return setString(resultSet);
    }

    public static String countAttendanceByDateAndCouchId(String coachId, String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM coach_attendance WHERE coach_id=? AND date LIKE ?", coachId, date + "%");
        return setString(resultSet);
    }

    private static ArrayList<AttendanceProjection> setProjection(ResultSet resultSet) throws SQLException {
        ArrayList<AttendanceProjection> attendanceProjectionsList = new ArrayList<>();
        while (resultSet.next()) {
            AttendanceProjection attendanceProjection = new AttendanceProjection();
            attendanceProjection.setDate(resultSet.getString(1));
            attendanceProjection.setTime(resultSet.getString(2));
            attendanceProjection.setId(resultSet.getString(3));
            attendanceProjection.setFistName(resultSet.getString(4));
            attendanceProjection.setLastName(resultSet.getString(5));

            attendanceProjectionsList.add(attendanceProjection);
        }
        return attendanceProjectionsList;
    }

    private static String setString(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    /*public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id FROM customer_attendance  WHERE date=?",DateTimeUtil.dateNow());
    }*/

    /*private static ArrayList<String> setList(ResultSet resultSet) throws SQLException {
        ArrayList<String> list=new ArrayList<>();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }*/

}
