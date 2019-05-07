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

    Collection<Message> getListeMessagePourUnTopic(Topic topic) throws TopicInexistantException;

    Topic recupererTopic(String nomTopic, String nomTheme) throws TopicInexistantException;

    void ajouterMessage(Topic topic, Theme theme, Message message);

    Message creerMessage(String auteur,Topic topic,String string);

    void creerTheme(String nomTheme);

    Topic creerTopic(String nomTopic, Theme theme, String auteur)throws NomTopicDejaExistantException;





    // Donc celle on va surement la supp mais gardons la pour le moment
  //  Topic recupererTopic(String nomTopic);
}
