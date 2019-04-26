package facade;

import facade.erreurs.NomTopicDejaExistantException;
import facade.erreurs.ThemeInexistantException;
import facade.erreurs.TopicInexistantException;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.personnes.Personne;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by asus on 07/04/2019.
 */
public interface ForumService {

    Collection<Theme> getListeTheme();

    Collection<Topic> getListeTopicPourUnTheme(Theme theme) throws ThemeInexistantException;
    Theme recupererTheme(String nomTheme) throws ThemeInexistantException;

    Collection<Message> getListeMessagePourUnTopic(Topic topic);


    // J'ai jouté celle la psk il faut le Theme pour savoir ou chercher le topic
    Topic recupererTopic(String nomTopic, String nomTheme) throws TopicInexistantException;
    // Donc celle on va surement la supp mais gardons la pour le moment
    Topic recupererTopic(String nomTopic);

    // J'ai jouté celle la psk il faut le Theme pour savoir ou ajouter le topic
    void ajouterMessage(Topic topic, Theme theme, Message message);
    // Donc celle on va surement la supp mais gardons la pour le moment
    void ajouterMessage(Topic topic,String string);


    void creerTheme(String nomTheme);

    //j'ai changer la valeur de retour, c'eter Topic, j'ai mis void
    Topic creerTopic(String nomTopic, Theme theme,Message Message, String auteur)throws NomTopicDejaExistantException;

}
