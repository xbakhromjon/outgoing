package uz.darico.confirmative;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;
import uz.darico.sender.Sender;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface ConfirmativeRepository extends JpaRepository<Confirmative, UUID>, BaseRepository {
    @Query(nativeQuery = true, value = "delete from confirmative where id in :IDs")
    @Modifying
    void deleteAll(List<UUID> IDs);

    @Query(nativeQuery = true, value = "delete from missive_confirmatives where confirmatives_id in :IDs")
    @Modifying
    void deleteFromRelatedTable(List<UUID> IDs);

    @Query(nativeQuery = true, value = "select * from confirmative where id in (select confirmatives_id from missive_confirmatives where missive_id = :ID)")
    List<Confirmative> getAll(UUID ID);
}
