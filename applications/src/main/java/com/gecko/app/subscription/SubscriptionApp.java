package com.gecko.app.subscription;

import com.gecko.env.Database;
import com.gecko.env.TransactionManagerSetup;
import com.gecko.subscription.domain.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by hlieu on 07/8/17.
 */
public class SubscriptionApp {



   static TransactionManagerSetup TMS;

   private EntityManagerFactory EMF;
   private String peristentUnitName;

   static {
      try {
         TMS = new TransactionManagerSetup (Database.ORACLE);
         //EMF = Persistence.createEntityManagerFactory ("ApplicationPU");
      } catch (Exception ex) {
         ex.printStackTrace ();
         throw new ExceptionInInitializerError ("Exception with transaction manager setup object.");
      }
   }

   public SubscriptionApp(String pUName) {
      peristentUnitName = pUName;
      try {
         EMF = Persistence.createEntityManagerFactory (peristentUnitName);
      } catch (Exception ex) {
         ex.printStackTrace ();
         throw new ExceptionInInitializerError ("Exception with transaction manager setup object.");
      }
   }

   public void updateMessage (Message message) throws Exception {
      UserTransaction tx = TMS.getUserTransaction ();
      tx.begin();

      EntityManager em = EMF.createEntityManager ();

      em.merge (message);
      tx.commit ();
      em.close();
   }

   public List<Message> getMessages () throws Exception {
      UserTransaction tx = TMS.getUserTransaction ();
      tx.begin ();

      EntityManager em = EMF.createEntityManager ();
      List<Message> list = em.createQuery ("select m from com.gecko.subscription.domain.Message m ").getResultList ();

      tx.commit ();
      em.close();
      return list;
   }

   public void saveMessage (Message message) throws Exception {
      UserTransaction tx = TMS.getUserTransaction ();
      tx.begin ();

      EntityManager em = EMF.createEntityManager ();
      em.persist (message);

      tx.commit ();
      em.close();
   }

   public static void main (String[] args) throws Exception {
      SubscriptionApp app = new SubscriptionApp ("ApplicationPU");

      Message message = new Message();
      message.setText ("Hello World!");
      app.saveMessage(message);

      List<Message> list = app.getMessages();

      list.get(0).setText("I'm here to rule the world!");
      app.updateMessage (list.get(0));
   }
}
