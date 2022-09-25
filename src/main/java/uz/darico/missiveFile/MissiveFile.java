package uz.darico.missiveFile;

import lombok.*;
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
@Table(name = "missive_file")
public class MissiveFile extends Auditable {
    @OneToOne
    private ContentFile file;
    private Integer version;
    private String rejectedPurpose;
    private Long rejectedWorkPlaceID;
    private Long rejectedUserID;
}
