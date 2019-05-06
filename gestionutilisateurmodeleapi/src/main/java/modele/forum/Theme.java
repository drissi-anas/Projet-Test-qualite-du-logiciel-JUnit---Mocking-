package modele.forum;

import facade.erreurs.NomTopicDejaExistantException;

import java.util.Collection;

public interface Theme {


    Collection<Topic> getListeTopics();

    String getNom();
    long getIdentifiant();

    void ajouterTopic(Topic topic)throws NomTopicDejaExistantException;
}
