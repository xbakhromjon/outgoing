package uz.darico.journal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Bakhromjon Sun, 11:39 AM 3/20/2022
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JournalLogDto  {
    private String createdIpAddress;
    private String createdDevice;
    private String createdBrowser;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDateTime createdAt;
}
