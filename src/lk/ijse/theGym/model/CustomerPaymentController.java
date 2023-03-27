package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.CustomerPayment;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.DateTimeUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerPaymentController {
    public static ResultSet getFinalTotal(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(monthly_fees) FROM customer_payment WHERE date=?", date);
    }

    public static ResultSet getFinalTotalOnYear(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT monthly_fees FROM customer_payment WHERE date LIKE ?");
        statement.setString(1, year + "%");
        return statement.executeQuery();

    }

    public static ResultSet getMonthlyReport(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(monthly_fees)FROM customer_payment WHERE date LIKE ?", date);
    }


    public static boolean isAlreadyPay(String id) throws SQLException, ClassNotFoundException {
        String[] split = DateTimeUtil.dateNow().split("-");
        String date=split[0];
        ResultSet set = CrudUtil.crudUtil("SELECT customer_id FROM customer_payment WHERE customer_id=? AND month=? AND year =?", id, DateTimeUtil.monthNow(),date);
        return set.next();
    }

    public static ResultSet getPaymentDetails(String year,String month) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_payment.customer_id,customer.fist_name,customer.last_name,customer_payment.monthly_fees,customer_payment.date FROM customer_payment INNER JOIN customer ON customer_payment.customer_id = customer.customer_id WHERE customer_payment.year=? AND month=?",year,month );
    }

    public static boolean addPayment(CustomerPayment customerPayment) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO customer_payment VALUES (?,?,?,?,?)",
                customerPayment.getMonthly_fees(),
                customerPayment.getDate(),
                customerPayment.getMonth(),
                customerPayment.getCustomer_id(),
                customerPayment.getYear()
        );
    }

    public static ResultSet getSearchNameResult(String name, String month, String year) throws SQLException, ClassNotFoundException {
        return  CrudUtil.crudUtil("SELECT customer_payment.customer_id,customer.fist_name,customer.last_name,customer_payment.monthly_fees,customer_payment.date FROM customer_payment INNER JOIN customer ON customer_payment.customer_id = customer.customer_id WHERE customer_payment.year=? AND month=? AND customer.fist_name LIKE ? OR customer_payment.year=? AND customer_payment.month=? AND customer.last_name LIKE ?",year,month,name+"%",year,month,name+"%");
    }

    public static ResultSet getSearchIdResult(String id, String month, String year) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_payment.customer_id,customer.fist_name,customer.last_name,customer_payment.monthly_fees,customer_payment.date FROM customer_payment INNER JOIN customer ON customer_payment.customer_id = customer.customer_id WHERE customer_payment.year=? AND month=? AND customer_payment.customer_id LIKE ?",year,month,id+"%");
    }

    public static ResultSet getIdDetails(String id, String year, String month) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_payment.customer_id,customer.fist_name,customer.last_name,customer_payment.monthly_fees,customer_payment.date FROM customer_payment INNER JOIN customer ON customer_payment.customer_id = customer.customer_id WHERE customer_payment.year=? AND month=? AND customer_payment.customer_id = ?",year,month,id);
    }
}
