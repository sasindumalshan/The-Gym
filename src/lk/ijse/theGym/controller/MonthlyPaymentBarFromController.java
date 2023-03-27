package lk.ijse.theGym.controller;

import javafx.scene.text.Text;

public class MonthlyPaymentBarFromController {

    public Text txtName;
    public Text txtDate;
    public Text txtFees;
    public Text txtId;
    public void setData(String id,String name,String fees,String date){
          txtId.setText(id);
          txtName.setText(name);
          txtFees.setText(fees);
          txtDate.setText(date);
    }
}
