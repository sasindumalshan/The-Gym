package lk.ijse.theGym.model;

import lk.ijse.theGym.to.Schedule;
import lk.ijse.theGym.to.ScheduleDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleDetailsController {
    public static boolean setDetails(ArrayList<ScheduleDetails> scheduleDetails, Schedule schedule) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < scheduleDetails.size(); i++) {
            System.out.println("Steps "+scheduleDetails.get(i).getSteps());
            if (CrudUtil.crudUtil("INSERT INTO schedule_details VALUES (?,?,?)",
                    scheduleDetails.get(i).getExercises_id(),
                    scheduleDetails.get(i).getSteps(),
                    schedule.getSchedule_id()
                    )){

            }else {
                return false;
            }
        }
        return true;
    }
}
