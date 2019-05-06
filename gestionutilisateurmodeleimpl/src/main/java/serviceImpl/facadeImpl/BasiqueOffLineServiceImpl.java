package serviceImpl.facadeImpl;

import facade.BasiquesOffLineService;
import facade.erreurs.InformationManquanteException;
import serviceImpl.inscriptionImpl.InscriptionImpl;
import modele.inscription.InscriptionPotentielle;


import java.util.ArrayList;
import java.util.Collection;

public class BasiqueOffLineServiceImpl implements BasiquesOffLineService {

    Collection<InscriptionPotentielle> listeDemandes= new ArrayList<>();

    @Override
    public long posterDemandeInscription(String pseudo, String motDePasse, String roleDemande)throws InformationManquanteException {

        if(pseudo==null || motDePasse==null || roleDemande==null || pseudo.equals("") || motDePasse.equals("") || roleDemande.equals("")){
            throw new InformationManquanteException();
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
