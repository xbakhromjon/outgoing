package uz.darico.template;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.base.entity.Auditable;
import uz.darico.contentFile.ContentFile;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Template extends Auditable {
    private Long workPlaceID;
    private Long userID;
    @OneToOne
    private ContentFile file;
    private String createdPurpose;

}
