package modele.personnes;

import facade.erreurs.RoleDejaAttribueException;

import java.util.Collection;

public interface Personne {
    String getNom();

    void setNom(String nom);

    long getIdentifiant();
    void supprimerRole(String role);

    Collection<String> getRoles();
    void addRole(String role) throws RoleDejaAttribueException;

    void setMdp(String mdp);
    String getMdp();


    boolean hasRole(String role);
}
