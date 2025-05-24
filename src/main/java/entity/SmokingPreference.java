package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SmokingPreferences")
@Data
public class SmokingPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
