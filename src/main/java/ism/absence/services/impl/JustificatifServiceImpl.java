package ism.absence.services.impl;

import ism.absence.data.models.Justificatif;
import ism.absence.mobile.dto.request.JustificatifRequestDTO;
import ism.absence.mobile.dto.response.JustificatifResponseDTO;
import ism.absence.services.JustificatifService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JustificatifServiceImpl implements JustificatifService {
    @Override
    public Justificatif submit(JustificatifRequestDTO dto) {
        return null;
    }

    @Override
    public List<JustificatifResponseDTO> getByEtudiant(String matricule) {
        return List.of();
    }

    @Override
    public Optional<JustificatifResponseDTO> getById(String id) {
        return Optional.empty();
    }

    @Override
    public List<JustificatifResponseDTO> getByEtudiantAndEtat(String matricule, String etat) {
        return List.of();
    }

    @Override
    public List<JustificatifResponseDTO> getJustificatifsPendingForEtudiant(String matricule) {
        return List.of();
    }

    @Override
    public long countByEtudiant(String matricule) {
        return 0;
    }

    @Override
    public boolean updateEtat(String id, String nouvelEtat) {
        return false;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public long countByEtudiantAndEtat(String matricule, String etat) {
        return 0;
    }
}