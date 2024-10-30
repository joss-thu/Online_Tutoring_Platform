package de.thu.thutorium;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "actor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleEntity {
    @Id
    private Integer id;
    private String first_name;
    private String last_name;
}
