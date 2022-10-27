package uz.darico.missive;

import org.springframework.stereotype.Component;
import uz.darico.base.validator.BaseValidator;
import uz.darico.contentFile.ContentFile;
import uz.darico.exception.exception.ValidationException;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missive.dto.MissiveCreateDTO;
import uz.darico.missive.dto.MissiveRegisteDTO;
import uz.darico.missive.dto.MissiveUpdateDTO;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.missiveFile.dto.MissiveFileCreateDTO;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MissiveValidator implements BaseValidator {

    public void validForCreate(MissiveCreateDTO createDTO) {
        if (createDTO.getWorkPlaceID() == null) {
            throw new ValidationException("workPlace cannot null");
        }
        Long workPlaceID = createDTO.getWorkPlaceID();
        if (createDTO.getConfirmativeWorkPlaceIDs() != null) {
            List<Long> confirmativeWorkPlaceIDs = createDTO.getConfirmativeWorkPlaceIDs();
            if (confirmativeWorkPlaceIDs.contains(workPlaceID)) {
                throw new ValidationException(String.format("%s workPlace cannot be confirmative", workPlaceID));
            }
            HashSet<Long> confirmativesSet = new HashSet<>(confirmativeWorkPlaceIDs);
            if (confirmativeWorkPlaceIDs.size() != confirmativesSet.size()) {
                throw new ValidationException(String.format("%s invalid confirmatives. Duplicate workPlaceID", confirmativeWorkPlaceIDs));
            }
        }
        if (createDTO.getOutReceivers() != null) {
            List<OutReceiverCreateDTO> outReceivers = createDTO.getOutReceivers();
            List<Long> outReceiverList = outReceivers.stream().map(OutReceiverCreateDTO::getCorrespondentID).collect(Collectors.toList());
            HashSet<Long> outReceiverSet = new HashSet<>(outReceiverList);
            if (outReceiverSet.size() != outReceivers.size()) {
                throw new ValidationException(String.format("%s invalid outReceivers. Duplicate correspondent", outReceiverList));
            }
        }
        if (createDTO.getInReceivers() != null) {
            List<InReceiverCreateDTO> inReceivers = createDTO.getInReceivers();
            List<Long> inReceiverList = inReceivers.stream().map(InReceiverCreateDTO::getCorrespondentID).collect(Collectors.toList());
            HashSet<Long> inReceiverSet = new HashSet<>(inReceiverList);
            if (inReceiverSet.size() != inReceivers.size()) {
                throw new ValidationException(String.format("%s invalid inReceivers. Duplicate correspondent", inReceiverList));
            }
        }
        if (createDTO.getFishkaID() == null) {
            throw new ValidationException("Fishka cannot be null");
        }
    }

    public void validForUpdate(MissiveUpdateDTO updateDTO) {
        if (updateDTO.getID() == null) {
            throw new ValidationException("MissiveID cannot null");
        }

        if (updateDTO.getWorkPlaceID() == null) {
            throw new ValidationException("WorkPlaceID cannot null");
        }
        Long workPlaceID = updateDTO.getWorkPlaceID();
        if (updateDTO.getConfirmativeWorkPlaceIDs() != null) {
            List<Long> confirmativeWorkPlaceIDs = updateDTO.getConfirmativeWorkPlaceIDs();
            if (confirmativeWorkPlaceIDs.contains(workPlaceID)) {
                throw new ValidationException(String.format("%s workPlace cannot be confirmative", workPlaceID));
            }
            HashSet<Long> confirmativesSet = new HashSet<>(confirmativeWorkPlaceIDs);
            if (confirmativeWorkPlaceIDs.size() != confirmativesSet.size()) {
                throw new ValidationException(String.format("%s invalid confirmatives. Duplicate workPlaceID", confirmativeWorkPlaceIDs));
            }
        }
        if (updateDTO.getOutReceivers() != null) {
            List<OutReceiverCreateDTO> outReceivers = updateDTO.getOutReceivers();
            List<Long> outReceiverList = outReceivers.stream().map(OutReceiverCreateDTO::getCorrespondentID).collect(Collectors.toList());
            HashSet<Long> outReceiverSet = new HashSet<>(outReceiverList);
            if (outReceiverSet.size() != outReceivers.size()) {
                throw new ValidationException(String.format("%s invalid outReceivers. Duplicate correspondent", outReceiverList));
            }
        }
        if (updateDTO.getInReceivers() != null) {
            List<InReceiverCreateDTO> inReceivers = updateDTO.getInReceivers();
            List<Long> inReceiverList = inReceivers.stream().map(InReceiverCreateDTO::getCorrespondentID).collect(Collectors.toList());
            HashSet<Long> inReceiverSet = new HashSet<>(inReceiverList);
            if (inReceiverSet.size() != inReceivers.size()) {
                throw new ValidationException(String.format("%s invalid inReceivers. Duplicate correspondent", inReceiverList));
            }
        }
    }

    public void validForNewVersion(MissiveCreateDTO createDTO) {
        if (createDTO.getRootID() == null) {
            throw new ValidationException("Missive cannot be null");
        }
        if (createDTO.getContent() == null) {
            throw new ValidationException("File cannot be null");
        }
        if (createDTO.getWorkPlaceID() == null) {
            throw new ValidationException("WorkPlace cannot be null");
        }
    }

    public void validForRegister(MissiveRegisteDTO missiveRegisteDTO) {

    }
}
