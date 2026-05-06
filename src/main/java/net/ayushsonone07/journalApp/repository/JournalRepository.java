package net.ayushsonone07.journalApp.repository;

import net.ayushsonone07.journalApp.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Long> {

}
