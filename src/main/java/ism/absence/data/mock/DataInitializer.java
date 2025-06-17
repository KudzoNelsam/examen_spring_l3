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

    private final List<Etudiant> etudiants = new ArrayList<>();
    private final List<AnneeScolaire> annees = new ArrayList<>();
    private final List<Filiere> filieres = new ArrayList<>();
    private final List<Classe> classes = new ArrayList<>();
    private final List<Module> modules = new ArrayList<>();
    private final List<Cours> cours = new ArrayList<>();
    private final List<SeanceCours> seances = new ArrayList<>();
    private final List<Pointage> pointages = new ArrayList<>();
    private final List<Salle> salles = new ArrayList<>();
    private User userVigile;

    private static final double DEFAULT_LONGITUDE = -17.458473;
    private static final double DEFAULT_LATITUDE = 14.69058;

    @Override
    public void run(String... args) {
        clearData();
        createAdminAndVigile();
        createEtudiants();
        createAnnees();
        createFilieres();
        createModules();
        createCours();
        createClasses();
        createInscriptions();
        createSalles();
        createSeances();
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
        vigileRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void createEtudiants() {
        for (int i = 1; i <= 20; i++) {
            User user = new User();
            user.setFullName("Etudiant " + i);
            user.setUsername(String.format("etu%03d", i)); // etu001 à etu020
            user.setPassword("passer");
            user.setAdresse("Dakar");
            user.setRole(UserRole.ROLE_ETUDIANT);

            Localisation loc = new Localisation(DEFAULT_LONGITUDE, DEFAULT_LATITUDE);
            user.setLocalisation(loc);

            user = userService.save(user);

            Etudiant etu = new Etudiant();
            etu.setUserId(user.getId());
            etudiants.add(etudiantService.save(etu));
        }
    }

    private void createAdminAndVigile() {
        Optional<User> existingAdmin = userRepository.findByUsername("admin");
        if (existingAdmin.isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setFullName("Administrateur Principal");
            admin.setAdresse("Administration Centrale");
            admin.setRole(UserRole.ROLE_ADMIN);
            Localisation loc = new Localisation(DEFAULT_LONGITUDE, DEFAULT_LATITUDE);
            admin.setLocalisation(loc);
            userService.save(admin);
        }

        Optional<User> existingVigile = userRepository.findByUsername("johndoe");
        if (existingVigile.isEmpty()) {
            User vigileUser = new User();
            vigileUser.setUsername("johndoe");
            vigileUser.setPassword("passer");
            vigileUser.setFullName("John Doe");
            vigileUser.setAdresse("Sicap Amitié");
            vigileUser.setRole(UserRole.ROLE_VIGILE);
            Localisation loc = new Localisation(DEFAULT_LONGITUDE, DEFAULT_LATITUDE);
            vigileUser.setLocalisation(loc);
            vigileUser = userService.save(vigileUser);

            Vigile vigile = new Vigile();
            vigile.setUserId(vigileUser.getId());
            vigile.setNumeroVig("VIG001");
            vigileRepository.save(vigile);
            this.userVigile = vigileUser;
        } else {
            this.userVigile = existingVigile.get();
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

    private void createModules() {
        String[] nomsModules = {"Java", "Python", "Flutter", "Spring Boot"};
        for (String nom : nomsModules) {
            Module m = new Module();
            m.setLibelle("Programmation " + nom);
            modules.add(moduleRepository.save(m));
        }
    }

    private void createCours() {
        String[] profs = {"Baila Wane", "Aly Niang", "Fatou Sow", "Aminata Diallo"};

        for (int i = 0; i < modules.size(); i++) {
            Module m = modules.get(i);
            Cours c = new Cours();
            c.setNom("Cours de " + m.getLibelle());
            c.setProfesseur(profs[i % profs.length]);
            c.setModuleId(m.getId());
            cours.add(coursRepository.save(c));
        }
    }

    private void createClasses() {
        // Chaque classe est liée à un cours spécifique
        Classe cl1 = new Classe();
        cl1.setFiliereId(filieres.get(0).getId()); // IAGE
        cl1.setGrade(Grade.L3);
        cl1.setLibelle("L3-IAGE");
        cl1.setCoursId(cours.get(0).getId()); // Java
        classes.add(classeRepository.save(cl1));

        Classe cl2 = new Classe();
        cl2.setFiliereId(filieres.get(0).getId()); // IAGE
        cl2.setGrade(Grade.L3);
        cl2.setLibelle("L3-IAGE-B");
        cl2.setCoursId(cours.get(1).getId()); // Python
        classes.add(classeRepository.save(cl2));

        Classe cl3 = new Classe();
        cl3.setFiliereId(filieres.get(1).getId()); // GLRS
        cl3.setGrade(Grade.L3);
        cl3.setLibelle("L3-GLRS");
        cl3.setCoursId(cours.get(2).getId()); // Flutter
        classes.add(classeRepository.save(cl3));

        Classe cl4 = new Classe();
        cl4.setFiliereId(filieres.get(1).getId()); // GLRS
        cl4.setGrade(Grade.L3);
        cl4.setLibelle("L3-GLRS-B");
        cl4.setCoursId(cours.get(3).getId()); // Spring Boot
        classes.add(classeRepository.save(cl4));
    }

    private void createInscriptions() {
        AnneeScolaire annee = annees.stream()
                .filter(a -> a.getStatut() == StatutAnnee.ENCOURS)
                .findFirst()
                .orElseThrow();

        // Répartir les étudiants dans les 4 classes
        for (int i = 0; i < etudiants.size(); i++) {
            Inscription ins = new Inscription();
            ins.setEtudiantId(etudiants.get(i).getId());
            ins.setClasseId(classes.get(i % classes.size()).getId()); // Répartition cyclique
            ins.setAnneeScolaireId(annee.getId());
            ins.setDateInscription(LocalDate.now());
            ins.setActive(true);
            inscriptionRepository.save(ins);
        }
    }

    private void createSalles() {
        String[] nomsSlles = {"Salle A1", "Salle B2", "Laboratoire Info"};

        for (String nom : nomsSlles) {
            Salle s = new Salle();
            s.setNom(nom);
            s.setAdresse("Campus ISM Dakar");
            s.setCapacite(60);
            Localisation loc = new Localisation(DEFAULT_LONGITUDE, DEFAULT_LATITUDE);
            s.setLocalisation(loc);
            salles.add(salleRepository.save(s));
        }
    }

    private void createSeances() {
        LocalDate today = LocalDate.now();

        for (Cours c : cours) {
            // Créer des séances passées
            for (int i = 0; i < 3; i++) {
                SeanceCours s1 = new SeanceCours();
                s1.setCoursId(c.getId());
                s1.setSalleId(salles.get(i % salles.size()).getId());
                s1.setDate(today.minusDays(14 + i * 2)); // Séances passées
                s1.setHeureDebut(LocalTime.of(8, 0));
                s1.setHeureFin(LocalTime.of(10, 0));
                seances.add(seanceCoursRepository.save(s1));
            }

            // Créer des séances futures
            for (int i = 0; i < 2; i++) {
                SeanceCours s2 = new SeanceCours();
                s2.setCoursId(c.getId());
                s2.setSalleId(salles.get((i + 1) % salles.size()).getId());
                s2.setDate(today.plusDays(2 + i * 3)); // Séances futures
                s2.setHeureDebut(LocalTime.of(14, 0));
                s2.setHeureFin(LocalTime.of(16, 0));
                seances.add(seanceCoursRepository.save(s2));
            }
        }
    }

    private void createPointages() {
        AnneeScolaire annee = annees.stream()
                .filter(a -> a.getStatut() == StatutAnnee.ENCOURS)
                .findFirst()
                .orElseThrow();

        for (Etudiant etu : etudiants) {
            Optional<Inscription> inscriptionOpt = inscriptionRepository
                    .findByEtudiantIdAndAnneeScolaireId(etu.getId(), annee.getId());

            if (inscriptionOpt.isEmpty()) continue;

            Inscription inscription = inscriptionOpt.get();
            Classe classe = classeRepository.findById(inscription.getClasseId()).orElse(null);
            if (classe == null) continue;

            int absencesCount = 0;
            int pointageCounter = 0;

            for (SeanceCours sc : seances) {
                // Vérifier si cette séance concerne le cours de la classe de l'étudiant
                if (!sc.getCoursId().equals(classe.getCoursId())) {
                    continue;
                }

                Pointage p = new Pointage();
                p.setMatricule(etu.getMatricule());
                p.setSeanceCoursId(sc.getId());
                p.setVigileId(userVigile.getId());
                p.setEstJustifie(false); // Par défaut non justifié

                // Logique de pointage basée sur la date de la séance
                if (sc.getDate().isBefore(LocalDate.now())) {
                    // Séances passées
                    if (absencesCount < 2) {
                        // Forcer quelques absences pour avoir des données de test
                        p.setStatut(StatutPointage.ABSENT);
                        p.setDate(LocalDateTime.of(sc.getDate(), sc.getHeureFin().plusHours(1)));
                        absencesCount++;
                    } else {
                        int remainder = pointageCounter % 10;
                        if (remainder < 6) {
                            p.setStatut(StatutPointage.PRESENT);
                            p.setDate(LocalDateTime.of(sc.getDate(), sc.getHeureDebut().plusMinutes(5)));
                        } else if (remainder < 8) {
                            p.setStatut(StatutPointage.EN_RETARD);
                            p.setDate(LocalDateTime.of(sc.getDate(), sc.getHeureDebut().plusMinutes(25)));
                        } else {
                            p.setStatut(StatutPointage.ABSENT);
                            p.setDate(LocalDateTime.of(sc.getDate(), sc.getHeureFin().plusHours(1)));
                        }
                    }
                } else {
                    // Séances futures - en attente
                    p.setStatut(StatutPointage.EN_ATTENTE);
                    p.setDate(null); // Pas encore de pointage
                }

                pointages.add(pointageRepository.save(p));
                pointageCounter++;
            }
        }
    }

    private void createJustificatifs() {
        int justificatifCounter = 0;

        for (Pointage p : pointages) {
            if (p.getStatut() == StatutPointage.ABSENT && p.getDate() != null) {
                // Créer un justificatif pour certaines absences seulement
                if (justificatifCounter % 3 == 0) { // 1 justificatif sur 3 absences
                    Justificatif j = new Justificatif();
                    j.setMotif(getRandomMotif(justificatifCounter));
                    j.setEtat(getRandomEtat(justificatifCounter));
                    j.setDate(p.getDate().plusHours(2)); // Justificatif soumis après l'absence
                    j.setMatricule(p.getMatricule()); // Utilise le matricule de l'étudiant
                    j.setPiecesJointes(null);
                    justificatifRepository.save(j);

                    // Marquer le pointage comme justifié si le justificatif est validé
                    if (j.getEtat() == EtatJustificatif.VALIDE) {
                        p.setEstJustifie(true);
                        pointageRepository.save(p);
                    }
                }
                justificatifCounter++;
            }
        }
    }

    private String getRandomMotif(int index) {
        String[] motifs = {
                "Maladie grave",
                "Urgence familiale",
                "Problème de transport",
                "Rendez-vous médical"
        };
        return motifs[index % motifs.length];
    }

    private EtatJustificatif getRandomEtat(int index) {
        EtatJustificatif[] etats = {
                EtatJustificatif.EN_COURS,
                EtatJustificatif.VALIDE,
                EtatJustificatif.REJETE
        };
        return etats[index % etats.length];
    }
}