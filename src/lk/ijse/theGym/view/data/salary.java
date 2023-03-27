package lk.ijse.theGym.view.data;

public class salary {
    private String id;
    private String salary;
    private String role;
    private String usage;

    public salary() {
    }

    public salary(String id, String salary, String role, String usage) {
        this.id = id;
        this.salary = salary;
        this.role = role;
        this.usage = usage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "salary{" +
                "id='" + id + '\'' +
                ", salary='" + salary + '\'' +
                ", role='" + role + '\'' +
                ", usage='" + usage + '\'' +
                '}';
    }
}
