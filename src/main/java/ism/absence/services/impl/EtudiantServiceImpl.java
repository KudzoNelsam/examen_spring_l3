package ism.absence.services.impl;

import ism.absence.core.exceptions.EntityNotFoundException;
import ism.absence.core.utils.MatriculeGenerator;
import ism.absence.data.models.Etudiant;
import ism.absence.data.repository.EtudiantRepository;
import ism.absence.services.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {
    private final EtudiantRepository etudiantRepository;
    private final MatriculeGenerator matriculeGenerator;

    @Override
    public Etudiant save(Etudiant etudiant) {
        int totalEtudiant = etudiantRepository.findAll().size();
        String matricule = matriculeGenerator.generateMatricule(totalEtudiant + 1);
        etudiant.setMatricule(matricule);
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant findByMatricule(String matricule) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findByMatricule(matricule);

        if (optionalEtudiant.isPresent()) {
            return optionalEtudiant.get();
        }
        throw new EntityNotFoundException("Aucun etudiant avec ce matricule");
    }

    @Override
    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant findByUserId(String userId) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findByUserId(userId);
        if (optionalEtudiant.isPresent()) {
            return optionalEtudiant.get();
        }
        throw new EntityNotFoundException("Aucun etudiant avec ce user");
    }
}