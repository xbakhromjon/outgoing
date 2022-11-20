package uz.bakhromjon.contentFile.dto;

/**
 * @author : Bakhromjon Khasanboyev
 **/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContentFileGetDTO {
    private String path;
    private String originalName;
    private String generatedName;
    private Long size;
    private String contentType;
}
