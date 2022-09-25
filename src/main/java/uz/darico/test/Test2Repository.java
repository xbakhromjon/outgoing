
package uz.darico.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Test2Repository extends JpaRepository<Test2, Long> {
}
