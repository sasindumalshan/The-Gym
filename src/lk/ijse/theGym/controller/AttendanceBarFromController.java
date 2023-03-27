package lk.ijse.theGym.controller;

import javafx.scene.text.Text;

public class AttendanceBarFromController {
    public Text txtId;
    public Text txtName;
    public Text txtTime;
    public Text txtDate;
    public void setData(String id, String name, String time, String date){
        txtId.setText(id);
        txtName.setText(name);
        txtTime.setText(time);
        txtDate.setText(date);
    }
}
