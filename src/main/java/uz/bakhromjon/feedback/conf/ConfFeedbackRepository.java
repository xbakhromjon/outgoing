package uz.bakhromjon.feedback.conf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import java.util.UUID;

@Repository
public interface ConfFeedbackRepository extends JpaRepository<ConfFeedback, UUID>, BaseRepository {
}
