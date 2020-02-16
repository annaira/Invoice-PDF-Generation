package invoice;


import java.time.LocalDate;

public class Invoice {
    public String number;
    public String amount;
    public int payableInWeeks;
    public LocalDate dateOfService;

    public static Invoice testData() {
        Invoice invoice = new Invoice();
        invoice.number = "R99-8541";
        invoice.amount = "22,66 €";
        invoice.payableInWeeks = 2;
        invoice.dateOfService = LocalDate.of(2020, 1, 20);
        return invoice;
    }

    public static Invoice testDataDentist() {
        Invoice invoice = new Invoice();
        invoice.number = "5-85214-96325874";
        invoice.amount = "59,00";
        invoice.dateOfService = LocalDate.of(2018, 8, 30);
        return invoice;
    }
}
