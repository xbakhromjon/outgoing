package uz.bakhromjon.missive.projections;

import java.time.LocalDate;

public interface MissiveListProjection {
    byte[] getID();

    Integer getTotalCount();

    Long getOrgID();

    Long getDepartmentID();

    Long getSenderUserID();

    String getShortInfo();

    String getNumber();

    LocalDate getRegisteredAt();
}
