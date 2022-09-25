package uz.darico.outReceiver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;
import uz.darico.inReceiver.InReceiver;

import java.util.UUID;

@Repository
public interface OutReceiverRepository extends JpaRepository<OutReceiver, UUID>, BaseRepository {
}
