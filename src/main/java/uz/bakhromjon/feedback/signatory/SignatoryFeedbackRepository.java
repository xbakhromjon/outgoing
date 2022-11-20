package uz.bakhromjon.feedback.signatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import java.util.UUID;

@Repository
public interface SignatoryFeedbackRepository extends JpaRepository<SignatoryFeedback, UUID>, BaseRepository {
}
