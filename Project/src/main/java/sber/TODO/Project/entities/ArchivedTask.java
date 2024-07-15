package sber.TODO.Project.entities;

import jakarta.persistence.*;
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
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private int prior;

    @Column(nullable = false)
    private boolean done;

    @Column(nullable = false)
    private Category category;
}
