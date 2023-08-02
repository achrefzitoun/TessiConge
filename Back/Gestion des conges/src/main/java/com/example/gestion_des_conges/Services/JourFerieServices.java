package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.gestion_des_conges.Entities.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JourFerieServices implements IJourFerieServices {
    @Autowired
    private final ICongeRepository congeRepository;

    @Autowired
    private final IEmployeeRepository employeeRepository;

    @Autowired
    private final IJourFerieRepository jourFerieRepository;

    @Autowired
    private final IMotifRefusRepository motifRefusRepository;

    @Autowired
    private final IPolitiqueRepository politiqueRepository;

    @Autowired
    private final IRoleRepository roleRepository;

    @Autowired
    private final ITypeCongeRepository typeCongeRepository;

    @Value("${france.holidays.file.path}")
    private String franceHolidaysFilePath;

    @Value("${tunisia.holidays.file.path}")
    private String tunisiaHolidaysFilePath;

    @Override
    // Execution automatique chaque 01/12
    public void miseAJourJourFerie() throws IOException {
        try {
            saveJourFerie(franceHolidaysFilePath,"FranceHolidays");
            saveJourFerie(tunisiaHolidaysFilePath,"TunisiaHolidays");
        }
        catch (IOException e) {
            //a
        }

    }

    public void saveJourFerie(String fileName, String sheetName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(fileName); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            for (Row row : sheet) {
                JourFerie jourFerie = new JourFerie();
                int jour = 0;
                int mois = 0;
                String description;

                if (row.getRowNum() == 0) {
                    continue;
                }
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                        jour = (int) row.getCell(0).getNumericCellValue();
                    } else if (row.getCell(0).getCellType() == CellType.STRING) {
                        jour = Integer.parseInt(row.getCell(0).getStringCellValue());
                    }
                    if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                        mois = (int) row.getCell(1).getNumericCellValue();
                    } else if (row.getCell(1).getCellType() == CellType.STRING) {
                        mois = Integer.parseInt(row.getCell(1).getStringCellValue());
                    }

                    description = row.getCell(2).getStringCellValue();

                    jourFerie.setAnnee(LocalDateTime.now().getYear() + 1);

                    LocalDateTime dateDebut = LocalDateTime.of(LocalDateTime.now().getYear() + 1, mois, jour, 0, 0, 0);
                    LocalDateTime dateFin = LocalDateTime.of(LocalDateTime.now().getYear() + 1, mois, jour, 23, 59, 59);

                    jourFerie.setDateDebut(dateDebut);
                    jourFerie.setDateFin(dateFin);

                    jourFerie.setNomDate(description);

                    jourFerieRepository.save(jourFerie);
                }
            }
        }
        catch (IOException e) {
            //a
        }
    }

    @Override
    public JourFerie addJourFerie(JourFerie jourFerie) {
        return jourFerieRepository.save(jourFerie);
    }

    @Override
    public JourFerie updateJourFerie(JourFerie jourFerie) {
        return jourFerieRepository.save(jourFerie);
    }

    @Override
    public void deleteJourFerie(int id) {
        jourFerieRepository.deleteById(id);
    }

    @Override
    public JourFerie retrieveJourFerie(int id) {
        return jourFerieRepository.findById(id).orElse(null);
    }

    @Override
    public List<JourFerie> retrieveAllJourFerie() {
        List<JourFerie> jourFeries = new ArrayList<>();
        jourFerieRepository.findAll().forEach(jourFeries::add);
        return jourFeries;
    }
}

