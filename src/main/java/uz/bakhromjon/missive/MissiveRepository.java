package uz.bakhromjon.missive;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uz.bakhromjon.base.repository.BaseRepository;
import uz.bakhromjon.missive.projections.MissiveListProjection;
import uz.bakhromjon.missive.projections.MissiveVersionShortInfoProjection;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MissiveRepository extends JpaRepository<Missive, UUID>, BaseRepository {

    @Query(nativeQuery = true, value = "select * from missive where id = :ID and not is_deleted")
    Optional<Missive> find(UUID ID);

    @Query(nativeQuery = true, value = "update missive set is_deleted = true where id = :ID")
    @Modifying
    void delete(UUID ID);


    @Query(nativeQuery = true, value = "update sender set is_ready_to_send = true where id = (select sender_id from missive where id = :ID)")
    @Modifying
    void readyForSender(UUID ID);

    @Query(nativeQuery = true, value = "update confirmative set is_ready_to_send = true, status_code = 1 where id = :confID \n")
    @Modifying
    void readyForConf(UUID confID);

    @Query(nativeQuery = true, value = "            update signatory\n" +
            "            set is_signed   = true,\n" +
            "            status_code = 1,\n" +
            "            signed_at = current_timestamp\n" +
            "            where id = (select signatory_id from missive where id = :ID)\n" +
            "            ")
    @Modifying
    void sign(UUID ID);

    @Query(nativeQuery = true, value = "update missive set is_ready = true where id = :ID")
    @Modifying
    void prepareToSend(UUID ID);

    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where not m.is_deleted  and m.is_last_version \n" +
            "  and s.work_placeid = :workPlaceID\n" +
            "  and not s.is_ready_to_send\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case when :correspondentID is not null then  (:correspondentID in (select correspondentid\n" +
            "                            from out_receiver\n" +
            "                            where id in (select out_receivers_id\n" +
            "                                         from missive_out_receivers\n" +
            "                                         where missive_id = m.id))\n" +
            "    or :correspondentID in (select correspondentid\n" +
            "                            from in_receiver\n" +
            "                            where id in (select in_receivers_id\n" +
            "                                         from missive_in_receivers\n" +
            "                                         where missive_in_receivers.missive_id = m.id))) else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getSketchies(Long workPlaceID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "            where not m.is_deleted  and m.is_last_version\n" +
            "              and s.work_placeid = :workPlaceID\n" +
            "              and not s.is_ready_to_send")
    Integer getSketchyCount(Long workPlaceID);

    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where not m.is_deleted and m.is_last_version \n" +
            "  and s.work_placeid = :workPlaceID\n" +
            "  and s.is_ready_to_send\n" +
            "  and not m.is_ready\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case\n" +
            "          when :correspondentID is not null then (:correspondentID in (select correspondentid\n" +
            "                                                                       from out_receiver\n" +
            "                                                                       where id in (select out_receivers_id\n" +
            "                                                                                    from missive_out_receivers\n" +
            "                                                                                    where missive_id = m.id))\n" +
            "              or :correspondentID in (select correspondentid\n" +
            "                                      from in_receiver\n" +
            "                                      where id in (select in_receivers_id\n" +
            "                                                   from missive_in_receivers\n" +
            "                                                   where missive_in_receivers.missive_id = m.id)))\n" +
            "          else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getInProcesses(Long workPlaceID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "            where not m.is_deleted\n" +
            "              and m.is_last_version\n" +
            "              and s.work_placeid = :workPlaceID\n" +
            "              and s.is_ready_to_send\n" +
            "              and not m.is_ready")
    Integer getInProcessCount(Long workPlaceID);


    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where not m.is_deleted and  m.is_last_version and m.id in (select missive_confirmatives.missive_id\n" +
            "                                    from missive_confirmatives\n" +
            "                                    where missive_confirmatives.confirmatives_id in (select id\n" +
            "                                                                                     from confirmative\n" +
            "                                                                                     where confirmative.work_placeid = :workPlaceID\n" +
            "                                                                                       and not confirmative.is_ready_to_send ))\n" +
            "  and s.is_ready_to_send\n" +
            "  and not m.is_ready\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case\n" +
            "          when :correspondentID is not null then (:correspondentID in (select correspondentid\n" +
            "                                                                       from out_receiver\n" +
            "                                                                       where id in (select out_receivers_id\n" +
            "                                                                                    from missive_out_receivers\n" +
            "                                                                                    where missive_id = m.id))\n" +
            "              or :correspondentID in (select correspondentid\n" +
            "                                      from in_receiver\n" +
            "                                      where id in (select in_receivers_id\n" +
            "                                                   from missive_in_receivers\n" +
            "                                                   where missive_in_receivers.missive_id = m.id)))\n" +
            "          else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getForConfirm(Long workPlaceID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "            where not m.is_deleted\n" +
            "              and m.is_last_version\n" +
            "              and m.id in (select missive_confirmatives.missive_id\n" +
            "                           from missive_confirmatives\n" +
            "                           where missive_confirmatives.confirmatives_id in (select id\n" +
            "                                                                            from confirmative\n" +
            "                                                                            where confirmative.work_placeid = :workPlaceID\n" +
            "                                                                              and not confirmative.is_ready_to_send\n" +
            "                                                                             ))\n" +
            "              and s.is_ready_to_send\n" +
            "              and not m.is_ready")
    Integer getForConfirmCount(Long workPlaceID);


    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "where not m.is_deleted  and m.is_last_version \n" +
            "  and m.id in (select missive_confirmatives.missive_id\n" +
            "               from missive_confirmatives\n" +
            "               where missive_confirmatives.confirmatives_id in (select id\n" +
            "                                                                from confirmative\n" +
            "                                                                where confirmative.work_placeid = :workPlaceID\n" +
            "                                                                  and confirmative.is_ready_to_send))\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case\n" +
            "          when :correspondentID is not null then (:correspondentID in (select correspondentid\n" +
            "                                                                       from out_receiver\n" +
            "                                                                       where id in (select out_receivers_id\n" +
            "                                                                                    from missive_out_receivers\n" +
            "                                                                                    where missive_id = m.id))\n" +
            "              or :correspondentID in (select correspondentid\n" +
            "                                      from in_receiver\n" +
            "                                      where id in (select in_receivers_id\n" +
            "                                                   from missive_in_receivers\n" +
            "                                                   where missive_in_receivers.missive_id = m.id)))\n" +
            "          else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getConfirmed(Long workPlaceID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);


    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "            where not m.is_deleted\n" +
            "              and m.is_last_version\n" +
            "              and m.id in (select missive_confirmatives.missive_id\n" +
            "                           from missive_confirmatives\n" +
            "                           where missive_confirmatives.confirmatives_id in (select id\n" +
            "                                                                            from confirmative\n" +
            "                                                                            where confirmative.work_placeid = :workPlaceID\n" +
            "                                                                              and confirmative.is_ready_to_send))")
    Integer getConfirmedCount(Long workPlaceID);


    @Query(nativeQuery = true, value = "update  missive set is_ready = true where id = (select missive_id from missive_confirmatives where confirmatives_id = :confID)")
    @Modifying
    void readyByConfID(UUID confID);

    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "         inner join signatory s2 on m.signatory_id = s2.id\n" +
            "where not m.is_deleted and m.is_last_version and s2.work_placeid = :workPlaceID and m.is_ready and not s2.is_signed\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case\n" +
            "          when :correspondentID is not null then (:correspondentID in (select correspondentid\n" +
            "                                                                       from out_receiver\n" +
            "                                                                       where id in (select out_receivers_id\n" +
            "                                                                                    from missive_out_receivers\n" +
            "                                                                                    where missive_id = m.id))\n" +
            "              or :correspondentID in (select correspondentid\n" +
            "                                      from in_receiver\n" +
            "                                      where id in (select in_receivers_id\n" +
            "                                                   from missive_in_receivers\n" +
            "                                                   where missive_in_receivers.missive_id = m.id)))\n" +
            "          else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getForSign(Long workPlaceID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);


    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "                     inner join signatory s2 on m.signatory_id = s2.id\n" +
            "            where not m.is_deleted and m.is_last_version and s2.work_placeid = :workPlaceID and m.is_ready and not s2.is_signed")
    Integer getForSignCount(Long workPlaceID);


    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "         inner join signatory s2 on m.signatory_id = s2.id\n" +
            "where not m.is_deleted and m.is_last_version and s2.work_placeid = :workPlaceID and s2.is_signed\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case\n" +
            "          when :correspondentID is not null then (:correspondentID in (select correspondentid\n" +
            "                                                                       from out_receiver\n" +
            "                                                                       where id in (select out_receivers_id\n" +
            "                                                                                    from missive_out_receivers\n" +
            "                                                                                    where missive_id = m.id))\n" +
            "              or :correspondentID in (select correspondentid\n" +
            "                                      from in_receiver\n" +
            "                                      where id in (select in_receivers_id\n" +
            "                                                   from missive_in_receivers\n" +
            "                                                   where missive_in_receivers.missive_id = m.id)))\n" +
            "          else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getSigned(Long workPlaceID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);


    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "         inner join signatory s2 on m.signatory_id = s2.id\n" +
            "where not m.is_deleted\n" +
            "  and m.is_last_version\n" +
            "  and m.orgid = :orgID and not m.is_confirm_office_manager \n" +
            "  and s2.is_signed\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case\n" +
            "          when :correspondentID is not null then (:correspondentID in (select correspondentid\n" +
            "                                                                       from out_receiver\n" +
            "                                                                       where id in (select out_receivers_id\n" +
            "                                                                                    from missive_out_receivers\n" +
            "                                                                                    where missive_id = m.id))\n" +
            "              or :correspondentID in (select correspondentid\n" +
            "                                      from in_receiver\n" +
            "                                      where id in (select in_receivers_id\n" +
            "                                                   from missive_in_receivers\n" +
            "                                                   where missive_in_receivers.missive_id = m.id)))\n" +
            "          else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getSignedForOfficeManager(Long orgID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "                     inner join signatory s2 on m.signatory_id = s2.id\n" +
            "            where not m.is_deleted and m.is_last_version and s2.work_placeid = :workPlaceID and s2.is_signed")
    Integer getSignedCount(Long workPlaceID);

    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "                     inner join signatory s2 on m.signatory_id = s2.id\n" +
            "            where not m.is_deleted and m.is_last_version and m.orgid = :orgID and not m.is_confirm_office_manager  and s2.is_signed")
    Integer getSignedForOfficeManagerCount(Long orgID);

    @Query(nativeQuery = true, value = "select count(*) over () as totalCount,\n" +
            "       m.id             as ID,\n" +
            "       m.departmentid   as departmentID,\n" +
            "       m.orgid          as orgID,\n" +
            "       s.userid            senderUserID,\n" +
            "       m.short_info        shortInfo ,\n" +
            "       m.registered_at as registeredAt,\n" +
            "       m.number as number\n" +
            "from missive m\n" +
            "         inner join sender s on m.sender_id = s.id\n" +
            "         inner join signatory s2 on m.signatory_id = s2.id\n" +
            "where m.is_confirm_office_manager\n" +
            "  and not m.is_deleted\n" +
            "  and m.is_last_version\n" +
            "  and s.work_placeid = :workPlaceID\n" +
            "  and s2.is_signed\n" +
            "  and case\n" +
            "          when :confirmativeWorkPlaceID is not null then :confirmativeWorkPlaceID in (select work_placeid\n" +
            "                                                                                      from confirmative\n" +
            "                                                                                      where id in\n" +
            "                                                                                            (select confirmatives_id\n" +
            "                                                                                             from missive_confirmatives\n" +
            "                                                                                             where missive_id = m.id))\n" +
            "          else true end\n" +
            "  and case when :shortInfo is not null then m.short_info ilike :shortInfo else true end\n" +
            "  and case\n" +
            "          when :correspondentID is not null then (:correspondentID in (select correspondentid\n" +
            "                                                                       from out_receiver\n" +
            "                                                                       where id in (select out_receivers_id\n" +
            "                                                                                    from missive_out_receivers\n" +
            "                                                                                    where missive_id = m.id))\n" +
            "              or :correspondentID in (select correspondentid\n" +
            "                                      from in_receiver\n" +
            "                                      where id in (select in_receivers_id\n" +
            "                                                   from missive_in_receivers\n" +
            "                                                   where missive_in_receivers.missive_id = m.id)))\n" +
            "          else true end\n" +
            "limit :limit offset :offset")
    List<MissiveListProjection> getSent(Long workPlaceID, Long confirmativeWorkPlaceID, String shortInfo, Long correspondentID, Integer limit, Integer offset);

    @Query(nativeQuery = true, value = "select count(*)\n" +
            "            from missive m\n" +
            "                     inner join sender s on m.sender_id = s.id\n" +
            "                     inner join signatory s2 on m.signatory_id = s2.id\n" +
            "            where m.is_confirm_office_manager and not m.is_deleted and m.is_last_version and s.work_placeid = :workPlaceID and s2.is_signed")
    Integer getSentCount(Long workPlaceID);

    @Query(nativeQuery = true, value = "update missive set is_ready = false where id = :ID")
    @Modifying
    void notReady(UUID ID);

    @Query(nativeQuery = true, value = "update missive set root_versionid = :rootVersionId where id = :id")
    @Modifying
    void setRootVersionID(UUID id, UUID rootVersionId);

    @Query(nativeQuery = true, value = "select *\n" +
            "            from missive\n" +
            "            where root_versionid = :rootID\n" +
            "              and version = (select max(t.version)\n" +
            "                             from (select * from missive where root_versionid = :rootID) as t\n" +
            "                             group by t.root_versionid)")
    Missive getLastVersion(UUID rootID);

    @Query(nativeQuery = true, value = "select id as ID, version\n" +
            "            from missive\n" +
            "            where root_versionid = (select root_versionid\n" +
            "                                    from missive\n" +
            "                                    where id = :id)\n" +
            "            order by version")
    List<MissiveVersionShortInfoProjection> getAllVersions(UUID id);

    @Query(nativeQuery = true, value = "update missive_file\n" +
            "            set content = :content\n" +
            "            where id = (select missive_file_id from missive where missive.id = :ID)")
    @Modifying
    void setContent(UUID ID, String content);

    @Query(nativeQuery = true, value = "update missive_file set content = :content where id = (select missive_file_id from missive where id = :missiveID)")
    @Modifying
    void updateContent(UUID missiveID, String content);
}
