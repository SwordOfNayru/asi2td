package td.banque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import td.banque.model.Personne;


public interface PersonneRepository <T extends Personne> extends JpaRepository<T, Long>

{} 