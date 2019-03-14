package modele.inscription;

public interface InscriptionPotentielle {
    String getRoleDemande();

    void setRoleDemande(String roleDemande);

    String getNom();

    void setNom(String nom);

    String getMdp();

    void setMdp(String mdp);

    long getIdentifiant();

    void setIdentifiant(long identifiant);
}
