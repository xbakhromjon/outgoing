package uz.darico.missive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import uz.darico.base.repository.BaseRepository;
import uz.darico.missive.dto.MissiveGetDTO;
import uz.darico.missive.projections.MissiveListProjection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface MissiveRepository extends JpaRepository<Missive, UUID>, BaseRepository {

    @Query(nativeQuery = true, value = "update missive set is_deleted = true where id = :ID")
    @Modifying
    void delete(UUID ID);


    @Query(nativeQuery = true, value = "update sender set is_ready_to_send = true where id = (select sender_id from missive where id = :ID)")
    @Modifying
    void readyForSender(UUID ID);

    @Query(nativeQuery = true, value = "update confirmative set is_ready_to_send = true where id = :confID")
    @Modifying
    void readyForConf(UUID confID);

    @Query(nativeQuery = true, value = "update signatory set is_signed = true  where id = (select signatory_id from missive where id = :ID)")
    @Modifying
    void sign(UUID ID);

    @Query(nativeQuery = true, value = "update missive set is_ready = true where id = :ID")
    @Modifying
    void prepareToSend(UUID ID);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where s.work_placeid = :workPlaceID\n" +
            "  and not s.is_ready_to_send\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getSketchies(Long workPlaceID, Integer limit, Integer offset);
}
