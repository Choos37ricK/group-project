package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RegistrationRequest {

    private String email;
    private String passwd1;
    private String passwd2;
    private String firstName;
    private String lastName;
    private String code;

}
