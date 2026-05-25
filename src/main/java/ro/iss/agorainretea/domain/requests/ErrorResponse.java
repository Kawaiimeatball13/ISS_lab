package ro.iss.agorainretea.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@AllArgsConstructor
@ResponseBody
@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime time;
    private String message;
    private String error;

}
