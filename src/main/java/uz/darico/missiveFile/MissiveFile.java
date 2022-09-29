package uz.darico.missiveFile;

import lombok.*;
import org.hibernate.annotations.Type;
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
@Table(name = "missive_file")
public class MissiveFile extends Auditable {
    @Type(type = "text")
    private String content;
    private Integer version;
    private String rejectedPurpose;
    private Long rejectedWorkPlaceID;
    private Long rejectedUserID;
}
