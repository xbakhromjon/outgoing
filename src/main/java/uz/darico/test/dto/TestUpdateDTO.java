package uz.darico.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.test.Test2;
import uz.darico.test.Test3;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestUpdateDTO {
    private Long id;
    private Integer code;
    private List<Test2> test2s;
    private List<Test3> test3s;
}
