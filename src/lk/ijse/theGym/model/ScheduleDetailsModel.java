package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.ScheduleDetailsDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class ScheduleDetailsModel {
    public static boolean setDetails(List<ScheduleDetailsDTO> scheduleDetailsList) throws SQLException, ClassNotFoundException {
        for (ScheduleDetailsDTO scheduleDetailsDTO : scheduleDetailsList) {
            if (!save(scheduleDetailsDTO)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(ScheduleDetailsDTO scheduleDetailsDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO schedule_details VALUES (?,?,?)",
                scheduleDetailsDTO.getExercises_id(),
                scheduleDetailsDTO.getSteps(),
                scheduleDetailsDTO.getSchedule_id()
        );
    }
}
