package io.github.biojj.modules.schedule.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O Titulo é obrigatório")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "A Descrição é obrigatória")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "A data de início é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @NotNull(message = "O status é obrigatório")
    @Column(name = "status", nullable = false)
    private Boolean status;
}
