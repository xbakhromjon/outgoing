package uz.darico.template;

import lombok.*;
import org.hibernate.annotations.Type;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.entity.Auditable;
import uz.darico.contentFile.ContentFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Template extends Auditable {
    private Long orgID;
    private Long workPlaceID;
    private Long userID;
    @Type(type = "text")
    private String content;
    private String name;
    @OneToOne
    private ContentFile image;
    private Boolean isGlobal = false;

    @Builder

    public Template(UUID id, boolean isDeleted, LocalDateTime createdAt, Long createdBy, LocalDateTime updatedAt, Long updatedBy, Long orgID, Long workPlaceID, Long userID, String content, String name, ContentFile image, Boolean isGlobal) {
        super(id, isDeleted, createdAt, createdBy, updatedAt, updatedBy);
        this.orgID = orgID;
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.content = content;
        this.name = name;
        this.image = image;
        this.isGlobal = isGlobal;
    }
}
