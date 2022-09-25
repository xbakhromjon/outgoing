package uz.darico.test;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.darico.base.service.BaseService;
import uz.darico.test.dto.TestUpdateDTO;

import java.util.List;

@Service
public class TestService implements BaseService {
    private final TestRepository testRepository;
    private final Test2Repository test2Repository;
    private final Test3Repository test3Repository;

    public TestService(TestRepository testRepository, Test2Repository test2Repository, Test3Repository test3Repository) {
        this.testRepository = testRepository;
        this.test2Repository = test2Repository;
        this.test3Repository = test3Repository;
    }

    public ResponseEntity<?> update(TestUpdateDTO testUpdateDTO) {
        Test test = testRepository.getReferenceById(testUpdateDTO.getId());
        List<Test2> test2s = testUpdateDTO.getTest2s();
        List<Test2> saved = test2Repository.saveAll(test2s);
        test.setTest2s(test2s);
        List<Test3> test3s = testUpdateDTO.getTest3s();
        List<Test3> savedTest3 = test3Repository.saveAll(test3s);
        test.setTest3s(savedTest3);
        testRepository.save(test);
        return ResponseEntity.ok(true);
    }
}
