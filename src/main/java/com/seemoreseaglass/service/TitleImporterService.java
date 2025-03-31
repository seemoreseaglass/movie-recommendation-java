package com.seemoreseaglass.service;

import com.seemoreseaglass.entity.Title;
import com.seemoreseaglass.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

@Service
public class TitleImporterService {

    @Autowired
    private TitleRepository repo;

    @PostConstruct
    public void importTitlesFromTsv() {
        Path path = Paths.get("src/main/resources/imdb/title.basics.tsv");

        try (BufferedReader br = Files.newBufferedReader(path)) {
            br.readLine();

            br.lines()
                .limit(1000)
                .map(this::parseLine)
                .filter(t -> t != null)
                .forEach(repo::save);

            System.out.println("IMDb titles imported successfully!");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private Title parseLine(String line) {
        try {
            String[] fields = line.split("\t");
            Title t = new Title();
            t.setTconst(fields[0]);
            t.setTitleType(fields[1]);
            t.setPrimaryTitle(fields[2]);
            t.setOriginalTitle(fields[3]);
            t.setAdult("1".equals(fields[4]));
            t.setStartYear(parseInt(fields[5]));
            t.setEndYear(parseInt(fields[6]));
            t.setRuntimeMinutes(parseInt(fields[7]));
            t.setGenres(fields.length > 8 ? fields[8] : null);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }
}