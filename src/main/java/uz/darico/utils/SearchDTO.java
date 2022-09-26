package uz.darico.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String confirmative;
    private Long correspondent;
    private String shortInfo;
    private Long workPlace;
    private Integer page;
    private Integer size = 20;
    private Integer offset = page * size;
    private Integer tab;
}
