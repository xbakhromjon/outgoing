package uz.bakhromjon.feedback.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bakhromjon.base.repository.BaseRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID>, BaseRepository {

    @Query(nativeQuery = true, value = "select * from feedback where root_missiveid = :rootMissiveID and work_placeid = :workPlaceID")
    Optional<Feedback> find(UUID rootMissiveID, UUID workPlaceID);
}
