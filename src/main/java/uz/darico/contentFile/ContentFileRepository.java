package uz.darico.contentFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;

import java.util.UUID;

@Repository
public interface ContentFileRepository extends JpaRepository<ContentFile, UUID>, BaseRepository {
}
