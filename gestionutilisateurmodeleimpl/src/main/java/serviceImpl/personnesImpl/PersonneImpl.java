package serviceImpl.personnesImpl;

import facade.erreurs.RoleDejaAttribueException;
import modele.personnes.Personne;

import java.util.ArrayList;
import java.util.Collection;

public class PersonneImpl implements Personne {

    private String nom;
    private String mdp;
    private static long compteur;
    private long identifiant;
    private Collection<String>roles=new ArrayList<>();

    public PersonneImpl(String nom, String mdp) {
        this.nom = nom;
        this.mdp = mdp;
        compteur ++;
        this.identifiant = compteur;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getMdp() {
        return mdp;
    }

    @Override
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public long getIdentifiant() {
        return identifiant;
    }


    @Override
    public void supprimerRole(String role) {
        String role1=null;
        for (String s: roles) {
            if(s.equals(role)){
                role1=s;
            }
        }
        roles.remove(role1);
    }

    @Override
    public Collection<String> getRoles() {
        return roles;
    }

    @Override
    public void addRole(String role) throws RoleDejaAttribueException {

        for (String s: roles) {
            if(s.equals(role)){
                throw new RoleDejaAttribueException();
            }
        }

        this.roles.add(role);

    }
    @Override
    public boolean hasRole(String role) {
        for (String s: roles) {
            if(s.equals(role)){
                return true;
            }
        }
        return false;

    }
}
