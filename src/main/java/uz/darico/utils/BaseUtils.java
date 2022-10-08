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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.darico.confirmative.ConfStatus;
import uz.darico.exception.exception.UniversalException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class BaseUtils {

//    public static void main(String[] args) throws IOException {
//        writeHtmlAsPdf("/home/xbakhromjon/work/test.pdf", "<p>Hello</p>");
//    }

    public UUID strToUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new UniversalException("%s ID UUID formatda bo'lishi kerak.".formatted(id),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public Long strToLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (Exception e) {
            throw new UniversalException("%s Long formatda bo'lishi kerak", HttpStatus.BAD_REQUEST);
        }
    }

    public UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }

    public static byte[] convertUUIDToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public String convertConfStatusCodeToStr(Integer code) {
        EnumSet<ConfStatus> confStatuses = EnumSet.allOf(ConfStatus.class);
        for (ConfStatus confStatus : confStatuses) {
            if (confStatus.getCode().equals(code)) {
                return confStatus.getName();
            }
        }
        throw new UniversalException("%s confStatus code incorrect".formatted(code), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <E, D> ResponsePage<D> toResponsePage(Page<E> page, List<D> content) {
        ResponsePage<D> responsePage = new ResponsePage<>();
        responsePage.setNumberOfElements(page.getNumberOfElements());
        responsePage.setNumber(page.getNumber());
        responsePage.setTotalPages(page.getTotalPages());
        responsePage.setContent(content);
        responsePage.setTotalElements(page.getTotalElements());
        responsePage.setSize(page.getSize());
        return responsePage;
    }

    public <D> ResponsePage<D> toResponsePage(List<D> content, Integer page, Integer size, Integer totalElements) {
        ResponsePage<D> responsePage = new ResponsePage<>();
        responsePage.setNumber(page);
        responsePage.setContent(content);
        responsePage.setTotalElements(totalElements);
        responsePage.setSize(size);
        return responsePage;
    }


    public void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new java.io.File(path));
    }


    public void writeHtmlAsPdf(String path, String html) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        PdfWriter writer = new PdfWriter(fileOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        PageSize pageSize = PageSize.A5;
        pdfDocument.setDefaultPageSize(pageSize);
        ConverterProperties properties = new ConverterProperties();
        HtmlConverter.convertToPdf(html, pdfDocument, properties);
    }

}
