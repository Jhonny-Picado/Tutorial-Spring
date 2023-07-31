package payroll;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataPreDefinitions {

    /* Employees */

    @Value("${payroll.employee1.firstName}")
    private String firstName1;

    @Value("${payroll.employee1.lastName}")
    private String lastName1;

    @Value("${payroll.employee1.role}")
    private String role1;

    @Value("${payroll.employee2.firstName}")
    private String firstName2;

    @Value("${payroll.employee2.lastName}")
    private String lastName2;

    @Value("${payroll.employee2.role}")
    private String role2;

    public String getFirstName1() {
        return firstName1;
    }

    public String getLastName1() {
        return lastName1;
    }

    public String getRole1() {
        return role1;
    }

    public String getFirstName2() {
        return firstName2;
    }

    public String getLastName2() {
        return lastName2;
    }

    public String getRole2() {
        return role2;
    }

}