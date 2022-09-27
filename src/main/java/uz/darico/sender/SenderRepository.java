package uz.darico.sender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SenderRepository extends JpaRepository<Sender, UUID>, BaseRepository {
    @Query(nativeQuery = true, value = "select * from sender where id = (select sender_id from missive where id = :missiveID)")
    Optional<Sender> findByMissiveId(UUID missiveID);
}
