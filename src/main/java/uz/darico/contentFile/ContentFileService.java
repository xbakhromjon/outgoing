package uz.darico.contentFile;

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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContentFileService extends AbstractService<ContentFileRepository, InReceiverValidator, ContentFileMapper> {

    private final BaseUtils baseUtils;
    private final String FILE_PATH_LINUX = "/home/xbakhromjon/database";
    private final String GENERATED_FILES_PATH = "/generated";
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
        List<UUID> IDs = contentFiles.stream().map(ContentFile::getId).toList();
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
            FileOutputStream fileOutputStream = new FileOutputStream(new File(url_windows));
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            fileOutputStream.flush();
            ContentFile fileEntity = new ContentFile();
            fileEntity.setPath(url_windows);
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
        baseUtils.writeHtmlAsPdf(path, html);
        return path;
    }
}
