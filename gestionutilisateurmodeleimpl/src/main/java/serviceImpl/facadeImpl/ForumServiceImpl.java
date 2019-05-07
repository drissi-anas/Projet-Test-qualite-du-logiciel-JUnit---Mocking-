package serviceImpl.facadeImpl;

import facade.ForumService;
import facade.erreurs.NomTopicDejaExistantException;
import facade.erreurs.ThemeInexistantException;
import facade.erreurs.TopicInexistantException;
import modele.forum.*;
import serviceImpl.forumImpl.MessageImpl;
import serviceImpl.forumImpl.ThemeImpl;
import serviceImpl.forumImpl.TopicImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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


// a faire
    @Override
    public Collection<Message> getListeMessagePourUnTopic(Topic topic) {
        return null;
    }




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

    // A faire
    @Override
    public Message creerMessage(String auteur,Topic topic, String texte) {
        Date date= new Date();
        return new MessageImpl(auteur,texte,date,topic);
    }

    @Override
    public void creerTheme(String nomTheme) {
        Theme theme = new ThemeImpl(nomTheme);
        listeThemes.add(theme);
    }

    @Override
    public Topic creerTopic(String nomTopic, Theme theme, String auteur) throws NomTopicDejaExistantException {
        return null;
    }

    /*--@Override
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
    }*/
}
