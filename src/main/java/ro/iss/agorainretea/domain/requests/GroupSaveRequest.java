package ro.iss.agorainretea.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GroupSaveRequest {
    private String name;
    private String description;
    private String location;
    private Long adminId;
}
