package uz.darico.utils;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePage<T> {
    private Integer totalPages;
    private long totalElements;
    private Integer size;
    private Integer number;
    private long numberOfElements;
    private List<T> content;


    public static ResponsePage<?> getEmptyInstance() {
       return new ResponsePage<>(0, 0L, 0,0, 0L, new ArrayList<>());
    }
}
