package lk.ijse.theGym.controller.User.bar;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.CustomerAddFromController;
import lk.ijse.theGym.controller.User.RegisterFromController;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.model.CoachController;
import lk.ijse.theGym.model.CustomerController;
import lk.ijse.theGym.util.Alert.MyAlert;
import lk.ijse.theGym.util.Navigation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegistrationBarController implements Initializable {

    public Text txtCustId;
    public Text txtName;
    public Text txtEmail;
    public Text txtCity;
    public JFXButton btnRemove;
    public JFXButton btnSchedule;

    public void setData(String id, String name, String email, String city) {
        txtCustId.setText(id);
        txtName.setText(name);
        txtCity.setText(city);
        txtEmail.setText(email);
    }

    public void ViewOnMouseClick(MouseEvent mouseEvent) throws IOException {
        CustomerAddFromController.isViewOnActionSelected = true;
        CustomerAddFromController.id = txtCustId.getText();
        Navigation.popupNavigation("CustomerAddFrom.fxml");


    }

    public void removeOnAction(ActionEvent actionEvent) {
        MyAlert myAlert = new MyAlert("Are You sure", MyAlert.MyButtonType.CONFORMATION);
        myAlert.showAert();
        try {
            if (CustomerController.idExists(txtCustId.getText())) {
                class MyThread extends Thread {

                    @Override
                    public void run() {
                        System.out.println("run");


                        while (!MyAlert.isButtonClick) {
//                            System.out.println("aaaa");
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        while (MyAlert.isButtonClick) {
                            if (MyAlert.isYesSelected) {
                                try {
                                    if (CustomerController.removeCustomer(txtCustId.getText())) {
                                        MyAlert.isButtonClick = false;
                                        MyAlert.isYesSelected = false;
                                        Platform.runLater(() -> RegisterFromController.getInstance().setCustomerData());
                                    }
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                MyAlert.isButtonClick = false;
                                MyAlert.isYesSelected = false;

                            }
                            if (MyAlert.isNoSelected) {
                                System.out.println("no is selected");
                                MyAlert.isButtonClick = false;
                                MyAlert.isNoSelected = false;
                            }
                            break;
                        }
                    }
                }
                MyThread thread = new MyThread();
                thread.start();
            }
            if (CoachController.idExists(txtCustId.getText())) {
                class MyThread extends Thread {

                    @Override
                    public void run() {
                        System.out.println("run");


                        while (!MyAlert.isButtonClick) {
//                            System.out.println("aaaa");
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        while (MyAlert.isButtonClick) {
                            if (MyAlert.isYesSelected) {
                                try {
                                    if (CoachController.removeCustomer(txtCustId.getText())) {
                                        MyAlert.isButtonClick = false;
                                        MyAlert.isYesSelected = false;
                                        Platform.runLater(() -> RegisterFromController.getInstance().setCoachesData());
                                    }
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                MyAlert.isButtonClick = false;
                                MyAlert.isYesSelected = false;

                            }
                            if (MyAlert.isNoSelected) {
                                System.out.println("no is selected");
                                MyAlert.isButtonClick = false;
                                MyAlert.isNoSelected = false;
                            }
                            break;
                        }
                    }
                }
                MyThread thread = new MyThread();
                thread.start();

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        RegisterFromController.getInstance().setCoachCount();
        RegisterFromController.getInstance().setCustomerCount();

    }

    public void onMouseEntered(MouseEvent mouseEvent) {
        if (RegisterFromController.getInstance().rBtnCoach.isSelected()){
            btnSchedule.setVisible(false);
        }else {
            btnSchedule.setVisible(true);
        }

        btnRemove.setVisible(true);
    }

    public void onMouseExited(MouseEvent mouseEvent) {

        btnSchedule.setVisible(false);
        btnRemove.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSchedule.setVisible(false);
        btnRemove.setVisible(false);
    }

    public void SheduleOnAction(ActionEvent actionEvent) {

        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/theGym/view/report/SHEDULE.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        System.out.println(txtCustId.getText());
        hm.put("ID", txtCustId.getText());

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
