package lk.ijse.theGym.model;

import lk.ijse.theGym.to.CoachAttendance;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachAttendanceController {
    public static ResultSet getTodayAttendance(String dateNow) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_attendance.date,coach_attendance.time,coach_attendance.coach_id,coach.fist_name,coach.last_name FROM coach INNER JOIN coach_attendance ON coach.coach_id = coach_attendance.coach_id WHERE coach_attendance.date=?",dateNow);
    }

    public static ResultSet getsearchName(String date, String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_attendance.coach_id FROM coach INNER JOIN coach_attendance ON coach_attendance.coach_id = coach.coach_id WHERE coach_attendance.date=? AND coach.fist_name LIKE ? OR coach_attendance.date=? AND coach.last_name LIKE ?",date,name,date,name);
    }

    public static ResultSet getsearchId(String date, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_id FROM coach_attendance WHERE coach_attendance.date=? AND coach_id LIKE ?",date,id);
    }

    public static ResultSet getIdData(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_attendance.date,coach_attendance.time,coach_attendance.coach_id,coach.fist_name,coach.last_name FROM coach INNER JOIN coach_attendance ON coach.coach_id = coach_attendance.coach_id WHERE coach_attendance.coach_id=?",id);
    }

    public static ResultSet getTodayAttendanceCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM coach_attendance WHERE date=?", DateTimeUtil.dateNow());
    }

    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id FROM customer_attendance  WHERE date=?",DateTimeUtil.dateNow());
    }

    public static boolean setAttendance(CoachAttendance coachAttendance) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO coach_attendance VALUES (?,?,?)",coachAttendance.getDate(),coachAttendance.getTime(),coachAttendance.getCoach_id());
    }

    public static ResultSet getAttendanceCount(String valueOf) throws SQLException, ClassNotFoundException {
        String[] split = DateTimeUtil.dateNow().split("-");
        String month=split[0]+"-"+split[1];
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM coach_attendance WHERE coach_id=? AND date LIKE ?",valueOf,month+"%");
    }
}
