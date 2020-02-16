import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import customer.Customer;
import invoice.Invoice;
import paymentdata.PaymentData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ZahnarztInvoice {
    private static final String ADDRESS_LINE_1 = "Sendlinger Tor Platz 7";
    private static final String ADDRESS_LINE_2 = "80036 München";
    private static final String PHONE_NUMBER = "089/52031536";
    private static final String EMAIL = "annairahurnaus@gmail.com";
    private static final String WEBSITE = "www.we-wash.com";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String INVOICE_PROVIDER = "ABC medical money GmbH";

    private static Customer customer = Customer.testData();
    private static Invoice invoice = Invoice.testDataDentist();
    private static PaymentData paymentData = PaymentData.testData();

    public static void main(String[] args) throws IOException {
        new ZahnarztInvoice().create();
    }

    private void create() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter("Dentist" + LocalTime.now().toSecondOfDay() + ".pdf"));
        Document document = new Document(pdfDoc);
        document.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, 0.5f));
        document.setProperty(Property.MARGIN_LEFT, 50.0f);
        document.setProperty(Property.MARGIN_RIGHT, 15.0f);

        Image image = new Image(ImageDataFactory.create("src/main/resources/tooth.jpg"));
        image.setFixedPosition(450.0f, 700.0f, 150);
        document.add(image);

        addEmptyLine(document);
        addEmptyLine(document);
        addEmptyLine(document);
        addLeftAlignedLine(document, "Zahnarzt München");
        addLeftAlignedLine(document, "Sonnenstraße 33");
        addLeftAlignedLine(document, "80337 München");

        document.add(new LineSeparator(new SolidLine(1)).setMarginTop(50));
        addAddressLine(document);

        Table addressTable = new Table(new float[]{3, 2});
        addressTable.useAllAvailableWidth();
        addressTable.setFixedLayout();
        addressTable.addCell(getCell(""));
        addressTable.addCell(getCell("ABC-Nr. " + invoice.number).setBold());
        addressTable.addCell(getCell(customer.saluation));
        addressTable.addCell(getCell(""));
        addressTable.addCell(getCell(customer.name));
        addressTable.addCell(getCell("Telefon: " + PHONE_NUMBER));
        addressTable.addCell(getCell(customer.addressLine1));
        addressTable.addCell(getCell("Fax: " + PHONE_NUMBER));
        addressTable.addCell(getCell(customer.addressLine2));
        addressTable.addCell(getCell("E-Mail: " + EMAIL));
        addressTable.addCell(getCell(""));
        addressTable.addCell(getCell("Internet: " + WEBSITE));
        document.add(addressTable);

        addSubjectLine(document);

        Table invoiceDataTable = new Table(new float[]{1, 2, 1, 1});
        invoiceDataTable.useAllAvailableWidth();
        invoiceDataTable.setFixedLayout();
        invoiceDataTable.addCell(getCell("Rechnungsnummer: "));
        invoiceDataTable.addCell(getCell(invoice.number));
        invoiceDataTable.addCell(getCell("Rechnungsdatum: "));
        invoiceDataTable.addCell(getCell(LocalDate.now().format(FORMATTER)));
        invoiceDataTable.addCell(getCell("Abschlagsnummer: "));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell("Steuernummer: "));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell("Behandelte Person: "));
        invoiceDataTable.addCell(getCell("Anna Ira Hurnaus"));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell(""));
        invoiceDataTable.addCell(getCell("Geburtsdatum: "));
        invoiceDataTable.addCell(getCell(customer.birthday.format(FORMATTER)));

        addEmptyLine(document);
        document.add(invoiceDataTable);
        addEmptyLine(document);

        addLeftAlignedLine(document, "Behandlungsdatum " + invoice.dateOfService.format(FORMATTER));
        addEmptyLine(document);

        Table servicesTable = new Table(new float[]{1.2f, 0.8f, 1, 3, 0.5f, 1, 0.7f, 1});
        servicesTable.useAllAvailableWidth();
        servicesTable.setFixedLayout();
        servicesTable.addCell(getSmallCell("Datum").setBold());
        servicesTable.addCell(getSmallCell("Region").setBold());
        servicesTable.addCell(getSmallCell("Nr.").setBold());
        servicesTable.addCell(getSmallCell("Leistungsbeschreibung/Auslagen").setBold());
        servicesTable.addCell(getSmallCell("Bgr.").setBold());
        servicesTable.addCell(getSmallCell("Faktor").setBold());
        servicesTable.addCell(getSmallCell("Anz.").setBold());
        servicesTable.addCell(getSmallCell("EUR").setBold());

        servicesTable.addCell(getSmallCell(invoice.dateOfService.format(FORMATTER)));
        servicesTable.addCell(new Cell(1, 1).add(new Paragraph("17-27,\n36-45")
                .setMultipliedLeading(1.0f))
                .setPaddingBottom(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(11.0f)
                .setBorder(Border.NO_BORDER).setFontSize(10.0f));
        servicesTable.addCell(getSmallCell("1040"));
        servicesTable.addCell(getSmallCell("Professionelle Zahnreinigung"));
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSmallCell("1,499"));
        servicesTable.addCell(getSmallCell("25"));
        servicesTable.addCell(getSmallCell("59,00"));
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSubtotalCell());
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSmallCell("59,00"));
        servicesTable.startNewRow();
        servicesTable.startNewRow();
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getTotalCell());
        servicesTable.addCell(getSmallCell(""));
        servicesTable.addCell(getSmallCell("59,00").setBold());
        document.add(servicesTable);

        addEmptyLine(document);
        addEmptyLine(document);

        PdfFont regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Paragraph paragraph = new Paragraph().addStyle(new Style().setFont(regular).setFontSize(11));
        paragraph.add(new Text("Die Honoraransprüche wurden an die " + INVOICE_PROVIDER + " abgetreten. Bitte " +
                "überweisen Sie den Betrag in Höhe von "));
        paragraph.add(new Text(invoice.amount + " EUR ").setFont(bold));
        paragraph.add(new Text("unter Angabe der "));
        paragraph.add(new Text("ABC-Nr. " + invoice.number).setFont(bold));
        paragraph.add(new Text(" an die u.a. Bankverbindung."));
        paragraph.setMultipliedLeading(1.0f);
        document.add(paragraph);


        Paragraph lastLine = new Paragraph();
        lastLine.add(new LineSeparator(new SolidLine(1)).setWidth(530).setMarginBottom(5));
        lastLine.add("\n");
        lastLine.add(new Text(INVOICE_PROVIDER + " - " + paymentData.name + " - BIC: " + paymentData.bic + " - " +
                "IBAN: " + paymentData.iban)).addStyle(new Style().setFont(regular).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
        lastLine.setFixedPosition(50, 40, 530);
        document.add(lastLine);

        addNumberOfPage(document, pdfDoc.getNumberOfPages());

        document.close();
    }

    private Cell getSmallCell(String text) {
        return getCell(text, 1).setFontSize(10.0f);
    }

    private Cell getTotalCell() {
        return getCell("Rechnungsbetrag: ", 3).setFontSize(11.0f).setBold().setTextAlignment(TextAlignment.RIGHT);
    }

    private Cell getSubtotalCell() {
        return getCell("Zwischensumme Honorar: ", 3).setFontSize(10.0f).setTextAlignment(TextAlignment.RIGHT);
    }

    private Cell getCell(String text) {
        return getCell(text, 1);
    }

    private Cell getCell(String text, int colspan) {
        return new Cell(1, colspan).add(new Paragraph(text).setMultipliedLeading(0.5f))
                .setPaddingBottom(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(11.0f)
                .setBorder(Border.NO_BORDER);
    }

    private void addEmptyLine(Document document) throws IOException {
        addLeftAlignedLine(document, null);
    }

    private void addSubjectLine(Document document) throws IOException {
        Style style = new Style()
                .setMarginTop(25.0f).setMarginBottom(15.0f)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12);
        Paragraph p = new Paragraph();
        p.add(new Text("Rechnung").addStyle(style));
        document.add(p);
    }

    private void addAddressLine(Document document) throws IOException {
        Style style = new Style()
                .setMarginTop(10.0f)
                .setMarginBottom(25.0f)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(10);
        Paragraph p =
                new Paragraph(new Text(INVOICE_PROVIDER + "| " + ADDRESS_LINE_1 + " | " + ADDRESS_LINE_2).addStyle(style));
        document.add(p);
    }

    private void addLeftAlignedLine(Document document, String left) throws IOException {
        Style style = new Style()
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(11).setPadding(0.0f).setMargin(0.0f);
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Text(left == null ? "" : left).addStyle(style));
        document.add(paragraph);
    }

    private void addNumberOfPage(Document document, int numberOfPages) throws IOException {
        Style style = new Style()
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(11).setPadding(0.0f).setMargin(0.0f);
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Tab());
        paragraph.addTabStops(new TabStop(1000, TabAlignment.RIGHT));
        paragraph.add(new Text("Seite 1 von " + numberOfPages).addStyle(style));
        paragraph.setVerticalAlignment(VerticalAlignment.BOTTOM);
        paragraph.setFixedPosition(50, 20, 530);
        document.add(paragraph);
    }

}