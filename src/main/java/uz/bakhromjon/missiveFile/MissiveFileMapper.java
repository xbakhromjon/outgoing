package uz.bakhromjon.missiveFile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.contentFile.ContentFileService;
import uz.bakhromjon.feign.UserFeignService;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.missiveFile.dto.MissiveFileGetDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class MissiveFileMapper implements BaseMapper {
    private final UserFeignService userFeignService;
    private final ContentFileService contentFileService;

    public MissiveFile toEntity(String content) throws IOException {
//        ContentFile contentFile = contentFileService.writeAsPDF(content);
        return new MissiveFile.MissiveFileBuilder().content(content).version(1).build();
    }

    public MissiveFileGetDTO toGetDTO(MissiveFile missiveFile) {
        if (missiveFile == null) {
            return null;
        }
        UserInfo userInfo = userFeignService.getUserInfo(missiveFile.getRejectedUserID());
        MissiveFileGetDTO missiveFileGetDTO = new MissiveFileGetDTO(missiveFile.getContent(), missiveFile.getVersion(), missiveFile.getRejectedPurpose());
        if (userInfo != null) {
            missiveFileGetDTO.setRejectedFirstName(userInfo.getFirstName());
            missiveFileGetDTO.setRejectedLastName(userInfo.getLastName());
            missiveFileGetDTO.setRejectedMiddleName(userInfo.getMiddleName());
        }
        return missiveFileGetDTO;
    }


    public List<MissiveFileGetDTO> toGetDTO(List<MissiveFile> missiveFiles) {
        List<MissiveFileGetDTO> res = new ArrayList<>();
        missiveFiles.forEach(item -> {
            res.add(toGetDTO(item));
        });
        return res;
    }
}
