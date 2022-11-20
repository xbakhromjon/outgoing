package uz.bakhromjon.userPosition;

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
public interface UserPositionRepository extends JpaRepository<UserPosition, Long>, BaseRepository {
    Optional<UserPosition> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    @Query(nativeQuery = true, value = "update user_position set is_deleted = true  where id = :id")
    void delete(UUID id);

    @Query(nativeQuery = true, value = "select :id in (select user_position_id from work_place where not is_deleted)")
    boolean isUsed(UUID id);
}

