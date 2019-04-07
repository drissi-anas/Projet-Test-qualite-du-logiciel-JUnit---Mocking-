package facade;

import facade.erreurs.NomTopicDejaExistantException;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.personnes.Personne;

import java.util.ArrayList;

/**
 * Created by asus on 07/04/2019.
 */
public interface ForumService {

    ArrayList<Theme> getListeTheme();
    ArrayList<Topic> getListeTopicPourUnTheme(Theme theme);
    ArrayList<Message> getListeMessagePourUnTopic(Topic topic);
    void ajouterMessage(Topic topic,String string);
    Topic récupererTopic(String nomTopic);
    Theme récupererTheme(String nomTheme);
    Topic creerTopic(String nomTopic, Theme theme,String Message, Personne personne)throws NomTopicDejaExistantException;

}
