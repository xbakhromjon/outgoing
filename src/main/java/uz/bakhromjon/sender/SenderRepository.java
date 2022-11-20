package uz.bakhromjon.sender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
@Repository
@Transactional
public interface SenderRepository extends JpaRepository<Sender, UUID>, BaseRepository {
    @Query(nativeQuery = true, value = "select * from sender where id = (select sender_id from missive where id = :missiveID)")
    Optional<Sender> findByMissiveId(UUID missiveID);
}
