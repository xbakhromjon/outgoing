package uz.darico.missiveFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface MissiveFileRepository extends JpaRepository<MissiveFile, UUID>, BaseRepository {

    @Query(nativeQuery = true, value = "update missive_file set is_deleted = true  where id in :IDs")
    @Modifying
    void deleteAll(List<UUID> IDs);

    @Query(nativeQuery = true, value = "select * from missive_file where id in (select missive_files_id from missive_missive_files where missive_id = :ID)")
    List<MissiveFile> getAll(UUID ID);
}
