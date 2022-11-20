package uz.bakhromjon.missive.projections;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 14/10/22, Fri, 10:02
 **/
public interface MissiveVersionShortInfoProjection {
    byte[] getID();

    Integer getVersion();
}
