package uz.darico.missiveFile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.contentFile.ContentFile;
import uz.darico.contentFile.ContentFileService;
import uz.darico.feign.UserFeignService;
import uz.darico.feign.obj.UserInfo;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.dto.InReceiverGetDTO;
import uz.darico.missiveFile.dto.MissiveFileGetDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class MissiveFileMapper implements BaseMapper {
    private final UserFeignService userFeignService;
    private final ContentFileService contentFileService;

    public MissiveFile toEntity(String content) throws IOException {
        ContentFile contentFile = contentFileService.writeAsPDF(content);
        return new MissiveFile.MissiveFileBuilder().content(content).version(1).file(contentFile).build();
    }

    public MissiveFileGetDTO toGetDTO(MissiveFile missiveFile) {
        if (missiveFile == null) {
            return null;
        }
        UserInfo userInfo = userFeignService.getUserInfo(missiveFile.getRejectedUserID());
        MissiveFileGetDTO missiveFileGetDTO = new MissiveFileGetDTO(missiveFile.getFile(), missiveFile.getVersion(), missiveFile.getRejectedPurpose());
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
