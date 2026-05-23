package ro.iss.agorainretea.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String location;

    @OneToOne
    @JoinColumn(name = "adminId")
    @JsonBackReference
    private User admin;
}
