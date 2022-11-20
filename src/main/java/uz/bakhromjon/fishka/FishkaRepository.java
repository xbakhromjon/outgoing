package uz.bakhromjon.fishka;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface FishkaRepository extends JpaRepository<Fishka, UUID>, BaseRepository {

    @Query(nativeQuery = true, value = "select * from fishka where id = :id and not is_deleted")
    Optional<Fishka> find(UUID id);


    @Query(nativeQuery = true, value = "update fishka set is_deleted = true where id = :id")
    @Modifying
    void delete(UUID id);

    @Query(nativeQuery = true, value = "select * from fishka where orgid = :orgID and not is_deleted")
    List<Fishka> findAll(Long orgID);

    @Query(nativeQuery = true, value = "select * from fishka where fishka_type_code = :fishkaType")
    Optional<Fishka> findByFishkaType(Integer fishkaType);
}
