package uz.bakhromjon.journal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseOrderDTO  {
    private List<OrderDTO> orders;
}
