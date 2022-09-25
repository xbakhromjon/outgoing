package uz.darico.signatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;
import uz.darico.outReceiver.OutReceiver;

import java.util.UUID;

@Repository
public interface SignatoryRepository extends JpaRepository<Signatory, UUID>, BaseRepository {
}
