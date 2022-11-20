package uz.bakhromjon.template;

import lombok.*;
import org.hibernate.annotations.Type;
import uz.bakhromjon.base.entity.Auditable;
import uz.bakhromjon.contentFile.ContentFile;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Template extends Auditable {
    private UUID orgID;
    private UUID workPlaceID;
    private UUID userID;
    @Type(type = "text")
    private String content;
    private String name;
    @OneToOne
    private ContentFile image;
    private Boolean isGlobal = false;

    @Builder

    public Template(UUID id, boolean isDeleted, LocalDateTime createdAt, UUID createdBy, LocalDateTime updatedAt, UUID updatedBy, UUID orgID, UUID workPlaceID, UUID userID, String content, String name, ContentFile image, Boolean isGlobal) {
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
