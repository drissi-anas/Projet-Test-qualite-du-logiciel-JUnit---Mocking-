package modele.forum;

import modele.personnes.Personne;

import java.util.Collection;

/**
 * Created by asus on 07/04/2019.
 */
public interface Topic {

    String getNom();
    Theme getTheme();
    long getIdentifiant();
    Personne getCreateur();
    Collection<Message> getListeMessages();
    boolean ajouterMessage(Message message);


}
