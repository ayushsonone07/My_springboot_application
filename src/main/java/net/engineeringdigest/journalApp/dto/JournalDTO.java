package net.engineeringdigest.journalApp.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class JournalDTO {
    private String title;
    private String description;
    private String author;
}
