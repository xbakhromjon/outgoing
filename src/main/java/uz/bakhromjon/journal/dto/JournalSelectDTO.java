package uz.bakhromjon.journal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalSelectDTO {
    private UUID id;
    private String uzName;
    private String ruName;
    private Integer currentNumber;
}
