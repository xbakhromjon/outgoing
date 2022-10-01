package uz.darico.missive.projections;

import java.util.UUID;

public interface MissiveListProjection {
    byte[] getID();

    Integer getTotalCount();

    Long getOrgID();

    Long getDepartmentID();

    Long getSenderUserID();

    String getShortInfo();
}
