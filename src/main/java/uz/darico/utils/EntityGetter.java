package uz.darico.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.darico.exception.exception.UniversalException;
import uz.darico.missive.Missive;
import uz.darico.missive.MissiveMapper;
import uz.darico.missive.MissiveRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class EntityGetter {
    private final MissiveRepository missiveRepository;

    public EntityGetter(MissiveRepository missiveRepository) {
        this.missiveRepository = missiveRepository;
    }

    public Missive getMissive(UUID ID) {
        Optional<Missive> missiveOptional = missiveRepository.findById(ID);
        if (missiveOptional.isEmpty()) {
            throw new UniversalException("Missive not found", HttpStatus.BAD_REQUEST);
        }
        return missiveOptional.get();
    }
}
