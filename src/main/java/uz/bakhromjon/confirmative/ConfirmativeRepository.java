package uz.bakhromjon.confirmative;

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
public interface ConfirmativeRepository extends JpaRepository<Confirmative, UUID>, BaseRepository {
    @Query(nativeQuery = true, value = "delete from confirmative where id in :IDs")
    @Modifying
    void deleteAll(List<UUID> IDs);

    @Query(nativeQuery = true, value = "delete from missive_confirmatives where confirmatives_id in :IDs")
    @Modifying
    void deleteFromRelatedTable(List<UUID> IDs);

    @Query(nativeQuery = true, value = "select * from confirmative where id in (select confirmatives_id from missive_confirmatives where missive_id = :missiveID)")
    List<Confirmative> getAll(UUID missiveID);

    @Query(nativeQuery = true, value = "select *\n" +
            "from confirmative\n" +
            "where id in (select confirmatives_id from missive_confirmatives where missive_confirmatives.missive_id = (select missive_id from missive_confirmatives where confirmatives_id = :confID))")
    List<Confirmative> getAllSiblings(UUID confID);

    @Query(nativeQuery = true, value = "update confirmative set prev_is_ready = true where id = :ID")
    @Modifying
    void prevReady(UUID ID);

    @Query(nativeQuery = true, value = "update confirmative set is_ready_to_send = false where id in (select confirmatives_id from missive_confirmatives where missive_id = :missiveID)")
    @Modifying
    void notReadyByMissiveID(UUID missiveID);


    @Query(nativeQuery = true, value = "update confirmative set status_code = 2 where id = :rejectedByUUID")
    @Modifying
    void reject(UUID rejectedByUUID);


    @Query(nativeQuery = true, value = "update confirmative set status_code = :code where id = :ID")
    @Modifying
    void setStatus(UUID ID, Integer code);
}
