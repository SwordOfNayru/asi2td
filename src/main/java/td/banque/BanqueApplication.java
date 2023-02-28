package td.banque;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import td.banque.model.ConditionsGenerales;
import td.banque.model.PersonneMorale;
import td.banque.model.PersonnePhysique;
import td.banque.model.ProduitBancaire;
import td.banque.repository.ConditionsGeneralesRepository;
import td.banque.repository.PersonneRepository;
import td.banque.repository.ProduitBancaireRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@SpringBootApplication
public class BanqueApplication {
    public static void main(String[] args) {
        SpringApplication.run(BanqueApplication.class, args);
    }
    @Bean
    CommandLineRunner ajouterConditionsGenerales(ConditionsGeneralesRepository conditionsGeneralesRepository)
    {
        return args -> {
// On vide la table pour ne pas créer de doublons
            conditionsGeneralesRepository.deleteAll();
// On crée une liste de conditions générales que l'on insèrera d'un coup en base
            ArrayList<ConditionsGenerales> listeCG = new ArrayList<ConditionsGenerales>();
// On crée quelques conditions générales
            listeCG.add(new ConditionsGenerales(
                    0,
                    "Mastercard",
                    15));
            listeCG.add(new ConditionsGenerales(
                    3.0f,
                    "Livret épargne",
                    0));
            listeCG.add(new ConditionsGenerales(
                    1.0f,
                    "Compte rémunéré",
                    0));
            listeCG.add(new ConditionsGenerales(
                    3.0f,
                    "Prêt consommation",
                    0));
// On insère la liste dans la table
            conditionsGeneralesRepository.saveAll(listeCG);
// Testons maintenant les requetes par defaut
            listeCG = new ArrayList<ConditionsGenerales>();
            long nbConditionsGenerales;
// Nombre de conditions générales
            nbConditionsGenerales=conditionsGeneralesRepository.count();
            System.out.println("\n***************** test count");
            System.out.println("\nil y a "+nbConditionsGenerales+" conditions generales dans la base");
// Rechercher Condition générale par id
            long id=100;
            Optional<ConditionsGenerales> cg=conditionsGeneralesRepository.findById(id);
            System.out.println("\n***************** test findById");
            if (cg.isPresent()) System.out.println("\nconditions générales avec l'id : "+id+cg);
            else System.out.println("Pas de condition generale avec la clé : "+id);
// On récupère et on affiche les conditions générales en base
            System.out.println("\n***************** Toutes les conditions générales non triees");
            System.out.println(conditionsGeneralesRepository.findAll());
// On récupère les conditions générales en base triées par ordre alphabétique du typeProduit
            System.out.println("\n***************** Toutes les conditions générales dans l'ordre alphabetique du typeProduit");
            System.out.println(conditionsGeneralesRepository.findAll(Sort.by(Sort.Direction.ASC, "typeProduit")));
// Pagination
            int numeroPage=3; // numéro de la page chargée. Attention, le numéro commence à 0. Ici, on récupère la quatrième page
            int taillePage=1; // Nombre de conditions générales par page.
            // L'instruction suivante récupère la 4ième page de données, chaque page contenant une seule donnée. Les données sont ici aussi triées par ordre alphabétique du type
            Page<ConditionsGenerales> page= conditionsGeneralesRepository.findAll(PageRequest.of(3, 1,Sort.by(Sort.Direction.ASC, "typeProduit")));
            System.out.println("\n***************** Test pagination");
            System.out.println(page+"\n Contenu de la page"+page.getContent());
        };
    }
//
//    @Bean
//    CommandLineRunner testerRequetesAutomatiques(ConditionsGeneralesRepository conditionsGeneralesRepository) {
//
//        return args -> {
//
//            ArrayList<ConditionsGenerales> listeCG = new ArrayList<ConditionsGenerales>();
//
//            listeCG=conditionsGeneralesRepository.findByTypeProduit("Compte chèque");
//
//            System.out.println(listeCG);
//
//        };
//
//    }
    @Bean
    CommandLineRunner testerRequetesAutomatiques(ConditionsGeneralesRepository conditionsGeneralesRepository) {
        return args -> {
            ArrayList<ConditionsGenerales> listeCG = new ArrayList<ConditionsGenerales>();
            System.out.println("*******************************\nRechercher les conditions générales dont le type est Compte chèque");
            listeCG=conditionsGeneralesRepository.findByTypeProduit("Compte chèque");
            System.out.println(listeCG);
            System.out.println("*******************************\nPremiere solution : Rechercher les conditions générales dont le type contient compte");
            listeCG=conditionsGeneralesRepository.findByTypeProduitContains("compte");
            System.out.println(listeCG);
            System.out.println("*******************************\nDeuxieme solution : Rechercher les conditions générales dont le type contient compte");
            listeCG=conditionsGeneralesRepository.findByTypeProduitLike("%compte%");
            System.out.println(listeCG);
            System.out.println("*******************************\nRechercher les 3 dernieres conditions générales saisies en base");
            listeCG=conditionsGeneralesRepository.findFirst3ByOrderByIdDesc();
            System.out.println(listeCG);
            System.out.println("*******************************\nListe des produits dont la rentabilité est >=3 pour la banque");
            listeCG=conditionsGeneralesRepository.findByTauxInteretAgiosGreaterThanEqualOrderByTauxInteretAgiosAsc(3);
            System.out.println(listeCG);
        };
    }
    @Bean
    CommandLineRunner ajouterPersonnes(
            PersonneRepository<PersonneMorale> personneMoraleRepository,
            PersonneRepository<PersonnePhysique> personnePhysiqueRepository)
    {
        return args -> {
// Suppression des données créées par ce test lors d'une exécution précédente pour éviter les doublons
            personneMoraleRepository.deleteAll();
            personnePhysiqueRepository.deleteAll();
// Création de trois personnes morales
            PersonneMorale pm1=new PersonneMorale("102bis rue du Vesuve","SIRET1", "Pizza Tonio");
            PersonneMorale pm2=new PersonneMorale("45 Boulevard du fleuve","SIRET2", "Meubles cosy");
            PersonneMorale pm3=new PersonneMorale("14 allee des platanes","SIRET3", "Espaces tres verts");
// Enregistrement en base
            personneMoraleRepository.save(pm1);
            personneMoraleRepository.save(pm2);
            personneMoraleRepository.save(pm3);
// Affichage du résultat
            System.out.println(personneMoraleRepository.findAll());
// Ajout de personnes physiques
            PersonnePhysique pp1=new PersonnePhysique("19 rue des fleurs, 80000 Amiens", "Dupont","Jean");
            personnePhysiqueRepository.save(pp1);
            PersonnePhysique pp2=new PersonnePhysique("143 boulevard des landes, 64200 Anglet", "Eche","Piou");
            personnePhysiqueRepository.save(pp2);
            PersonnePhysique pp3=new PersonnePhysique("56 avenue de Paris, 60000 Beauvais", "Tristan","Jacques");
            personnePhysiqueRepository.save(pp3);
            System.out.println(personnePhysiqueRepository.findAll());
        };
    }
    @Bean
    CommandLineRunner seedData(
            ConditionsGeneralesRepository conditionsGeneralesRepository,
            PersonneRepository<PersonneMorale> personneMoraleRepository,
            PersonneRepository<PersonnePhysique> personnePhysiqueRepository,
            ProduitBancaireRepository produitBancaireRepository)
    {
        return args -> {
            List<ConditionsGenerales> conditionsGeneraless;
            List<ProduitBancaire> produitBancaires;
            ConditionsGenerales cg1=new ConditionsGenerales((float)0.2,"cg1",0);
            conditionsGeneralesRepository.save(cg1);
            ConditionsGenerales cg2=new ConditionsGenerales(3,"cg2",0);
            conditionsGeneralesRepository.save(cg2);
            ConditionsGenerales cg3=new ConditionsGenerales(0,"cg3",15);
            conditionsGeneralesRepository.save(cg3);
            cg3= conditionsGeneralesRepository.findById(cg3.getId()).orElseThrow();
            ProduitBancaire pb1 = new ProduitBancaire(&,cg3);
            produitBancaireRepository.save(pb1);
            cg2= conditionsGeneralesRepository.findById(cg2.getId()).orElseThrow();
            ProduitBancaire pb2 = new ProduitBancaire(2,cg2);
            produitBancaireRepository.save(pb2);
            cg3= conditionsGeneralesRepository.findById(cg3.getId()).orElseThrow();
            ProduitBancaire pb3 = new ProduitBancaire(3,cg3);
            produitBancaireRepository.save(pb3);
            conditionsGeneraless=conditionsGeneralesRepository.findAll();
            produitBancaires=produitBancaireRepository.findAll();
        };
    }
}
