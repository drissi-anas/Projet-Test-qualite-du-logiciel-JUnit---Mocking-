package serviceImpl.facadeImpl;

import facade.ForumService;
import facade.erreurs.NomTopicDejaExistantException;
import facade.erreurs.ThemeInexistantException;
import facade.erreurs.TopicInexistantException;
import modele.forum.*;
import serviceImpl.forumImpl.ThemeImpl;
import serviceImpl.forumImpl.TopicImpl;

import java.util.ArrayList;
import java.util.Collection;

public class ForumServiceImpl implements ForumService {

    
    public ForumServiceImpl(Collection<Theme> listeThemes) {
        this.listeThemes = listeThemes;
    }

    Collection<Theme>listeThemes;

    @Override
    public Collection<Theme> getListeTheme() {

        return listeThemes;

    }

    @Override
    public Collection<Topic> getListeTopicPourUnTheme(Theme theme) throws ThemeInexistantException {

        for (Theme t:listeThemes) {
            if(t.equals(theme)){
                return t.getListeTopics();
            }

        }
        throw new ThemeInexistantException();
    }

    @Override
    public Theme recupererTheme(String nomTheme) throws ThemeInexistantException {

        for (Theme t:listeThemes) {
            if(t.getNom().equals(nomTheme)){
                return t;
            }
        }

        throw new ThemeInexistantException();
    }



    @Override
    public Collection<Message> getListeMessagePourUnTopic(Topic topic) {
        return null;
    }

    // je crois qu'on doit supprimer celle la
    @Override
    public Topic recupererTopic(String nomTopic) {
        return null;
    }

    //Faut ajouter erreur topic inexistant
    @Override
    public Topic recupererTopic(String nomTopic, String nomTheme) throws TopicInexistantException {

        for (Theme t:listeThemes) {
            if(t.getNom().equals(nomTheme)){
                for (Topic top:t.getListeTopics()) {
                    if(top.getNom().equals(nomTopic)){
                        return top;
                    }
                }
            }
        }

        throw new TopicInexistantException();
    }

    // je crois qu'on doit supprimer celle la
    @Override
    public void ajouterMessage(Topic topic, String string) {

    }

    @Override
    public void ajouterMessage(Topic topic, Theme theme, Message message) {

        for (Theme t:listeThemes) {
            if(t.getNom().equals(theme.getNom())){
                for (Topic top:t.getListeTopics()) {
                    if(top.getNom().equals(topic.getNom())){

                        top.ajouterMessage(message);
                    }
                }
            }
        }
    }

    @Override
    public void creerTheme(String nomTheme) {
        Theme theme = new ThemeImpl(nomTheme);
        listeThemes.add(theme);
    }

    @Override
    public Topic creerTopic(String nomTopic, Theme theme, Message Message, String auteur) throws NomTopicDejaExistantException {

        Topic topic=null;
        for (Theme t:listeThemes) {
            if(t.getNom().equals(theme.getNom())){
                Collection<Message>listeMessages=new ArrayList<>();
                listeMessages.add(Message);
                 topic = new TopicImpl(auteur,nomTopic,theme,listeMessages);
                t.ajouterTopic(topic);

            }
        }
        return topic;
    }
}
