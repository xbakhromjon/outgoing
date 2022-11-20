package uz.bakhromjon.contentFile;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.bakhromjon.base.service.AbstractService;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.inReceiver.InReceiverValidator;
import uz.bakhromjon.utils.BaseUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContentFileService extends AbstractService<ContentFileRepository, InReceiverValidator, ContentFileMapper> {

    private final BaseUtils baseUtils;
    @Value("${app.file.upload.path}")
    private String FILE_UPLOAD_PATH;

    @Value("${app.file.generated.path}")
    private String GENERATED_FILE_PATH;
    private final ServletContext servletContext;

    public ContentFileService(ContentFileRepository repository, InReceiverValidator validator, ContentFileMapper mapper,
                              BaseUtils baseUtils, ServletContext servletContext) {
        super(repository, validator, mapper);
        this.baseUtils = baseUtils;
        this.servletContext = servletContext;
    }


    public List<ContentFile> refresh(List<UUID> baseFileIDs, List<ContentFile> trashContentFiles) {
        List<ContentFile> newBaseFiles = repository.findByIDs(baseFileIDs);
        deleteAll(trashContentFiles);
        return newBaseFiles;
    }


    public void deleteAll(List<ContentFile> contentFiles) {
        List<UUID> IDs = contentFiles.stream().map(ContentFile::getId).collect(Collectors.toList());
        repository.deleteFromRelatedTable(IDs);
        repository.deleteAll(IDs);
    }

    public List<ContentFile> getContentFiles(List<UUID> baseFileIDs) {
        return repository.findByIDs(baseFileIDs);
    }

    public ContentFile getContentFile(UUID ID) {
        Optional<ContentFile> optional = repository.findById(ID);
        if (optional.isEmpty()) {
            ContentFile saved = repository.save(new ContentFile());
            return saved;
        } else {
            return optional.get();
        }
    }

    public List<ContentFile> saveAll(List<ContentFile> contentFiles) {
        return repository.saveAll(contentFiles);
    }

    public ResponseEntity<UUID> upload(String orgId, MultipartHttpServletRequest request) throws IOException {
        Long orgID = baseUtils.strToLong(orgId);
        String folder = FILE_UPLOAD_PATH + "/" + orgID;
        Path path = Path.of(folder);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        MultipartFile file = request.getFile("file");
        if (file == null) {
            throw new UniversalException("File cannot be null", HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String extention = "";
        if (contentType.contains("pdf")) {
            extention = ".pdf";
        } else if (contentType.contains("png")) {
            extention = ".png";
        } else if (contentType.contains("jpg")) {
            extention = ".jpg";
        } else if (contentType.contains("jpeg")) {
            extention = ".jpeg";
        } else if (contentType.contains("word")) {
            extention = ".docx";
        }
        String generatedName = UUID.randomUUID() + extention;
        String url = folder + "/" + generatedName;
        FileOutputStream fileOutputStream = new FileOutputStream(new File(url));
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        fileOutputStream.flush();
        ContentFile fileEntity = new ContentFile();
        fileEntity.setPath(url);
        fileEntity.setContentType(contentType);
        fileEntity.setSize(size);
        fileEntity.setOriginalName(originalFilename);
        fileEntity.setGeneratedName(generatedName);
        ContentFile saved = repository.save(fileEntity);
        return ResponseEntity.ok(saved.getId());
    }

    public ResponseEntity<InputStreamResource> view(String id) throws FileNotFoundException {
        UUID ID = baseUtils.strToUUID(id);
        ContentFile fileEntity = getContentFile(ID);
        boolean exists = Files.exists(Path.of(fileEntity.getPath()));
        if (!exists) {
            throw new UniversalException(String.format("File not found with ID %s", id), HttpStatus.NOT_FOUND);
        }
        File send = new File(fileEntity.getPath());
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + fileEntity.getOriginalName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(send));
        return ResponseEntity.ok().headers(headers).contentLength(send.length()).contentType(MediaType.parseMediaType(fileEntity.getContentType())).body(resource);
    }

    public ResponseEntity<Boolean> download(String id, HttpServletResponse response) throws IOException {
        UUID ID = baseUtils.strToUUID(id);
        ContentFile fileEntity = getPersist(ID);
        String mimeType = URLConnection.guessContentTypeFromName(fileEntity.getOriginalName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        File file = new File(fileEntity.getPath());
        response.setContentType(mimeType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileEntity.getOriginalName());
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        return ResponseEntity.ok(true);
    }


    public ContentFile getPersist(UUID ID) {
        Optional<ContentFile> optional = repository.findById(ID);
        return optional.orElseThrow(() -> {
            throw new UniversalException("File Not Found", HttpStatus.BAD_REQUEST);
        });
    }

    public List<ContentFile> getAll(UUID ID) {
        return repository.getAll(ID);
    }

    public String writeAsPDF(String html) {
        Path path = Path.of(GENERATED_FILE_PATH);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String generatedName = UUID.randomUUID().toString() + ".pdf";
        String gen_file_path = GENERATED_FILE_PATH + "/" + generatedName;
        baseUtils.writeHtmlAsPdf(gen_file_path, html);
        return gen_file_path;
    }

    public String generateQRCode(String data, Integer width, Integer height) {
        String qrCodePath =  GENERATED_FILE_PATH + "/qrcode";
        Path path = Path.of(qrCodePath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        qrCodePath = qrCodePath + "/" + UUID.randomUUID() + ".png";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        try {
            baseUtils.generateQRcode(data, qrCodePath, "UTF-8", hashMap, height, width);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCodePath;
    }

    public ContentFile create(String path) {
        ContentFile contentFile = new ContentFile();
        contentFile.setPath(path);
        contentFile.setContentType("application/pdf");
        return repository.save(contentFile);
    }
}
