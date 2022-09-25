package uz.darico.inReceiver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;
import uz.darico.confirmative.Confirmative;

import java.util.UUID;

@Repository
public interface InReceiverRepository extends JpaRepository<InReceiver, UUID>, BaseRepository {
}
