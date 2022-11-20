package uz.bakhromjon.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 20/10/22, Thu, 21:39
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContentUpdateDTO {
    private String content;
    private UUID missiveID;
}
