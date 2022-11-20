package uz.bakhromjon.inReceiver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface InReceiverRepository extends JpaRepository<InReceiver, UUID>, BaseRepository {
    @Query(nativeQuery = true, value = "delete from in_receiver where id in :IDs")
    @Modifying
    void deleteAll(List<UUID> IDs);

    @Query(nativeQuery = true, value = "delete from missive_in_receivers where in_receivers_id in :IDs")
    @Modifying
    void deleteFromRelatedTable(List<UUID> IDs);

    @Query(nativeQuery = true, value = "select * from in_receiver where id in (select in_receivers_id from missive_in_receivers where missive_id = :ID)")
    List<InReceiver> getAllByMissiveID(UUID ID);

}
