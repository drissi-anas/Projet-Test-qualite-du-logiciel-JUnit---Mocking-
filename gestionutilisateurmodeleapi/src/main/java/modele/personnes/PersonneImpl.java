package modele.personnes;

import facade.erreurs.RoleDejaAttribueException;

import java.util.Collection;

public class PersonneImpl implements Personne {
    @Override
    public String getNom() {
        return null;
    }

    @Override
    public void setNom(String nom) {

    }

    @Override
    public long getIdentifiant() {
        return 0;
    }

    @Override
    public void supprimerRole(String role) {
    }

    @Override
    public Collection<String> getRoles() {
        return null;
    }

    @Override
    public void addRole(String role) throws RoleDejaAttribueException {

    }

    @Override
    public void setMdp(String mdp) {

    }

    @Override
    public String getMdp() {
        return null;
    }

    @Override
    public boolean hasRole(String role) {
        return false;
    }
}
