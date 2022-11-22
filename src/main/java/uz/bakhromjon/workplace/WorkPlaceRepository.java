package uz.bakhromjon.workplace;

/**
 * @author : Bakhromjon Khasanboyev
 **/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional
public interface WorkPlaceRepository extends JpaRepository<WorkPlace, Long>, BaseRepository {
    Optional<WorkPlace> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    @Query(nativeQuery = true, value = "update work_place set is_deleted = true  where id = :id")
    void delete(UUID id);

    @Query(nativeQuery = true, value = "select :userID in (select user_id from work_place where not is_deleted and user_id is not null)")
    Boolean isAttachedUser(UUID userID);

    @Query(nativeQuery = true, value = "select * from work_place where user_id = :userId")
    Optional<WorkPlace> findByUserId(UUID userId);
}

