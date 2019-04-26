package serviceImpl.inscriptionImpl;

import modele.inscription.InscriptionPotentielle;

public class InscriptionImpl implements InscriptionPotentielle {


    private String nom;
    private String mdp;
    private static long compteur;
    private long identifiant;
    private String roleDemande;


    public InscriptionImpl(String nom, String mdp, String roleDemande) {
        this.nom = nom;
        this.mdp = mdp;
        compteur ++;
        this.identifiant = compteur;
        this.roleDemande=roleDemande;
    }


    @Override
    public String getRoleDemande() {
        return roleDemande;
    }

    @Override
    public void setRoleDemande(String roleDemande) {
        this.roleDemande = roleDemande;
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
    public void setIdentifiant(long identifiant) {
        this.identifiant = identifiant;
    }
}
