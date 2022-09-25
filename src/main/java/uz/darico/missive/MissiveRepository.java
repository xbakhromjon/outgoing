package uz.darico.missive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import uz.darico.base.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface MissiveRepository extends JpaRepository<Missive, UUID>, BaseRepository {

}
