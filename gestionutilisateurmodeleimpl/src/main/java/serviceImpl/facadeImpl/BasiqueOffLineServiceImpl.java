package serviceImpl.facadeImpl;

import facade.BasiquesOffLineService;
import facade.ConnexionService;
import facade.erreurs.InformationManquanteException;
import facade.erreurs.UtilisateurDejaExistantException;
import modele.personnes.Personne;
import serviceImpl.inscriptionImpl.InscriptionImpl;
import modele.inscription.InscriptionPotentielle;


import java.util.ArrayList;
import java.util.Collection;

public class BasiqueOffLineServiceImpl implements BasiquesOffLineService {

    Collection<InscriptionPotentielle> listeDemandes;
    ConnexionService connexionService;

    public BasiqueOffLineServiceImpl(ConnexionService connexionService) { ;
        this.connexionService = connexionService;
        listeDemandes= new ArrayList<>();
    }



    @Override
    public long posterDemandeInscription(String pseudo, String motDePasse, String roleDemande) throws InformationManquanteException, UtilisateurDejaExistantException {


        if(pseudo==null || motDePasse==null || roleDemande==null || pseudo.equals("") || motDePasse.equals("") || roleDemande.equals("")){
            throw new InformationManquanteException();
        }

        for (Personne p: connexionService.getListeUtilisateurs()) {
            if(p.getNom().equals(pseudo)){
                throw new UtilisateurDejaExistantException();
            }

        }


        InscriptionPotentielle inscriptionPotentielle = new InscriptionImpl(pseudo,motDePasse,roleDemande);
        listeDemandes.add(inscriptionPotentielle);

        return inscriptionPotentielle.getIdentifiant();
    }

    @Override
    public Collection<InscriptionPotentielle> getListeDemandes() {
        return listeDemandes;
    }
}
