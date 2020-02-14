package invoice;


public class Invoice {
    public String number;
    public String amount;
    public int payableInWeeks;

    public static Invoice testData() {
        Invoice invoice = new Invoice();
        invoice.number = "R99-8541";
        invoice.amount = "22,66 €";
        invoice.payableInWeeks =2;
        return invoice;
    }
}
