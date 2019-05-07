package serviceImpl.facadeImpl;

import facade.ConnexionService;
import facade.ForumService;
import facade.erreurs.*;
import modele.forum.*;
import modele.personnes.Personne;
import serviceImpl.forumImpl.MessageImpl;
import serviceImpl.forumImpl.ThemeImpl;
import serviceImpl.forumImpl.TopicImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static facade.AdminService.ADMIN;
import static facade.AdminService.MODERATEUR;

public class ForumServiceImpl implements ForumService {

    private ConnexionService connexionService;
    
    public ForumServiceImpl(Collection<Theme> listeThemes, ConnexionService connexionService) {
        this.connexionService=connexionService;

        Theme t = new ThemeImpl("Sante");
        this.listeThemes = listeThemes;
        listeThemes.add(t);
        Date date = new Date();
        Collection<Message>listeMessages=new ArrayList<>();
        Topic topic = new TopicImpl("Ilyas","Allergie",t,listeMessages);
        Message message= new MessageImpl("Ilyas","TEST",date,topic);
        Message message1= new MessageImpl("Anas","TEST2",date,topic);
        topic.getListeMessages().add(message);
        topic.getListeMessages().add(message1);
        t.getListeTopics().add(topic);
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
    public Collection<Message> getListeMessagePourUnTopic(Topic topic) throws TopicInexistantException {

        for (Theme t:listeThemes) {
            if(t.equals(topic.getTheme())){
                return topic.getListeMessages();
            }
        }
        throw new TopicInexistantException();

    }




    @Override
    public Topic recupererTopic(String nomTopic, String nomTheme) throws TopicInexistantException,ThemeInexistantException {

        boolean themeExistant= false;
        for (Theme t:listeThemes) {
            if(t.getNom().equals(nomTheme)){
                themeExistant=true;
            }
        }

        if(!themeExistant){
            throw new ThemeInexistantException();
        }

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

    @Override
    public Message creerMessage(String auteur,Topic topic, String texte) throws InformationManquanteException {
        if(texte==null || texte.equals("") ){
            throw new InformationManquanteException();
        }
        Date date= new Date();
        return new MessageImpl(auteur,texte,date,topic);
    }

    @Override
    public void creerTheme(String nomTheme) throws InformationManquanteException {
        if(nomTheme==null || nomTheme.equals("") ){
            throw new InformationManquanteException();
        }
        Theme theme = new ThemeImpl(nomTheme);
        listeThemes.add(theme);
    }

    @Override
    public Topic creerTopic(String nomTopic, Theme theme, String auteur) throws NomTopicDejaExistantException, InformationManquanteException {
        if(nomTopic==null || nomTopic.equals("") ){
            throw new InformationManquanteException();
        }
        Topic topic=null;
        for (Theme t:listeThemes) {
            if(t.getNom().equals(theme.getNom())){
                Collection<Message>listeMessages=new ArrayList<>();
                topic = new TopicImpl(auteur,nomTopic,theme,listeMessages);
                t.ajouterTopic(topic);

            }
        }
        return topic;
    }

    @Override
    public void supprimerMessage(Message m, long identifiant) throws ActionImpossibleException {
        Topic topic = m.getTopic();
        String auteur = m.getAuteur();
        String personneQuiVeutSupprimer=null;

        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getIdentifiant()==identifiant){
                personneQuiVeutSupprimer=p.getNom();
            }

        }

        Boolean isAdmin = false;
        Boolean isModerateur = false;

        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==identifiant){
                for (String s:p.getRoles()) {
                    if(s.equals(ADMIN)){
                        isAdmin=true;
                    }
                    if(s.equals(MODERATEUR)){
                        isModerateur=true;
                    }
                }
            }
        }

        if(auteur.equals(personneQuiVeutSupprimer)){
            topic.getListeMessages().remove(m);
        }else
            if(isAdmin || isModerateur){
                topic.getListeMessages().remove(m);
            }else {
                throw new ActionImpossibleException();
            }

    }


}
