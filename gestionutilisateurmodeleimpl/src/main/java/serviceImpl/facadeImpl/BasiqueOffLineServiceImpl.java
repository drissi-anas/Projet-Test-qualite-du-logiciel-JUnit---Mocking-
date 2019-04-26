package serviceImpl.facadeImpl;

import facade.BasiquesOffLineService;
import serviceImpl.inscriptionImpl.InscriptionImpl;
import modele.inscription.InscriptionPotentielle;


import java.util.ArrayList;
import java.util.Collection;

public class BasiqueOffLineServiceImpl implements BasiquesOffLineService {

    Collection<InscriptionPotentielle> listeDemandes= new ArrayList<>();

    @Override
    public long posterDemandeInscription(String pseudo, String motDePasse, String roleDemande) {
        InscriptionPotentielle inscriptionPotentielle = new InscriptionImpl(pseudo,motDePasse,roleDemande);
        listeDemandes.add(inscriptionPotentielle);
        return 1;
    }
}
