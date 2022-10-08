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

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
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
}
