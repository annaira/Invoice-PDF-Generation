package customer;

import java.time.LocalDate;

public class Customer {
    public String saluation;
    public String name;
    public String addressLine1;
    public String addressLine2;
    public LocalDate birthday;

    public static Customer testData() {
        Customer customer = new Customer();
        customer.saluation = "Frau";
        customer.name = "Anna Ira Hurnaus";
        customer.addressLine1 = "Landsberger Straße 14";
        customer.addressLine2 = "D-80339 München";
        customer.birthday = LocalDate.of(1987, 12, 27);
        return customer;
    }
}
