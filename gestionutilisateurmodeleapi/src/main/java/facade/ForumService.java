package facade;

import facade.erreurs.NomTopicDejaExistantException;
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

    Collection<Topic> getListeTopicPourUnTheme(Theme theme);
    Theme récupererTheme(String nomTheme);

    Collection<Message> getListeMessagePourUnTopic(Topic topic);
    Topic récupererTopic(String nomTopic);

    void ajouterMessage(Topic topic,String string);


    void creerTheme(String nomTheme, long id);
    Topic creerTopic(String nomTopic, Theme theme,String Message, String auteur)throws NomTopicDejaExistantException;

}
