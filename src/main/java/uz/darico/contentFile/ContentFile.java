package uz.darico.contentFile;

import lombok.*;
import uz.darico.base.entity.Auditable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.sql.rowset.spi.SyncResolver;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class ContentFile extends Auditable {
    private String path;
    private String originalName;
    private String generatedName;
    private Long size;
    private String contentType;
}
