package customer;

public class Customer {
    public String saluation;
    public String name;
    public String addressLine1;
    public String addressLine2;

    public static Customer testData() {
        Customer customer = new Customer();
        customer.saluation = "Frau";
        customer.name = "Anna Ira Hurnaus";
        customer.addressLine1 = "Landsberger Straße 14";
        customer.addressLine2 = "D-80339 München";
        return customer;
    }
}
