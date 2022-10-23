package uz.darico.contentFile;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.darico.base.service.AbstractService;
import uz.darico.exception.exception.UniversalException;
import uz.darico.inReceiver.InReceiverValidator;
import uz.darico.utils.BaseUtils;

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
    private final String FILE_PATH_LINUX = "/home/database/files";
    private final String GENERATED_FILES_PATH_LINUX = "/generated";
    private final String GENERATED_FILES_PATH_WINDOWS = "\\generated";
    private final String FILE_PATH_WINDOWS = "";
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
        String folder_linux = FILE_PATH_LINUX + "/" + orgID;
        String folder_windows = FILE_PATH_WINDOWS + "\\" + orgID;
        Path path = Path.of(folder_windows);
        File file2 = new File(folder_windows);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        MultipartFile file = request.getFile("file");
        if (file != null) {
            if (file.getSize() > 100 * 1024 * 1024) {
                throw new UniversalException("File hajmi 100 mb dan kichik bo'lishi kerak", HttpStatus.BAD_REQUEST);
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
            String url_linux = folder_linux + "/" + generatedName;
            String url_windows = folder_windows + "\\" + generatedName;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(url_linux));
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            fileOutputStream.flush();
            ContentFile fileEntity = new ContentFile();
            fileEntity.setPath(url_linux);
            fileEntity.setContentType(contentType);
            fileEntity.setSize(size);
            fileEntity.setOriginalName(originalFilename);
            fileEntity.setGeneratedName(generatedName);
            ContentFile saved = repository.save(fileEntity);
            return ResponseEntity.ok(saved.getId());
        } else {
            throw new UniversalException("File null bo'lishi mumkin emas", HttpStatus.BAD_REQUEST);
        }
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
        String path_linux = FILE_PATH_LINUX + GENERATED_FILES_PATH_LINUX;
        String path_windows = FILE_PATH_WINDOWS + GENERATED_FILES_PATH_WINDOWS;
        Path pathObj_linux = Path.of(path_linux);
        Path pathObj_windows = Path.of(path_windows);
        if (!Files.exists(pathObj_linux)) {
            try {
                Files.createDirectories(pathObj_linux);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String generatedName = UUID.randomUUID().toString() + ".pdf";
        path_linux = path_linux + "/" + generatedName;
        path_windows = path_windows + "\\" + generatedName;
        baseUtils.writeHtmlAsPdf(path_linux, html);
        return path_linux;
    }

    public String generateQRCode(String data, Integer width, Integer height) {
        String path_linux = FILE_PATH_LINUX + GENERATED_FILES_PATH_LINUX + "/qrcode";
        String path_windows = FILE_PATH_WINDOWS + GENERATED_FILES_PATH_WINDOWS + "\\qrcode";
        Path pathObj_linux = Path.of(path_linux);
        Path pathObj_windows = Path.of(path_windows);
        if (!Files.exists(pathObj_linux)) {
            try {
                Files.createDirectories(pathObj_linux);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        path_linux = path_linux + "/" + UUID.randomUUID() + ".png";
        path_windows = path_windows + "\\" + UUID.randomUUID() + ".png";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        try {
            baseUtils.generateQRcode(data, path_linux, "UTF-8", hashMap, height, width);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path_linux;
    }

    public ContentFile create(String path) {
        ContentFile contentFile = new ContentFile();
        contentFile.setPath(path);
        contentFile.setContentType("application/pdf");
        ContentFile saved = repository.save(contentFile);
        return saved;
    }
}
