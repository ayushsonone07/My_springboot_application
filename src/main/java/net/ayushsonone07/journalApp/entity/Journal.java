package net.ayushsonone07.journalApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Journal")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private String author;
}
