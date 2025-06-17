package ism.absence.services;


import ism.absence.data.models.Justificatif;
import ism.absence.mobile.dto.request.JustificatifRequestDTO;
import ism.absence.mobile.dto.response.JustificatifResponseDTO;

import java.util.List;
import java.util.Optional;

public interface JustificatifService {

    /**
     * Soumettre un nouveau justificatif
     * @param dto Les données du justificatif
     * @return Le justificatif créé
     */
    Justificatif submit(JustificatifRequestDTO dto);

    /**
     * Récupérer tous les justificatifs d'un étudiant
     * @param matricule Le matricule de l'étudiant
     * @return Liste des justificatifs
     */
    List<JustificatifResponseDTO> getByEtudiant(String matricule);

    /**
     * Récupérer un justificatif par son ID
     * @param id L'ID du justificatif
     * @return Le justificatif trouvé
     */
    Optional<JustificatifResponseDTO> getById(String id);

    /**
     * Récupérer les justificatifs par étudiant et état
     * @param matricule Le matricule de l'étudiant
     * @param etat L'état recherché (EN_COURS, VALIDE, REJETE)
     * @return Liste des justificatifs filtrés
     */
    List<JustificatifResponseDTO> getByEtudiantAndEtat(String matricule, String etat);

    /**
     * Récupérer les justificatifs en attente d'un étudiant
     * @param matricule Le matricule de l'étudiant
     * @return Liste des justificatifs en cours
     */
    List<JustificatifResponseDTO> getJustificatifsPendingForEtudiant(String matricule);

    /**
     * Compter le nombre total de justificatifs d'un étudiant
     * @param matricule Le matricule de l'étudiant
     * @return Le nombre de justificatifs
     */
    long countByEtudiant(String matricule);

    /**
     * Mettre à jour l'état d'un justificatif
     * @param id L'ID du justificatif
     * @param nouvelEtat Le nouvel état
     * @return true si la mise à jour a réussi
     */
    boolean updateEtat(String id, String nouvelEtat);

    /**
     * Supprimer un justificatif par son ID
     * @param id L'ID du justificatif à supprimer
     */
    void deleteById(String id);

    /**
     * Compter les justificatifs par étudiant et état
     * @param matricule Le matricule de l'étudiant
     * @param etat L'état recherché
     * @return Le nombre de justificatifs
     */
    long countByEtudiantAndEtat(String matricule, String etat);
}