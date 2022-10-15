package uz.darico.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import uz.darico.confirmative.dto.ConfirmativePDFDTO;
import uz.darico.missive.dto.PDFDTO;
import uz.darico.sender.dto.SenderPDFDTO;
import uz.darico.signatory.dto.SignatoryPDFDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 15/10/22, Sat, 17:25
 **/
public class Test {
    private static final String FILE_PATH_LINUX = "/home/xbakhromjon/database";
    private static final String GENERATED_FILES_PATH = "/generated";

    public static void main(String[] args) {
        String content = "<p> Ўзбекистон Республикаси Президентининг 2018 йил 19 февралдаги\n" +
                "“Ахборот технологиялари ва коммуникациялари соҳасини янада такомиллаштириш чора-тадбирлари тўғрисида”ги ПФ-5349-сон Фармонига мувофиқ “Ахборот-коммуникация технологияларини ривожлантириш маркази” давлат унитар корхонасининг асосий вазифаси этиб, “Электрон ҳокимият” ахборот тизимини яратиш, жорий этиш белгиланган. “Давлат харидлари тўғрисида”ги Ўзбекистон Республикаси қонунинг 67-моддасига асосан вилоят ҳокимлиги ҳузуридаги “Ахборот-коммуникация технологияларини ривожлантириш маркази” давлат унитар корхонаси “Ягона етказиб берувчи” сифатида реестрга киритилган.\n" +
                "вилоят ҳокимлиги ҳузуридаги “Ахборот-коммуникация технологияларини ривожлантириш маркази” ДУК туманлар ва шаҳарлар ҳокимликларида “Электрон ҳокимият” ахборот тизимларини жорий этишни мувофиқлаштириш мақсадида масофавий ҳамда доимий жойида хизмат кўрсатиш, шунингдек, ахборот хавфсизлигини таъминлаш борасидаги барча вазифаларни бажариб, хизматларни амалга ошириб келмоқда.\n" +
                "Юқоридагиларни инобатга олиб, Сиздан, ҳокимликда ахборот технологиялари ва коммуникациялар соҳасини ривожлантириш ҳамда техник қўллаб-қувватлаш мақсадида вилоят ҳокимлиги ҳузуридаги “Ахборот-коммуникация технологияларини ривожлантириш маркази” давлат унитар корхонаси билан келишган ҳолда қилинган ҳисоб-китобларга асосан 2020 йилда кўрсатиладиган хизматлар учун жами\n" +
                "84,0 млн.сўм тўловларни ажратишингиз сўралади.\n" +
                "\n" +
                "Амалга оширилган ҳамда амалга ошириладиган ишлар юзасидан тегишли ҳисоб-китоблар, ____ варақда илова қилинади.\n</p>";
        SignatoryPDFDTO signatoryPDFDTO = new SignatoryPDFDTO("Вилоят ҳокими биринчи ўринбосари", "/home/xbakhromjon/Pictures/qrcode.png", "Ф.Умаров");
        SenderPDFDTO senderPDFDTO = new SenderPDFDTO("Вилоят ҳокими биринчи ўринбосари", "/home/xbakhromjon/Pictures/qrcode.png", "Ф.Умаров");
        ConfirmativePDFDTO confirmativePDFDTO1 = new ConfirmativePDFDTO("Вилоят ҳокими биринчи ўринбосари", "/home/xbakhromjon/Pictures/qrcode.png", "Ф.Умаров");
        ConfirmativePDFDTO confirmativePDFDTO2 = new ConfirmativePDFDTO("Вилоят ҳокими биринчи ўринбосари", "/home/xbakhromjon/Pictures/qrcode.png", "Ф.Умаров");
        List<ConfirmativePDFDTO> confirmativePDFDTOs = List.of(confirmativePDFDTO1, confirmativePDFDTO2);
        PDFDTO pdfdto = new PDFDTO("/home/xbakhromjon/Pictures/fishka.jpg", LocalDate.now(), "1663", content, signatoryPDFDTO, senderPDFDTO, confirmativePDFDTOs);
        boolean result = generatePDF(pdfdto);

    }

    public static boolean generatePDF(PDFDTO pdfdto) {
        String fishka = String.format("   <img src=%s", pdfdto.getFishkaPath())
                + " width=\"100%\" height=\"140px\">";
        String dateNumber = String.format("<strong>%s</strong>", pdfdto.getDate()) +
                String.format("<strong>№ %s</strong> <br> <br>", pdfdto.getNumber());
        SignatoryPDFDTO signatoryPDFDTO = pdfdto.getSignatoryPDFDTO();
//        String signatory = String.format("<p style=\"float:left\">%s</p>", signatoryPDFDTO.getFullPosition()) + ;
        String html = fishka + dateNumber + pdfdto.getContent();
        writeAsPDF(html);
        return true;
    }

    public static String writeAsPDF(String html) {
        String path = FILE_PATH_LINUX + GENERATED_FILES_PATH;
        Path pathObj = Path.of(path);
        if (!Files.exists(pathObj)) {
            try {
                Files.createDirectories(pathObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String generatedName = UUID.randomUUID().toString() + ".pdf";
        path = path + "/" + generatedName;
        writeHtmlAsPdf(path, html);
        return path;
    }

    public static void writeHtmlAsPdf(String path, String html) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            PdfWriter writer = new PdfWriter(fileOutputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            PageSize pageSize = PageSize.A5;
            pdfDocument.setDefaultPageSize(pageSize);
            ConverterProperties properties = new ConverterProperties();
            HtmlConverter.convertToPdf(html, pdfDocument, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new java.io.File(path));
    }


}
