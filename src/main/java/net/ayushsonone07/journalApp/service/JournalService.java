package net.ayushsonone07.journalApp.service;

import net.ayushsonone07.journalApp.dto.JournalDTO;
import net.ayushsonone07.journalApp.entity.Journal;
import net.ayushsonone07.journalApp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalService {
    @Autowired
    private JournalRepository repo;

    public List<JournalDTO> getAllJournals(){
        return repo.findAll().stream()
                .map(p -> {
                    JournalDTO dto = new JournalDTO();
                    dto.setTitle(p.getTitle());
                    dto.setDescription(p.getDescription());
                    dto.setAuthor(p.getAuthor());
                    return dto;
                }).collect(Collectors.toList());
    }

    public Journal createJournal(JournalDTO dto) {
        Journal j = new Journal();
        j.setTitle(dto.getTitle());
        j.setDescription(dto.getDescription());
        j.setAuthor(dto.getAuthor());
        return repo.save(j);
    }

    public Journal updateJournal(Long id, JournalDTO dto) {
        Journal journal = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal not found!"));

        if(dto.getTitle() != null ||  dto.getAuthor() != null || dto.getDescription() != null) {
            journal.setTitle(dto.getTitle());
            journal.setDescription(dto.getDescription());
            journal.setAuthor(dto.getAuthor());
        }
        return  repo.save(journal);
    }

}
