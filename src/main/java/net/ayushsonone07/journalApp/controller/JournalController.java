package net.ayushsonone07.journalApp.controller;

import net.ayushsonone07.journalApp.dto.ApiResponse;
import net.ayushsonone07.journalApp.dto.JournalDTO;
import net.ayushsonone07.journalApp.entity.Journal;
import net.ayushsonone07.journalApp.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/journals")
public class JournalController {
    @Autowired
    private JournalService service;

    @GetMapping
    public List<JournalDTO> getAll() {
//        System.out.println("Get All Controller Ke Andar");

        return service.getAllJournals();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Journal>> create(@RequestBody JournalDTO dto){

        return ResponseEntity.ok(ApiResponse.created("Journal Created!",service.createJournal(dto)));
    }

}
