package lk.ijse.theGym.controller.User;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.bar.ScheduleBarController;
import lk.ijse.theGym.dto.ExerciseDTO;
import lk.ijse.theGym.dto.ScheduleDTO;
import lk.ijse.theGym.dto.ScheduleDetailsDTO;
import lk.ijse.theGym.model.ExerciseModel;
import lk.ijse.theGym.modelController.CoachController;
import lk.ijse.theGym.modelController.CustomerController;
import lk.ijse.theGym.model.ScheduleModel;
import lk.ijse.theGym.to.ScheduleDetails;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleFromController implements Initializable {
    private static ScheduleFromController controller;
    public JFXComboBox comboMember;
    public JFXComboBox comboCoach;
    public JFXButton btnNew;
    public JFXButton btnDone;
    public JFXButton newSchedule;
    public Text coachName;
    public Text memberName;
    public JFXTextField lblExe;
    public VBox vBox;

    public ScheduleFromController() {
        controller = this;
    }

    public static ScheduleFromController getController() {
        return controller;
    }

    private static List<ScheduleDetailsDTO> getScheduleDetailsList(ArrayList<ScheduleDetails> scheduleDetails, ScheduleDTO scheduleDTO) {
        List<ScheduleDetailsDTO> list = new ArrayList<>();
        for (ScheduleDetails details : scheduleDetails) {
            ScheduleDetailsDTO scheduleDetailsDTO = new ScheduleDetailsDTO();
            scheduleDetailsDTO.setSchedule_id(scheduleDTO.getSchedule_id());
            scheduleDetailsDTO.setExercises_id(details.getExercises_id());
            scheduleDetailsDTO.setSteps(details.getSteps());
            list.add(scheduleDetailsDTO);
        }
        return list;
    }

    public void memberOnAction(ActionEvent actionEvent) {
        try {
            ResultSet set = CustomerController.getIdForData(String.valueOf(comboMember.getValue()));
            if (set.next()) {
                memberName.setText(set.getString(2) + " " + set.getString(3));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void coachOnAction(ActionEvent actionEvent) {
        try {
            ResultSet set = CoachController.getAllForID(String.valueOf(comboCoach.getValue()));
            if (set.next()) {
                coachName.setText(set.getString(2) + " " + set.getString(3));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void newOnAction(ActionEvent actionEvent) {

        if (btnNew.getText().equals("ADD")) {
            try {
                ExerciseDTO exerciseDTO = new ExerciseDTO();
                exerciseDTO.setExercises_id(nextId());
                exerciseDTO.setExercises(lblExe.getText());

                if (ExerciseModel.save(exerciseDTO)) {
                    ScheduleFromController.getController().setData();
                    new Alert(Alert.AlertType.CONFIRMATION, "Ok").show();
                }
                lblExe.clear();
                lblExe.setVisible(false);
                btnNew.setText("NEW");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        }
        if (btnNew.getText().equals("NEW")) {
            lblExe.setVisible(true);
            btnNew.setText("ADD");
        }

    }

    private String nextId() {
        try {
            List<String> ids = ExerciseModel.findIdOrderByLength();
            String exitsId = null;
            for (String id : ids) {
                exitsId = id;
            }
            try {
                String[] x0s = exitsId.split("X0");
                int nextId = Integer.parseInt(x0s[1]);
                nextId++;
                return "X0" + nextId;
            } catch (NullPointerException e) {
                return "X01";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public void donOnAction(ActionEvent actionEvent) {
        if (btnDone.getText().equals("Close")) {
            Navigation.close(actionEvent);
        }
        if (btnDone.getText().equals("DONE")) {
            boolean isFullFillArray = true;
            for (int i = 0; i < ScheduleBarController.scheduleDetails.size(); i++) {
                if (ScheduleBarController.scheduleDetails.get(i).getSteps().equals(null)) {
                    new Alert(Alert.AlertType.WARNING, "Chech").show();
                    isFullFillArray = false;
                    break;
                }
            }
            if (isFullFillArray) {
                try {
                    ScheduleDTO scheduleDTO = new ScheduleDTO();
                    scheduleDTO.setSchedule_id(getNextScheduleId());
                    scheduleDTO.setCoach_id(String.valueOf(comboCoach.getValue()));
                    scheduleDTO.setCustomer_id(String.valueOf(comboMember.getValue()));

                    List<ScheduleDetailsDTO> scheduleDetailsDTOS = getScheduleDetailsList(ScheduleBarController.scheduleDetails, scheduleDTO);

                    if (ScheduleModel.placeSchedule(scheduleDTO, scheduleDetailsDTOS)) {
                        setData();
                        new Alert(Alert.AlertType.CONFIRMATION, "OK").show();
                        Navigation.close(actionEvent);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }
        ScheduleBarController.newUser = true;
        coachName.setText("");
        memberName.setText("");
        comboCoach.setVisible(false);
        comboMember.setVisible(false);
        setAllIdsCoach();
        setAllIdsCustomer();

    }

    private String getNextScheduleId() {
        try {
            String existId = null;
            List<String> ids = ScheduleModel.findIdOrderByLength();
            for (String id : ids) {
                existId = id;
            }
            try {
                String[] s = existId.split("S");
                int NextId = Integer.parseInt(s[1]);
                NextId++;
                return "S" + NextId;
            } catch (NullPointerException e) {
                return "S1";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void btnNewScheduleOnAction(ActionEvent actionEvent) {
        ScheduleBarController.newUser = false;
        comboCoach.setVisible(true);
        comboMember.setVisible(true);
        btnDone.setText("DONE");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAllIdsCustomer();
        setAllIdsCoach();
        newSchedule.setVisible(false);
        comboMember.setVisible(false);
        comboCoach.setVisible(false);
        lblExe.setVisible(false);
        setData();
        btnDone.setText("Close");
        setDefaultSchedule();

    }

    private void setDefaultSchedule() {
        String[] exercisesList = {
                "Dead lift",
                "Back squat",
                "Bench Press",
                "Dumbbell romanian dead lift",
                "Kettle bell swing",
                "Suspended push up",
                "Pull up",
                "Medicine ball slam",
                "Swiss Ball Rollout",
                "Banded Good Morning",
                "Hamstring curl",
                "Suspended inverted row",
                "Barbell overhead press",
                "Barbell hip thrust"
        };
        try {
            if (!ScheduleModel.isExistId("X01")) {
                for (String exercise : exercisesList) {
                    ExerciseDTO exerciseDTO = new ExerciseDTO();
                    exerciseDTO.setExercises_id(nextId());
                    exerciseDTO.setExercises(exercise);

                    ExerciseModel.save(exerciseDTO);
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setData() {
        vBox.getChildren().clear();

        try {
            List<ExerciseDTO> exercise = ScheduleModel.findExercise();
            for (ExerciseDTO exerciseDTO:exercise) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/theGym/view/bar/SheduleBar.fxml"));
                Parent root = loader.load();
                ScheduleBarController controller = loader.getController();
                controller.setData(exerciseDTO);
                vBox.getChildren().add(root);
            }

        } catch (IOException e) {
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setAllIdsCoach() {
        comboCoach.getItems().clear();
        ArrayList<String> id = new ArrayList<>();
        try {
            ResultSet set = CoachController.getALlId();
            while (set.next()) {
                id.add(set.getString(1));
            }
            comboCoach.getItems().addAll(id);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setAllIdsCustomer() {
        comboMember.getItems().clear();
        ArrayList<String> id = new ArrayList<>();
        try {
            ResultSet set = CustomerController.getAll();
            while (set.next()) {
                id.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        comboMember.getItems().addAll(id);

    }

    public void AnchorPaneMouseEntered(MouseEvent mouseEvent) {
        newSchedule.setVisible(true);

    }

    public void AnchorPaneMouseExited(MouseEvent mouseEvent) {
        newSchedule.setVisible(false);
    }

    public void closeOnMuseClick(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }
}
