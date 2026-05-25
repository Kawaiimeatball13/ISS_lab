package ro.iss.agorainretea.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String familyName;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String email;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Team team;
}
