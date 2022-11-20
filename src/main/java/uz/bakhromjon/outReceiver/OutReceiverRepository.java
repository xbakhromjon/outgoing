package uz.bakhromjon.outReceiver;

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
public interface OutReceiverRepository extends JpaRepository<OutReceiver, UUID>, BaseRepository {

    @Query(nativeQuery = true, value = "delete from out_receiver where id in :IDs")
    @Modifying
    void deleteAll(List<UUID> IDs);

    @Query(nativeQuery = true, value = "delete from missive_out_receivers where out_receivers_id in :IDs")
    @Modifying
    void deleteFromRelatedTable(List<UUID> IDs);

    @Query(nativeQuery = true, value = "select * from out_receiver where id in (select out_receivers_id from missive_out_receivers where missive_id = :ID)")
    List<OutReceiver> getAllByMissiveID(UUID ID);
}
