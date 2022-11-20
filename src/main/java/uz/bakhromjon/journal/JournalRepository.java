package uz.bakhromjon.journal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Bakhromjon Sat, 4:13 PM 3/12/2022
 */
@Repository
public interface JournalRepository extends JpaRepository<Journal, UUID>, BaseRepository {
    Page<Journal> findAllByIsDeletedAndArchivedAndOrgId(Boolean deleted, Boolean archived, Long orgId, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from journal where org_id =?1 and not is_deleted and not archived order by  order_number limit ?2 offset ?3")
    List<Journal> findAllByIsDeletedAndClosedAndArchivedAndOrgId(Long orgId, Integer size, Integer offset);

    Page<Journal> findAllByArchivedAndIsDeletedAndOrgId(Boolean archived, Boolean deleted, Long id, Pageable pageable);

    List<Journal> findAllByArchivedAndIsDeletedAndClosedAndOrgId(Boolean archived, Boolean deleted, Boolean closed, Long id);

    boolean existsByOrgIdAndUzNameIgnoreCaseAndIsDeleted(UUID orgId, String name, Boolean deleted);

    Optional<Journal> findByIdAndIsDeleted(UUID id, Boolean deleted);

    @Query(nativeQuery = true, value = "select * from journal where id = ?1 and archived = ?2 and is_deleted = ?3")
    Optional<Journal> findByIdAndArchivedAndIsDeleted(UUID id, Boolean archived, Boolean deleted);

    Optional<Journal> findByIdAndIsDeletedAndClosed(UUID id, boolean b, boolean b1);

    Page<Journal> findAllByOrgIdAndDeletedAndClosed(Long id, Boolean deleted, Boolean closed, Pageable pageable);


    List<Journal> findAllByClosedAndIsDeleted(boolean b, boolean b1);

    @Query(nativeQuery = true, value = "select count(*) from journal where org_id = ?1 and not is_deleted")
    Integer findOrderNumber(UUID orgId);

    @Modifying
    @Query(nativeQuery = true, value = "update journal set order_number = ?2 where id = ?1 returning *")
    Journal changeOrderNumber(UUID id, Integer order);

    @Query(nativeQuery = true, value = "select * from journal where id = :id and not is_deleted")
    Optional<Journal> find(UUID id);
}
