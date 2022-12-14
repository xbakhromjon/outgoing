package uz.bakhromjon.template;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface TemplateRepository extends JpaRepository<Template, UUID>, BaseRepository {

    @Query(nativeQuery = true, value = "select * from template where id = :ID and not is_deleted")
    Optional<Template> find(UUID ID);

    @Query(nativeQuery = true, value = "update template set is_deleted = true where id = :ID")
    @Modifying
    void delete(UUID ID);

    @Query(nativeQuery = true, value = "select * from template where not is_deleted and work_placeid = :workPlaceID or (is_global and orgid = :orgID)")
    List<Template> findAll(Long workPlaceID, Long orgID);
}
