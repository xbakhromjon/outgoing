package uz.darico.missiveFile;

import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.contentFile.ContentFile;


@Component
public class MissiveFileMapper implements BaseMapper {

    public MissiveFile toEntity(String content) {
        return new MissiveFile.MissiveFileBuilder().content(content).version(1).build();
    }
}
