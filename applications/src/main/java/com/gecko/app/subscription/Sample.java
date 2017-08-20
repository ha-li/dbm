package com.gecko.app.subscription;

import com.gecko.core.application.Application;
import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.JpaRepository;
import com.gecko.core.repository.RepositoryUtil;
import com.gecko.subscription.domain.Item;
import com.gecko.subscription.domain.Message;
import com.gecko.subscription.domain.Sender;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import sun.tools.jconsole.Plotter;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample {
   public static void changeState (String id) throws Exception {

      try ( UnitOfWork unit = UnitOfWork.beginUnitOfWork () ) {
         Item item = JpaRepository.getById (Item.class, id);
         item.setName ("DopeAss");

         UnitOfWork.commitUnitOfWork ();
      }
   }

   // these should load proxy but some reason it's generating sql statements
   public static void referenceItem (String id) {
      EntityManager em = Application.createEntityManager ();
      Item item;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         item = em.getReference (Item.class, id);
      }
      String name = item.getName ();  // this will force the persistence context to get
      // the actual item
      boolean isLoaded = RepositoryUtil.isLoaded (item);
      System.out.println ("item is loaded: " + isLoaded);
      System.out.println ("item name " + name);
   }

   public static void referenceMessage (String id) {
      EntityManager em = Application.createEntityManager ();
      Message item;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         item = em.getReference (Message.class, id);
      }
      Sender sender = item.getSender ();  // this will force the persistence context to get
      // the actual item
      boolean isLoaded = RepositoryUtil.isLoaded (item);
      System.out.println ("item is loaded: " + isLoaded);
      System.out.println ("item name " + sender.getFirstName ());
   }



   public static void main (String[] args) throws Exception {
      //changeState ("15d5c00e-ffae-4267-a1d3-0f127de95ced");
      //referenceItem ("291d2b35-416a-46b3-8ad6-7d73da09f2a3");
      // referenceMessage ("0739e47e-ed2d-4a3e-85c2-706ed8d469aa");

   }
}
