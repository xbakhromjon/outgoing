package uz.darico.missive.projections;

import java.util.UUID;

public interface MissiveListProjection {
    byte[] getID();

    Long getOrgID();

    Long getDepartmentID();

    Long getSenderUserID();

    String getShortInfo();
}
