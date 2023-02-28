package td.banque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import td.banque.model.ProduitBancaire;

public interface ProduitBancaireRepository extends JpaRepository<ProduitBancaire, Long> {
}
