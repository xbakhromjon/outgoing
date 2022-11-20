package uz.bakhromjon.missive.projections;

import java.time.LocalDate;
import java.util.UUID;

public interface MissiveListProjection {
    byte[] getID();

    Integer getTotalCount();

    UUID getOrgID();

    UUID getDepartmentID();

    UUID getSenderUserID();

    String getShortInfo();

    String getNumber();

    LocalDate getRegisteredAt();
}
