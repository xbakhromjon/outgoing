package uz.darico.sender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;

import java.util.UUID;
@Repository
public interface SenderRepository extends JpaRepository<Sender, UUID>, BaseRepository {
}
