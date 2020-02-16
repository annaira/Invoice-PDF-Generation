package paymentdata;

public class PaymentData {
    public String name;
    public String holder;
    public String accountNumber;
    public String blz;
    public String iban;
    public String bic;
    public String taxNumber;


    public static PaymentData testData() {
        PaymentData paymentData = new PaymentData();
        paymentData.name = "Sparkasse Baden-Baden Gaggenau";
        paymentData.holder = "Manuel Maier";
        paymentData.accountNumber = "7117138110";
        paymentData.blz = "66250030";
        paymentData.iban = "DE80662500307117138110";
        paymentData.bic = "SOLADES1BAD";
        paymentData.taxNumber = "149 852 96587";
        return paymentData;
    }

}
