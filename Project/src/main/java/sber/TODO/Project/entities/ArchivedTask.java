package sber.TODO.Project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sber.TODO.Project.Category;

import java.time.LocalDateTime;

@Entity
@Table(name = "archived_task")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ArchivedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank(message = "Напиши что-нибудь")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Напиши что-нибудь")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Выбери дату")
    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private int prior;

    @Column(nullable = false)
    private boolean done = false;

    @Column(nullable = false)
    private Category category;
}
