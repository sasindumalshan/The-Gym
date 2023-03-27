package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.ScheduleController;
import lk.ijse.theGym.to.Exercises;
import lk.ijse.theGym.to.ScheduleDetails;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScheduleBarController implements Initializable {
    public Pane stepPane;
    public JFXTextField lblSteps;
    public ImageView btnUpdate;
    public ImageView btnDelete;
    public ImageView btnCancel;
    public ImageView btnOk;
    public JFXTextField lblExe;
    public JFXTextField lblExe1;
    public Text txtID;
    public JFXCheckBox exeCheck;
    boolean update =false;
    boolean delete =false;
    public static boolean newUser=true;

    public static ArrayList<ScheduleDetails> scheduleDetails=new ArrayList<>();
    public void setData(String exercises, String id){
        exeCheck.setText(exercises);
        txtID.setText(id);
    }

    public void AcerPaneOnMoseEnter(MouseEvent mouseEvent) {
        if (newUser){
            if (!update|delete){
                btnDelete.setVisible(true);
                btnUpdate.setVisible(true);
            }
        }


    }

    public void AnderPaneOnMoseExit(MouseEvent mouseEvent) {
        btnDelete.setVisible(false);
        btnUpdate.setVisible(false);
    }

    public void stepsKeyReleased(KeyEvent keyEvent) {
        System.out.println(scheduleDetails.size());
        for (int i = 0; i < scheduleDetails.size(); i++) {
            if (scheduleDetails.get(i).getExercises_id().equals(txtID.getText())){
                scheduleDetails.get(i).setSteps(lblSteps.getText());
                scheduleDetails.get(i).setExercises_id(txtID.getText());
                System.out.println(scheduleDetails.get(i).getSteps());
            }
        }
    }

    public void updateOnMouseClick(MouseEvent mouseEvent) {
        exeCheck.setVisible(false);
        lblExe.setVisible(true);
        lblExe.setText(exeCheck.getText());
        btnOk.setVisible(true);
        btnCancel.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        update=true;
        delete=false;
    }

    public void deleteOnMouseClick(MouseEvent mouseEvent) {
        btnOk.setVisible(true);
        btnCancel.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        update=false;
        delete=true;
    }

    public void cancelOnMouseClick(MouseEvent mouseEvent) {
        if (update){
            update=false;
            btnOk.setVisible(false);
            btnCancel.setVisible(false);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
            lblExe.clear();
            lblExe.setVisible(false);
        }
        if (delete){
            delete=false;
            update=false;
            btnOk.setVisible(false);
            btnCancel.setVisible(false);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
        }
    }

    public void checkOnAction(ActionEvent actionEvent) {
        if (exeCheck.isSelected()){
            scheduleDetails.add(new ScheduleDetails(
                    txtID.getText(),
                    lblSteps.getText(),
                    null
            ));
            stepPane.setVisible(true);
        }else {
            for (int i = 0; i < scheduleDetails.size(); i++) {
               if (scheduleDetails.get(i).getExercises_id().equals(txtID.getText())){
                   scheduleDetails.remove(i);
                   break;
               }
            }
            stepPane.setVisible(false);
        }
    }

    public void okOnMouseClick(MouseEvent mouseEvent) {
        if (update){
            update=false;
            update=false;
            btnOk.setVisible(false);
            btnCancel.setVisible(false);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
            try {
                if (ScheduleController.update(new Exercises(
                        txtID.getText(),
                        lblExe.getText()
                ))) {
                    ScheduleFromController.getController().setData();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            exeCheck.setVisible(true);
            exeCheck.setText(lblExe.getText());
            lblExe.clear();
            lblExe.setVisible(false);


        }
        if (delete){
            delete=false;
            update=false;
            try {
                if (ScheduleController.remove(txtID.getText())){
                    ScheduleFromController.getController().setData();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            btnOk.setVisible(false);
            btnCancel.setVisible(false);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stepPane.setVisible(false);
    }
}
