package uz.bakhromjon.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.base.entity.AbstractEntity;
import uz.bakhromjon.contentFile.ContentFile;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Organization extends AbstractEntity {
    private String name;
    private String shortName;
    private String address;
    private String mobileNumber;
    private String email;
    @OneToOne
    private ContentFile logo;
    private String website;
}
