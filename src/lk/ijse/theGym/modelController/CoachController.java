package lk.ijse.theGym.modelController;

import lk.ijse.theGym.to.Coach;
import lk.ijse.theGym.to.Customer;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachController {
    public static boolean idExists(String id) throws SQLException, ClassNotFoundException {
        ResultSet set= CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE coach_id=?",id);
        return set.next();
    }

    public static ResultSet getAll() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM coach");
    }

    public static ResultSet getLastaId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT  coach_id FROM coach ORDER BY LENGTH(coach_id),coach_id");
    }

    public static boolean addCoach(Coach coach) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO coach VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                coach.getCoach_id(),
                coach.getFist_name(),
                coach.getLast_name(),
                coach.getAddress_street(),
                coach.getAddress_city(),
                coach.getAddress_lene(),
                coach.getE_mail(),
                coach.getBirthday(),
                coach.getNic(),
                coach.getContact_number(),
                coach.getGender(),
                coach.getRegister_date(),
                coach.getSalary_id(),
                coach.getCoach_type()


                );
    }

    public static ResultSet getAllForID(String id) throws SQLException, ClassNotFoundException {
        System.out.println("Coach id"+id);

        return CrudUtil.crudUtil("SELECT * FROM coach WHERE coach_id=?",id);
    }

    public static boolean removeCustomer(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM coach WHERE coach_id=?",text);
    }

    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE customer SET fist_name=?,last_name=?,address_city=?,address_street=?,address_lene=?,e_mail=?,contact_number=?,nic=?,birthday=? WHERE customer_id=?",
                customer.getFist_name(),
                customer.getLast_name(),
                customer.getAddress_city(),
                customer.getAddress_street(),
                customer.getAddress_lene(),
                customer.getE_mail(),
                customer.getContact_number(),
                customer.getNic(),
                customer.getBirthday(),
                customer.getCustomer_id()


                );
    }

    public static boolean updateCoach(Coach coach) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE coach SET fist_name=?,last_name=?,address_city=?,address_street=?,address_lene=?,e_mail=?,contact_number=?,nic=?,birthday=? WHERE coach_id=?",
                coach.getFist_name(),
                coach.getLast_name(),
                coach.getAddress_city(),
                coach.getAddress_street(),
                coach.getAddress_lene(),
                coach.getE_mail(),
                coach.getContact_number(),
                coach.getNic(),
                coach.getBirthday(),
                coach.getCoach_id()

                );
    }

    public static ResultSet getSearchIdData(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE coach_id LIKE ?",id+"%");
    }

    public static ResultSet getSearchNameData(String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE fist_name LIKE ? OR last_name LIKE ?",name+"%",name+"%");
    }

    public static ResultSet getSearchAllForID(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT *FROM coach WHERE coach_id=?",id);
    }

    public static ResultSet getCoachCunt() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM coach");
    }

    public static ResultSet getALlId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT coach_id FROM coach WHERE coach_type=?","Company");
    }

    public static ResultSet getDataForId(String valueOf) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT fist_name,last_name ,nic,coach_id,salary_id FROM coach WHERE coach_id=?",valueOf);
    }

    public static ResultSet getSalaryCount(String salary_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM coach WHERE salary_Id=?",salary_id);
    }
}
