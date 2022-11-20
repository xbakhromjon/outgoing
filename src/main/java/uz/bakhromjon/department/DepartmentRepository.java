package uz.bakhromjon.department;

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
public interface DepartmentRepository extends JpaRepository<Department, Long>, BaseRepository {
    Optional<Department> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    @Query(nativeQuery = true, value = "update department set is_deleted = true  where id = :id")
    void delete(UUID id);

}

