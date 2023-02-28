package td.banque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "PersonneMorale")
@Table(
        name = "personne_morale"
)
public class PersonneMorale extends Personne {
    @Column(
            name = "SIRET",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String SIRET;
    @Column(
            name = "nom",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String nom;

    public PersonneMorale(String adresse, String SIRET, String nom) {
        super(adresse);
        this.SIRET = SIRET;
        this.nom = nom;
    }

    public PersonneMorale() {

    }

    public String getSIRET() {
        return SIRET;
    }

    public void setSIRET(String SIRET) {
        this.SIRET = SIRET;
    }

    @Override

    public String toString() {

        return "PersonnePhysique{" +

                "id=" + id +

                ", nom='" + nom + '\'' +

                ", SIRET='" + SIRET + '\'' +

                ", adresse='" + adresse + '\'' +

                '}';

    }
}