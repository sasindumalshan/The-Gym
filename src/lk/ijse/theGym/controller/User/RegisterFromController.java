package lk.ijse.theGym.controller.User;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.bar.RegistrationBarController;
import lk.ijse.theGym.modelController.CoachController;
import lk.ijse.theGym.modelController.CustomerController;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterFromController implements Initializable {
    private static RegisterFromController instance;
    public JFXRadioButton rBtnCustomer;
    public JFXRadioButton rBtnCoach;
    public VBox vBox;
    public JFXTextField search;
    public Text txtAllCoach;
    public Text txtAllCustomer;
    private ArrayList<String> ids = new ArrayList<>();

    public RegisterFromController() {
        instance = this;
    }

    public static RegisterFromController getInstance() {
        return instance;
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("CashiarDashBordFrom.fxml", actionEvent);
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) {
        ids.clear();
        vBox.getChildren().clear();
        if (search.getText().equals("")) {
            vBox.getChildren().clear();
            if (rBtnCoach.isSelected()) {
                setCoachesData();
            }
            if (rBtnCustomer.isSelected()) {
                setCustomerData();
            }
        } else {

            try {
                if (rBtnCustomer.isSelected()) {
                    ResultSet set1 = CustomerController.getSearchIdData(search.getText());
                    while (set1.next()) {
                        boolean isNotDuplicate = true;
                        if (ids.isEmpty()) {
                            ids.add(set1.getString(1));
                        }
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).equals(set1.getString(1))) {
                                isNotDuplicate = false;
                            }
                        }
                        if (isNotDuplicate) {
                            ids.add(set1.getString(1));
                        }
                    }
                    ResultSet set2 = CustomerController.getSearchNameData(search.getText());
                    while (set2.next()) {
                        boolean isNotDuplicate = true;
                        if (ids.isEmpty()) {
                            ids.add(set2.getString(1));
                        }
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).equals(set2.getString(1))) {
                                isNotDuplicate = false;
                            }
                        }
                        if (isNotDuplicate) {
                            ids.add(set2.getString(1));
                        }
                    }
                }
                if (rBtnCoach.isSelected()) {
                    ResultSet set1 = CoachController.getSearchIdData(search.getText());
                    while (set1.next()) {
                        boolean isNotDuplicate = true;
                        if (ids.isEmpty()) {
                            ids.add(set1.getString(1));
                        }
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).equals(set1.getString(1))) {
                                isNotDuplicate = false;
                            }
                        }
                        if (isNotDuplicate) {
                            ids.add(set1.getString(1));
                        }
                    }
                    ResultSet set2 = CoachController.getSearchNameData(search.getText());
                    while (set2.next()) {
                        boolean isNotDuplicate = true;
                        if (ids.isEmpty()) {
                            ids.add(set2.getString(1));
                        }
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).equals(set2.getString(1))) {
                                isNotDuplicate = false;
                            }
                        }
                        if (isNotDuplicate) {
                            ids.add(set2.getString(1));
                        }
                    }
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
        searchDataSet();

    }

    private void searchDataSet() {
        try {
            if (rBtnCustomer.isSelected()) {
                for (int i = 0; i < ids.size(); i++) {
                    ResultSet set=CustomerController.getSearchAllData(ids.get(i));
                    if (set.next()){
                        navigation(set.getString(1),set.getString(2)+" "+set.getString(3),set.getString(5)+" ,"+set.getString(4)+" ,"+set.getString(6),set.getString(7));
                    }
                }
            }
            if (rBtnCoach.isSelected()) {
                for (int i = 0; i < ids.size(); i++) {
                    ResultSet set=CoachController.getSearchAllForID(ids.get(i));
                    if (set.next()){
                        navigation(set.getString(1),set.getString(2)+" "+set.getString(3),set.getString(5)+" ,"+set.getString(4)+" ,"+set.getString(6),set.getString(7));
                    }

                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void newEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        CustomerAddFromController.isAddOnActionSelected = true;
        Navigation.popupNavigation("CustomerAddFrom.fxml");

    }


    public void customerOnAction(ActionEvent actionEvent) {

        setCustomerData();
    }

    public void setCustomerData() {
     vBox.getChildren().clear();
            try {
                ResultSet set = CustomerController.getAll();
                while (set.next()) {
                    navigation(set.getString(1), set.getString(2) + " " + set.getString(3), set.getString(5) + " ," + set.getString(4) + " ," + set.getString(6), set.getString(7));
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }



    public void CoachesOnAction(ActionEvent actionEvent) {
        setCoachesData();
    }

    public void setCoachesData() {
        vBox.getChildren().clear();
        try {
            ResultSet set = CoachController.getAll();
            while (set.next()) {
                navigation(set.getString(1), set.getString(2) + " " + set.getString(3), set.getString(5) + " ," + set.getString(4) + " ," + set.getString(6), set.getString(7));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    private void navigation(String id, String name, String city, String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/theGym/view/bar/RegistrationBarFrom.fxml"));
            Parent root = loader.load();
            RegistrationBarController controller = loader.getController();
            controller.setData(id, name, email, city);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rBtnCustomer.fire();
        setCustomerCount();
        setCoachCount();
    }

    public void setCoachCount() {
        try {
            ResultSet set=CoachController.getCoachCunt();
            if (set.next()){
                txtAllCoach.setText(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setCustomerCount() {
        try {
            ResultSet set=CustomerController.getCustomerCount();
            if (set.next()){
                txtAllCustomer.setText(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void newScheduleOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.popupNavigation("SheduleFrom.fxml");
    }
}
