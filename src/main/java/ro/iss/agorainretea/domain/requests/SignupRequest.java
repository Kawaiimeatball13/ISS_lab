package ro.iss.agorainretea.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
    private String familyName;
    private String name;
    private String password;
    private String email;
}
