package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.dto.ApiResponse;
import net.engineeringdigest.journalApp.dto.AuthResponse;
import net.engineeringdigest.journalApp.dto.JournalDTO;
import net.engineeringdigest.journalApp.entity.Journal;
import net.engineeringdigest.journalApp.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


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
