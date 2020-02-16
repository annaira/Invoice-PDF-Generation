


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import customer.Customer;
import invoice.Invoice;
import paymentdata.PaymentData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class KrankengymnastikInvoice {
    private static final String KRANKENGYMNASTIK_PDF = "./KrankengymnastikInvoice";
    private static final String ADDRESS_LINE_1 = "Sendlinger Tor Platz 7";
    private static final String ADDRESS_LINE_2 = "80036 München";
    private static final String PHONE_NUMBER = "089/52031536";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static Customer customer = Customer.testData();
    private static Invoice invoice = Invoice.testData();
    private static PaymentData paymentData = PaymentData.testData();

    public static void main(String[] args) throws IOException {
        new KrankengymnastikInvoice().create();
    }

    private void create() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(KRANKENGYMNASTIK_PDF + LocalTime.now().toSecondOfDay() + ".pdf"));
        Document document = new Document(pdfDoc);
        document.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, 0.5f));


        addTitleLine(document, "Krankengymnastik am Sendlinger Tor");
        addTitleLine(document, "Manuel Müller");

        addLeftAndRightAlignedLine(document, ADDRESS_LINE_1, "IK 694471568");
        addLeftAndRightAlignedLine(document, ADDRESS_LINE_2, "");
        addLeftAndRightAlignedLine(document, PHONE_NUMBER, "annairahurnaus@gmail.com");
        addLeftAndRightAlignedLine(document, PHONE_NUMBER, "https://we-wash.com/");

        addAddressLine(document);

        addLeftAlignedLine(document, customer.saluation);
        addLeftAlignedLine(document, customer.name);
        addLeftAlignedLine(document, customer.addressLine1);
        addEmptyLine(document);
        addLeftAlignedLine(document, customer.addressLine2);
        addEmptyLine(document);
        addEmptyLine(document);
        addEmptyLine(document);
        addRightAlignedLine(document, LocalDate.now().format(FORMATTER));
        addLeftAlignedLine(document, "-");
        addEmptyLine(document);
        addSubjectLine(document, "Rechnung Nr. " + invoice.number);
        addEmptyLine(document);
        addLeftAlignedLines(document, "Versicherte einer gesetzlichen Krankenkasse müssen nach §32 SGB V eine " +
                "Zuzahlung zu den Kosten ihrer Behandlung leisten.");
        addEmptyLine(document);
        addLeftAlignedLines(document,
                "Aufgrund der Verordnung von Herrn Dr. med. Manuel Müller vom " + invoice.dateOfService.format(FORMATTER) + " erlauben wir uns, den Eigenanteil an einer Heilmittelbehandlung für Frau Anna Ira Hurnaus zu" +
                " berechnen:");
        addEmptyLine(document);
        addAmountLine(document, "Betrag: " + invoice.amount);
        addAmountHintLine(document);
        addEmptyLine(document);
        addEmptyLine(document);
        addLeftAlignedLines(document,
                "Den Eigenanteil überweisen Sie bitte bis zum " + LocalDate.now().plusWeeks(invoice.payableInWeeks).format(FORMATTER) + " unter Angabe unserer Rechnungsnummer " + invoice.number + " auf das unten angegebene Konto.");
        addEmptyLine(document);
        addEmptyLine(document);
        addLeftAlignedLine(document, paymentData.name);
        addLeftAlignedLine(document, "Kontoinhaber: " + paymentData.holder);
        addLeftAlignedLine(document, "Bankleitzahl: " + paymentData.blz + ", Kontonummer: " + paymentData.accountNumber);
        addLeftAlignedLine(document, "IBAN " + paymentData.iban);
        addLeftAlignedLine(document, "BIC " + paymentData.bic);
        addLeftAlignedLine(document, "Steuernummer " + paymentData.taxNumber);
        document.close();
    }

    private void addEmptyLine(Document document) throws IOException {
        addLeftAlignedLine(document, null);
    }

    private void addSubjectLine(Document document, String subject) throws IOException {
        Style style = new Style()
                .setMarginBottom(35.0f)
                .setMarginTop(15.0f)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(14);
        Paragraph p = new Paragraph(new Text(subject).addStyle(style));
        document.add(p);
    }

    private void addAmountLine(Document document, String amount) throws IOException {
        Style style = new Style()
                .setMarginTop(15.0f).setMarginBottom(5.0f)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12);
        Paragraph p = new Paragraph(new Text(amount).addStyle(style));
        document.add(p);
    }

    private void addAmountHintLine(Document document) throws IOException {
        Style style = new Style()
                .setMarginBottom(15.0f)
                .setMarginTop(5.0f)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(10);
        Paragraph p = new Paragraph(new Text("(Steuerfreie Leistungen nach §4 Nr.14 UStG)").addStyle(style));
        document.add(p);
    }

    private void addAddressLine(Document document) throws IOException {
        Style style = new Style()
                .setMarginTop(30.0f)
                .setMarginBottom(25.0f)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(10).setUnderline();
        Paragraph p = new Paragraph(new Text("Physiotherapie, " + ADDRESS_LINE_1 + ", " + ADDRESS_LINE_2).addStyle(style));
        document.add(p);
    }

    private void addTitleLine(Document document, String text) throws IOException {
        Style style = new Style()
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(16);
        Paragraph preface = new Paragraph().add(new Paragraph(new Text(text).addStyle(style)));
        document.add(preface);
    }

    private void addLeftAlignedLines(Document document, String left) throws IOException {
        Style style = new Style()
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12);
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Text(left == null ? "" : left).addStyle(style));
        paragraph.setMultipliedLeading(1.0f);
        document.add(paragraph);
    }

    private void addLeftAlignedLine(Document document, String left) throws IOException {
        addLeftAndRightAlignedLine(document, left, null);
    }

    private void addRightAlignedLine(Document document, String right) throws IOException {
        addLeftAndRightAlignedLine(document, null, right);
    }

    private void addLeftAndRightAlignedLine(Document document, String left, String right) throws IOException {
        Style style = new Style()
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(12).setPadding(0.0f).setMargin(0.0f);
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Text(left == null ? "" : left).addStyle(style));
        paragraph.add(new Tab());
        paragraph.addTabStops(new TabStop(1000, TabAlignment.RIGHT));
        paragraph.add(new Text(right == null ? "" : right).addStyle(style));
        document.add(paragraph);
    }

}