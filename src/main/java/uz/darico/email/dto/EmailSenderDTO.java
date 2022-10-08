package uz.darico.email.dto;

import lombok.*;


/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 07/10/22, Fri, 13:30
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailSenderDTO {
    private String to;
    private String from;
    private String subject;
    private String text;
    private String contentHtml;
    private String filePath;

    public EmailSenderDTO(String to, String from, String subject, String text, String filePath) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.filePath = filePath;
    }
}
