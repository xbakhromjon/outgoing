package uz.darico.outReceiver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;
import uz.darico.inReceiver.InReceiver;

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
}
