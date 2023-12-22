package lk.ijse.theGym.controller.User;

import javafx.scene.text.Text;
import lk.ijse.theGym.dto.projection.AttendanceProjection;

public class AttendanceBarFromController {
    public Text txtId;
    public Text txtName;
    public Text txtTime;
    public Text txtDate;
    public void setData(AttendanceProjection attendanceProjection){
        txtId.setText(attendanceProjection.getId());
        txtName.setText(attendanceProjection.getFistName()+" "+attendanceProjection.getLastName());
        txtTime.setText(attendanceProjection.getTime());
        txtDate.setText(attendanceProjection.getDate());
    }
}
