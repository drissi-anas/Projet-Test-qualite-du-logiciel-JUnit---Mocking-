package modele.forum;

import modele.personnes.Personne;

import java.util.Date;

/**
 * Created by asus on 07/04/2019.
 */
public interface Message {


    String getNom();
    Topic getTopic();
    long getIdentifiant();
    Personne getAuteur();
    Date getDate();
    void setText(String texte);
    void getText();

}
