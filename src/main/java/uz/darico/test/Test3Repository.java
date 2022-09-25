
package uz.darico.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Test3Repository extends JpaRepository<Test3, Long> {
}
