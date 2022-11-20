package uz.bakhromjon.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 14/10/22, Fri, 09:58
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveVersionShortInfoDTO {
    private UUID ID;
    private Integer version;
}
