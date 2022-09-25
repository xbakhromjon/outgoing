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
@AllArgsConstructor
@Entity
@Table(name = "content_file")
public class ContentFile extends Auditable {
    private String path;
    private String originalName;
    private String generatedName;
    private Double size;
}
