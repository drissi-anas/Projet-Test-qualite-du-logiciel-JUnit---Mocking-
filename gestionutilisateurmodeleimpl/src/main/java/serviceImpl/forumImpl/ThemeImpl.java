package serviceImpl.forumImpl;

import facade.erreurs.NomTopicDejaExistantException;
import modele.forum.Theme;
import modele.forum.Topic;

import java.util.ArrayList;
import java.util.Collection;

public class ThemeImpl implements Theme {


   private String nom;
   private static long compteur;
   private long identifiant;
   Collection<Topic> listeTopics= new ArrayList<>();

   public ThemeImpl(String nom) {
       this.nom = nom;
       compteur++;
       this.identifiant=compteur;
   }

   @Override
   public Collection<Topic> getListeTopics() {
       return listeTopics;
   }

   @Override
   public String getNom() {
       return nom;
   }

   @Override
   public long getIdentifiant() {
       return identifiant;
   }

   @Override
   public void ajouterTopic(Topic topic) throws NomTopicDejaExistantException {

       for (Topic t:listeTopics) {
           if (t.getNom().equals(topic.getNom())) {
               throw new NomTopicDejaExistantException();
           }

       }
       listeTopics.add(topic);

   }
}
