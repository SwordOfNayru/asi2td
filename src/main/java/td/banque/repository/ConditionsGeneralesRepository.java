package td.banque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import td.banque.model.ConditionsGenerales;

import java.util.ArrayList;

public interface ConditionsGeneralesRepository extends JpaRepository<ConditionsGenerales, Long> {
    ArrayList<ConditionsGenerales> findByTypeProduit(String typeProduit);
    ArrayList<ConditionsGenerales> findByTypeProduitLike(String typeProduit);
    ArrayList<ConditionsGenerales> findByTypeProduitContains(String typeProduit);
    ArrayList<ConditionsGenerales> findFirst3ByOrderByIdDesc();
    ArrayList<ConditionsGenerales> findByTauxInteretAgiosGreaterThanEqualOrderByTauxInteretAgiosAsc(float taux);
}