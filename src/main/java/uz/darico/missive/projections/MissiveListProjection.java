package uz.darico.missive.projections;

import java.util.UUID;

public interface MissiveListProjection {
    UUID getID();

    Long getDepartmentID();

    Long getSenderUserID();

    String getShortInfo();
}
