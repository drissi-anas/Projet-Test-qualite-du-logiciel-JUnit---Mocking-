package facade;

import facade.erreurs.NomTopicDejaExistantException;
import facade.erreurs.ThemeInexistantException;
import facade.erreurs.TopicInexistantexception;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;

import java.util.Collection;

/**
 * Created by asus on 07/04/2019.
 */
public interface ForumService {

    Collection<Theme> getListeTheme();

    Collection<Topic> getListeTopicPourUnTheme(Theme theme) throws ThemeInexistantException;
    Theme recupererTheme(String nomTheme) throws ThemeInexistantException;

    Collection<Message> getListeMessagePourUnTopic(Topic topic)throws TopicInexistantexception;


    Topic recupererTopic(Theme theme,String nomTopic) throws TopicInexistantexception;
    void ajouterMessage(Theme theme,Topic topic,String string);


    void creerTheme(String nomTheme);
    Topic creerTopic(String nomTopic, Theme theme,String Message, String auteur)throws NomTopicDejaExistantException;

}
