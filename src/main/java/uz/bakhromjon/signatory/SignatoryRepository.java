package uz.bakhromjon.signatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface SignatoryRepository extends JpaRepository<Signatory, UUID>, BaseRepository {
    @Query(nativeQuery = true, value = "update signatory set status_code = 2 where id = :rejectedByUUID")
    @Modifying
    void reject(UUID rejectedByUUID);

    @Query(nativeQuery = true, value = "update signatory set status_code = :code where id = :ID")
    @Modifying
    void setStatus(UUID ID, Integer code);
}
