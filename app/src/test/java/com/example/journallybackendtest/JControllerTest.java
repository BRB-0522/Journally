package com.example.journallybackendtest;

import junit.framework.TestCase;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JControllerTest extends TestCase {

    @Test
    void correctlyFormattedDateString() {
        String title = "test_journal";
        String description = "test_description";
        Journal journal = JController.createJournal(title, description);
        String dateFormat = "dd/MM/yyyy";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        String creationDate = dtf.format(LocalDateTime.now());
        assertEquals(creationDate, journal.getCreationDate());
    }

}