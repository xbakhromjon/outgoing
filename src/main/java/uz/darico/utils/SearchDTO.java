package uz.darico.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private Long confirmativeWorkPlace;
    private Long correspondent;
    private String shortInfo;
    private Long workPlace;
    private Long orgID;
    private Integer page;
    private Integer size = 20;
    private Integer tab;
    @JsonIgnore
    private Integer offset;
}
