package lk.ijse.theGym.controller.Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.CoachSalaryDetailsModel;
import lk.ijse.theGym.model.EmployeeSalaryDetailsModel;
import lk.ijse.theGym.model.OrdersModel;
import lk.ijse.theGym.modelController.CustomerPaymentController;
import lk.ijse.theGym.modelController.SupplierOrderDetailsController;
import lk.ijse.theGym.util.DateTimeUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ReportChartsFrom implements Initializable {
    public Text txtReport;
    public LineChart chart;
    public JFXComboBox rBtnSelectMonth;
    public JFXComboBox rBtnSelectYear;
    public JFXRadioButton fiveYearReport;
    public AnchorPane anchorpane;
    public CategoryAxis yAxy;
    public NumberAxis xAxy;
    ArrayList<String> supplierOder = new ArrayList<>();
    ArrayList<String> sumOfEmployeeSalary = new ArrayList<>();
    ArrayList<String> sumOfCoachSalary = new ArrayList<>();
    ArrayList<String> customerOder = new ArrayList<>();
    ArrayList<String> customerPayment = new ArrayList<>();
    ArrayList<String> Profit = new ArrayList<>();
    ArrayList<String> lost = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> year = new ArrayList<>();

    String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};


    private void onlyYearReport(){
        setTitle(rBtnSelectYear.getValue() + " REPORT ");
        clear();
        try {
            for (String year:year){
                String supplierOrder = SupplierOrderDetailsController.getYearSum(year);
                String CustomerOrder = OrdersModel.getYearSum(year);
                customerOder.add(CustomerOrder);
                supplierOder.add(supplierOrder);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        setList(null, setChartSupplierOrder(year, supplierOder), setChartCustomersOrder(year, customerOder),null);
//        setList(setChartLost(m, lost), setChartSupplierOrder(m, supplierOder), setChartCustomersOrder(m, customerOder), setChartProfit(m, Profit));


    }
    /**
     * clear all list
     */
    private void clear() {
        if (!supplierOder.isEmpty()) {
            supplierOder.clear();
        }
        if (!days.isEmpty()) {
            days.clear();
        }
        if (!sumOfCoachSalary.isEmpty()) {
            sumOfCoachSalary.clear();
        }
        if (!sumOfEmployeeSalary.isEmpty()) {
            sumOfEmployeeSalary.clear();
        }
        if (!customerOder.isEmpty()) {
            customerOder.clear();
        }
        if (!customerPayment.isEmpty()) {
            customerPayment.clear();
        }
        if (!lost.isEmpty()) {
            lost.clear();
        }
        if (!Profit.isEmpty()) {
            Profit.clear();
        }
    }

    public void selectMonthOnAction(ActionEvent actionEvent) {
        if (String.valueOf(rBtnSelectMonth.getValue()).equals("") | String.valueOf(rBtnSelectMonth.getValue()) == null) {
            onlyYearReport();
        } else {
            selectMonth(setSelectedYear());
        }
    }

    public void selectYearOnAction(ActionEvent actionEvent) {
        if (String.valueOf(rBtnSelectMonth.getValue()).equals("") | String.valueOf(rBtnSelectMonth.getValue()) == null) {
            onlyYearReport();
        } else {
            selectMonth(setSelectedYear());
        }
    }

    public void fiveYearReportOnAction(ActionEvent actionEvent) {
        if (fiveYearReport.isSelected()) {
            onlyYearReport();
            rBtnSelectMonth.setDisable(true);
            rBtnSelectYear.setDisable(true);
        } else {
            rBtnSelectMonth.setDisable(false);
            rBtnSelectYear.setDisable(false);
            if (String.valueOf(rBtnSelectMonth.getValue()).equals("") | String.valueOf(rBtnSelectMonth.getValue()) == null) {
                onlyYearReport();
            }
            if (!String.valueOf(rBtnSelectMonth.getValue()).equals("") | String.valueOf(rBtnSelectMonth.getValue()) == null) {
                selectMonth(setSelectedYear());
            }
        }
    }

    private void selectMonth(String argYear) {
        clear();
        chart.getData().clear();

        String year = argYear;
        /**
         * set Title
         * */
        setTitle(year + " " + rBtnSelectMonth.getValue() + " " + " REPORT ");

       /* try {


            int i = 0;

            for (String CurrentMonth : allMonth) {
                i++;
                if (CurrentMonth.equals(String.valueOf(rBtnSelectMonth.getValue()))) {
                    month = String.valueOf(i).length() == 1 ? "0" + i : String.valueOf(i);
                }
            }

            for (int j = 0; j < DateTimeUtil.getDays(Integer.valueOf(year), Integer.valueOf(month)); j++) {

                days.add(String.valueOf(j + 1).length() == 1 ? "0" + (j + 1) : String.valueOf(j + 1));

            }
            for (String countOfDay : days) {
                ResultSet set = SupplierOrderDetailsController.getTotalOnDay(year + "-" + month + "-" + countOfDay);
                if (set.next()) {
                    if (set.getString(1) == null) {
                        supplierOder.add("0");
                    } else {
                        supplierOder.add((set.getString(1)));
                    }
                } else {
                    supplierOder.add("0");
                }
            }
            for (String countOfDay : days) {
                String finalTotalOnDay = OrdersModel.sumOrdersByDate(year + "-" + month + "-" + countOfDay);
                customerOder.add(finalTotalOnDay);
            }
            for (String countOfDay : days) {
                ResultSet set = CustomerPaymentController.getFinalTotal(year + "-" + month + "-" + countOfDay);
                if (set.next()) {
                    if (set.getString(1) == null) {
                        customerPayment.add("0");
                    } else {
                        customerPayment.add(set.getString(1));
                    }
                } else {
                    customerPayment.add("0");
                }
            }
            for (int j = 0; j < customerOder.size(); j++) {
                System.out.println(customerPayment.get(j));
                System.out.println(customerOder.get(j));
                double total = Double.parseDouble(customerPayment.get(j)) + Double.parseDouble(customerOder.get(j));
                Profit.add(String.valueOf(total));
            }
            for (String countOfDay : days) {
                String sumOfEmployeeSalaryDetails = EmployeeSalaryDetailsModel.sumByDate(year + "-" + month + "-" + countOfDay);
                sumOfEmployeeSalary.add(sumOfEmployeeSalaryDetails);
            }
            for (String countOfDay : days) {
                String coachSalary = CoachSalaryDetailsModel.sumSalaryByDate(year + "-" + month + "-" + countOfDay);
                sumOfCoachSalary.add(coachSalary);
            }
            for (int j = 0; j < days.size(); j++) {
                int total = Integer.parseInt(sumOfEmployeeSalary.get(j)) + Integer.parseInt(sumOfCoachSalary.get(j)) + Integer.parseInt(supplierOder.get(j));
                lost.add(String.valueOf(total));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }*/

        int currentYear = Integer.parseInt(String.valueOf(rBtnSelectYear.getValue()));
        int currentMonth = getMonth(String.valueOf(rBtnSelectMonth.getValue()));
        int days = DateTimeUtil.getDays(currentYear, currentMonth);

        try {
            for (int i = 1; i <= days; i++) {
                // day in month
                String day = i < 10 ? "0" + i : String.valueOf(i);

                String supplierOrderDetails = SupplierOrderDetailsController.getTotalOnDay(currentYear + "-" + currentMonth + "-" + day);
                supplierOder.add(supplierOrderDetails);

                String customerOrderDetails = CustomerPaymentController.getFinalTotal(currentYear + "-" + currentMonth + "-" + day);
                customerOder.add(customerOrderDetails);

                this.days.add(day);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //setChartLost(this.days, lost);
        //setChartSupplierOrder(this.days, supplierOder);
        //setChartCustomersOrder(this.days, customerOder);
        //setChartProfit(this.days, Profit);

//        setList(
//                setChartLost(this.days, lost),
//                setChartSupplierOrder(this.days, supplierOder),
//                setChartCustomersOrder(this.days, customerOder),
//                setChartProfit(this.days, Profit));

        setList(
                null,
                setChartSupplierOrder(this.days, supplierOder),
                setChartCustomersOrder(this.days, customerOder),
                null);

    }

    private int getMonth(String month) {
        for (int i = 0; i <= allMonth.length; i++) {
            if (allMonth[i].equals(month)) {
                return (i + 1);
            }
        }
        return 0;
    }

    private void setTitle(String title) {
        txtReport.setText(title);
    }

    private String setSelectedYear() {
        String year;
        if (rBtnSelectYear.getValue() == null) {
            year = DateTimeUtil.yearNow();
        } else {
            year = String.valueOf(rBtnSelectYear.getValue());
        }
        return year;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAllMonth();
        setFiveYears();
        anchorpane.setStyle("-fx-background-color: transparent");
        chart.setStyle("-fx-background-color: transparent");
        rBtnSelectMonth.setValue(DateTimeUtil.monthNow());
        rBtnSelectYear.setValue(DateTimeUtil.yearNow());
        selectMonth(setSelectedYear());
//        ReportFromController.setStatus(lost, Profit);
    }

    private void setFiveYears() {

        int currentYear = Integer.parseInt(DateTimeUtil.yearNow());
        for (int i = 0; i < 5; i++) {
            year.add(String.valueOf(currentYear));
            currentYear--;
        }
        rBtnSelectYear.getItems().addAll(year);
    }

    private void setAllMonth() {
        rBtnSelectMonth.getItems().add("");
        rBtnSelectMonth.getItems().addAll(allMonth);
    }

    private void setList(XYChart.Series setChartLost, XYChart.Series setChartSupplierOrder, XYChart.Series setChartCustomersOrder, XYChart.Series setChartProfit) {
        chart.getData().clear();
        yAxy.setAnimated(false);
        yAxy.setTickMarkVisible(false);

        chart.getData().addAll(setChartCustomersOrder, setChartSupplierOrder);
//        chart.getData().addAll(setChartLost, setChartCustomersOrder, setChartProfit, setChartSupplierOrder);
        chart.getXAxis().setAutoRanging(true);


    }

    private XYChart.Series setChartProfit(ArrayList<String> list, ArrayList<String> value) {
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<String, Double>(list.get(i), Double.parseDouble(value.get(i))));
        }
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.setName("Profit");
        return series;

    }

    private XYChart.Series setChartSupplierOrder(ArrayList<String> days, ArrayList<String> value) {

        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < days.size(); i++) {
            series.getData().add(new XYChart.Data<String, Double>(days.get(i), Double.parseDouble(value.get(i))));
        }
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.setName("Supplier Orders");
        return series;
    }

    private XYChart.Series setChartCustomersOrder(ArrayList<String> list, ArrayList<String> value) {
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<String, Double>(list.get(i), Double.parseDouble(value.get(i))));
        }
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.setName("Customer Orders");
        return series;
    }

    private XYChart.Series setChartLost(ArrayList<String> list, ArrayList<String> value) {

        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<String, Double>(list.get(i), Double.parseDouble(value.get(i))));
        }
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.setName("Lost");
        return series;
    }


}
