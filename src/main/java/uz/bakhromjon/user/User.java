package uz.bakhromjon.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.bakhromjon.base.entity.Auditable;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.role.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:41
 **/
@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends Auditable {
    private String username;

    private String email;
    private String password;

    @OneToOne
    private ContentFile avatar;

    private String firstname;
    private String lastname;
    private String middleName;
    private Boolean isAttached = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, HashSet<Role> roles) {
        this(username, email, password);
        this.roles = roles;
    }
}