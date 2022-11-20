package uz.bakhromjon.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:43
 **/
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>, BaseRepository {
    Optional<User> findByUsername(String username);

    @Query(nativeQuery = true, value = "update users set is_deleted = true where id = :id")
    @Modifying
    void delete(UUID id);

    Optional<User> findByIdAndIsDeleted(UUID id, boolean isDeleted);
}