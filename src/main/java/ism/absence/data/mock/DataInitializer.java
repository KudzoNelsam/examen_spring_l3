package ism.absence.data.mock;

import ism.absence.data.enums.*;
import ism.absence.data.models.*;
import ism.absence.data.models.Module;
import ism.absence.data.records.Localisation;
import ism.absence.data.repository.*;
import ism.absence.services.EtudiantService;
import ism.absence.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final EtudiantRepository etudiantRepository;
    private final ModuleRepository moduleRepository;
    private final CoursRepository coursRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final PointageRepository pointageRepository;
    private final ClasseRepository classeRepository;
    private final FiliereRepository filiereRepository;
    private final InscriptionRepository inscriptionRepository;
    private final EtudiantService etudiantService;
    private final UserRepository userRepository;
    private final SeanceCoursRepository seanceCoursRepository;
    private final SalleRepository salleRepository;
    private final VigileRepository vigileRepository;
    private final UserService userService;
    private final JustificatifRepository justificatifRepository;

    private List<Etudiant> etudiants = new ArrayList<>();
    private List<AnneeScolaire> annees = new ArrayList<>();
    private List<Filiere> filieres = new ArrayList<>();
    private List<Classe> classes = new ArrayList<>();
    private List<Module> modules = new ArrayList<>();
    private List<Cours> cours = new ArrayList<>();
    private List<SeanceCours> seances = new ArrayList<>();
    private List<Pointage> pointages = new ArrayList<>();
    private List<Salle> salles = new ArrayList<>();
    private User userVigile;

    @Override
    public void run(String... args) {
        clearData();
        createEtudiants();
        createAnnees();
        createFilieres();
        createClasses();
        createInscriptions();
        createModules();
        createCours();
        createSalles();
        createSeances();
        createVigile();
        createPointages();
        createJustificatifs();
    }

    private void clearData() {
        justificatifRepository.deleteAll();
        pointageRepository.deleteAll();
        seanceCoursRepository.deleteAll();
        coursRepository.deleteAll();
        moduleRepository.deleteAll();
        inscriptionRepository.deleteAll();
        classeRepository.deleteAll();
        filiereRepository.deleteAll();
        anneeScolaireRepository.deleteAll();
        etudiantRepository.deleteAll();
        salleRepository.deleteAll();
    }

    private void createEtudiants() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Etudiant etu = new Etudiant();
            etu.setUserId(user.getId());
            etudiants.add(etudiantService.save(etu));
        }
    }

    private void createAnnees() {
        annees.add(saveAnnee(LocalDate.of(2022, 9, 22), LocalDate.of(2023, 7, 15), StatutAnnee.TERMINE));
        annees.add(saveAnnee(LocalDate.of(2023, 9, 22), LocalDate.of(2024, 6, 27), StatutAnnee.TERMINE));
        annees.add(saveAnnee(LocalDate.of(2024, 9, 22), LocalDate.of(2025, 7, 12), StatutAnnee.ENCOURS));
    }

    private AnneeScolaire saveAnnee(LocalDate debut, LocalDate fin, StatutAnnee statut) {
        AnneeScolaire an = new AnneeScolaire();
        an.setDateDebut(debut);
        an.setDateFin(fin);
        an.setStatut(statut);
        return anneeScolaireRepository.save(an);
    }

    private void createFilieres() {
        Filiere f1 = new Filiere();
        f1.setCodeFiliere("IAGE");
        f1.setNomFiliere("Informatique Appliquée à la Gestion");
        filieres.add(filiereRepository.save(f1));

        Filiere f2 = new Filiere();
        f2.setCodeFiliere("GLRS");
        f2.setNomFiliere("Génie Logiciel Réseaux et Systèmes");
        filieres.add(filiereRepository.save(f2));
    }

    private void createClasses() {
        Classe cl1 = new Classe();
        cl1.setFiliereId(filieres.get(1).getId()); // GLRS
        cl1.setGrade(Grade.L3);
        cl1.setLibelle("L3-GLRS");
        classes.add(classeRepository.save(cl1));

        Classe cl2 = new Classe();
        cl2.setFiliereId(filieres.get(0).getId()); // IAGE
        cl2.setGrade(Grade.L3);
        cl2.setLibelle("L3-IAGE");
        classes.add(classeRepository.save(cl2));
    }

    private void createInscriptions() {
        AnneeScolaire anneeEnCours = annees.stream()
                .filter(a -> a.getStatut() == StatutAnnee.ENCOURS)
                .findFirst().orElseThrow();

        Classe classe = classes.get(0); // L3-GLRS
        for (Etudiant etudiant : etudiants) {
            Inscription ins = new Inscription();
            ins.setEtudiantId(etudiant.getId());
            ins.setAnneeScolaireId(anneeEnCours.getId());
            ins.setClasseId(classe.getId());
            ins.setDateInscription(LocalDate.now());
            inscriptionRepository.save(ins);
        }
    }

    private void createModules() {
        Module m1 = new Module();
        m1.setLibelle("Programmation Java");
        modules.add(moduleRepository.save(m1));

        Module m2 = new Module();
        m2.setLibelle("Programmation Python");
        modules.add(moduleRepository.save(m2));
    }

    private void createCours() {
        Cours c1 = new Cours();
        c1.setNom("Java");
        c1.setProfesseur("Baila Wane");
        c1.setModuleId(modules.get(0).getId());
        cours.add(coursRepository.save(c1));

        Cours c2 = new Cours();
        c2.setNom("Python");
        c2.setProfesseur("Aly Niang");
        c2.setModuleId(modules.get(1).getId());
        cours.add(coursRepository.save(c2));

        // Associer un cours à une classe
        Classe cl = classes.get(0);
        cl.setCoursId(c1.getId());
        classeRepository.save(cl);
    }

    private void createSalles() {
        double longitude =-17.458750;
        double latitude =14.7173559;
        Salle s1 = new Salle();
        s1.setNom("202");
        s1.setAdresse("Dakar Campus");
        s1.setCapacite(100);
        s1.setLocalisation(new Localisation(longitude,latitude ));
        salles.add(salleRepository.save(s1));

        Salle s2 = new Salle();
        s2.setNom("101");
        s2.setAdresse("Dakar Campus");
        s2.setCapacite(65);
        s2.setLocalisation(new Localisation(longitude,latitude ));
        salles.add(salleRepository.save(s2));
    }

    private void createSeances() {
        SeanceCours sc1 = new SeanceCours();
        sc1.setCoursId(cours.getFirst().getId());
        sc1.setSalleId(salles.getFirst().getId());
        sc1.setDate(LocalDate.of(2025, 6, 15));
        sc1.setHeureDebut(LocalTime.of(8, 0));
        sc1.setHeureFin(LocalTime.of(12, 0));
        seances.add(seanceCoursRepository.save(sc1));

        SeanceCours sc2 = new SeanceCours();
        sc2.setCoursId(cours.get(0).getId());
        sc2.setSalleId(salles.get(0).getId());
        sc2.setDate(LocalDate.of(2025, 6, 16));
        sc2.setHeureDebut(LocalTime.of(13, 0));
        sc2.setHeureFin(LocalTime.of(17, 0));
        seances.add(seanceCoursRepository.save(sc2));
    }

    private void createVigile() {
        Optional<User> existing = userRepository.findByUsername("johndoe");
        if (existing.isPresent()) {
            userVigile = existing.get();
        } else {
            User user = new User();
            user.setAdresse("Sicap Amitié");
            user.setFullName("John Doe");
            user.setRole(UserRole.ROLE_VIGILE);
            user.setPassword("passer");
            user.setUsername("johndoe");
            user.setLocalisation(new Localisation(0, 0));
            userVigile = userService.save(user);
        }

        Vigile vigile = new Vigile();
        vigile.setUserId(userVigile.getId());
        vigile.setNumeroVig("VIG001");
        vigileRepository.save(vigile);
    }

    private void createPointages() {
        AnneeScolaire anneeEnCours = annees.stream()
                .filter(a -> a.getStatut() == StatutAnnee.ENCOURS)
                .findFirst().orElseThrow();

        for (Etudiant etu : etudiants) {
            Optional<Inscription> opt = inscriptionRepository.findByEtudiantIdAndAnneeScolaireId(
                    etu.getId(), anneeEnCours.getId()
            );
            if (opt.isEmpty()) continue;
            Inscription ins = opt.get();

            for (SeanceCours sc : seances) {
                Classe cl = classeRepository.findByCoursId(sc.getCoursId());
                if (!cl.getId().equals(ins.getClasseId())) continue;

                Pointage p = new Pointage();
                p.setMatricule(etu.getMatricule());
                p.setSeanceCoursId(sc.getId());
                p.setDate(LocalDateTime.of(sc.getDate(), sc.getHeureDebut().plusMinutes(10)));
                p.setStatut(StatutPointage.EN_ATTENTE);
                p.setVigileId(userVigile.getId());
                pointages.add(pointageRepository.save(p));
            }
        }
    }

    private void createJustificatifs() {
        for (Pointage p : pointages) {
            if (p.getStatut() == StatutPointage.ABSENT) {
                SeanceCours sc = seanceCoursRepository.findById(p.getSeanceCoursId()).orElse(null);
                if (sc == null) continue;
                if (p.getDate().isAfter(LocalDateTime.of(sc.getDate(), sc.getHeureFin()))) {
                    Justificatif j = new Justificatif();
                    j.setMotif("Maladie grave");
                    j.setEtat(EtatJustificatif.EN_COURS);
                    j.setDate(LocalDateTime.now());
                    j.setPiecesJointes(null); // tu peux intégrer Cloudinary plus tard ici
                    justificatifRepository.save(j);
                }
            }
        }
    }
}