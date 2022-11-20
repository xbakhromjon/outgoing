package uz.bakhromjon.permission;

/**
 * @author : Bakhromjon Khasanboyev
 **/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;
import uz.bakhromjon.userPosition.UserPosition;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, UUID>, BaseRepository {
}

