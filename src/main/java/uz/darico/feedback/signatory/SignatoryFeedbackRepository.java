package uz.darico.feedback.signatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.darico.base.repository.BaseRepository;

import java.util.UUID;

@Repository
public interface SignatoryFeedbackRepository extends JpaRepository<SignatoryFeedback, UUID>, BaseRepository {
}
