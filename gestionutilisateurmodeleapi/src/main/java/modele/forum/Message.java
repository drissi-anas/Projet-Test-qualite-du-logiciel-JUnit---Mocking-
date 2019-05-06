package modele.forum;

import modele.personnes.Personne;

import java.util.Date;

/**
 * Created by asus on 07/04/2019.
 */
public interface Message {


    Topic getTopic();
    long getIdentifiant();
    String getAuteur();
    Date getDate();
    void setText(String texte);
    String getText();

}
