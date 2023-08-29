package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.Autorisation;
import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.Etat;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashbord")
@CrossOrigin(origins = "*")
public class DashbordController {

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

    @Autowired
    private final IAutorisationRepository autorisationRepository;

    @GetMapping("/nbCongeAujourdhui")
    public Integer nbCongeAujourdhui(){
        return congeRepository.countByDateDemande(LocalDateTime.now());
    }

    @GetMapping("/nbCongeMois")
    public Integer nbCongeMois(){
        LocalDateTime dated = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 1, 0,0,0);
        LocalDateTime datef = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.MAX.getDayOfMonth(), 0,0,0);
        return congeRepository.countByDateDebutBetween(dated, datef);
    }

    @GetMapping("/nbEnAttente")
    public Integer nbEnAttente(){
        return congeRepository.countByEtat(Etat.En_Attente);
    }

    @GetMapping("/nbAutorisation")
    public Integer nbAutorisation(){
        return autorisationRepository.findByDateDebutBeforeAndDateFinAfter(LocalDateTime.now(), LocalDateTime.now()).size();
    }

    @GetMapping("/countEmp")
    public Integer countEmp() {
        List<Employee> em = new ArrayList<>();
        employeeRepository.findAll().forEach(em::add);
        return em.size();
    }

    @GetMapping("/getListAutorisation")
    public List<Autorisation> getListAutorisation(){
        return autorisationRepository.findByDateDebutBeforeAndDateFinAfter(LocalDateTime.now(), LocalDateTime.now());
    }

    @GetMapping("/getNbCongeMois")
    public Integer getNbCongeMois(@RequestParam("mois") int mois){
        int year = LocalDateTime.now().getYear();
        YearMonth yearMonth = YearMonth.of(year, mois);
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atStartOfDay();
        System.out.println(startDate);
        System.out.println(endDate);
        return congeRepository.countByEtatAndDateDebutBetween(Etat.Accepte, startDate, endDate);
    }








}
