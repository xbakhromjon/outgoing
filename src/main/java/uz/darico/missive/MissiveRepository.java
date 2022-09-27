package uz.darico.missive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import uz.darico.base.repository.BaseRepository;
import uz.darico.missive.projections.MissiveListProjection;

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

    @Query(nativeQuery = true, value = "update confirmative set is_ready_to_send = true where id = :confID returning (select is_ready_to_send from confirmative where id in (select confirmatives_id from missive_confirmatives where missive_confirmatives.missive_id = (select missive_id from missive_confirmatives where confirmatives_id = :confID)))\n")
    @Modifying
    List<Boolean> readyForConf(UUID confID);

    @Query(nativeQuery = true, value = "update signatory set is_signed = true  where id = (select signatory_id from missive where id = :ID)")
    @Modifying
    void sign(UUID ID);

    @Query(nativeQuery = true, value = "update missive set is_ready = true where id = :ID")
    @Modifying
    void prepareToSend(UUID ID);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, m.orgid as orgID,  s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where s.work_placeid = :workPlaceID\n" +
            "  and not s.is_ready_to_send\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getSketchies(Long workPlaceID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, m.orgid as orgID,  s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where s.work_placeid = :workPlaceID\n" +
            "  and s.is_ready_to_send and not m.is_ready\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getInProcesses(Long workPlaceID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, m.orgid as orgID,  s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where m.id in (select missive_confirmatives.missive_id from missive_confirmatives where missive_confirmatives.confirmatives_id in (select id from confirmative where confirmative.work_placeid = :workPlaceID and not confirmative.is_ready_to_send))\n" +
            "  and s.is_ready_to_send and not m.is_ready\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getForConfirm(Long workPlaceID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, m.orgid as orgID,  s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where m.id in (select missive_confirmatives.missive_id from missive_confirmatives where missive_confirmatives.confirmatives_id in (select id from confirmative where confirmative.work_placeid = :workPlaceID and confirmative.is_ready_to_send))\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getConfirmed(Long workPlaceID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "update  missive set is_ready = true where id = (select missive_id from missive_confirmatives where confirmatives_id = :confID)")
    @Modifying
    void ready(UUID confID);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, m.orgid as orgID,  s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "         inner join signatory s2 on m.signatory_id = s2.id\n" +
            "where s2.work_placeid = :workPlaceID and m.is_ready and not s2.is_signed \n" +
            "limit :limit offset :offset ")
    List<MissiveListProjection> getForSign(Long workPlaceID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, m.orgid as orgID,  s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "         inner join signatory s2 on m.signatory_id = s2.id\n" +
            "where s2.work_placeid = :workPlaceID and s2.is_signed \n" +
            "limit :limit offset :offset ")
    List<MissiveListProjection> getSigned(Long workPlaceID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select m.id as ID, m.departmentid as departmentID, m.orgid as orgID,  s.userid senderUserID, m.short_info shortInfo\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "         inner join signatory s2 on m.signatory_id = s2.id\n" +
            "where s.work_placeid = :workPlaceID and s2.is_signed \n" +
            "limit :limit offset :offset ")
    List<MissiveListProjection> getSent(Long workPlaceID, Integer limit, Integer offset);

}
