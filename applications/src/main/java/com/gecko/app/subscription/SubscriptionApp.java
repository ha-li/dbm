package com.gecko.app.subscription;

import com.gecko.env.Database;
import com.gecko.env.TransactionManagerSetup;
import com.gecko.subscription.domain.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 07/8/17.
 */
public class SubscriptionApp {

   static TransactionManagerSetup TMS;

   static EntityManagerFactory EMF;
   static {
      try {
         TMS = new TransactionManagerSetup (Database.ORACLE);
         EMF = Persistence.createEntityManagerFactory ("ApplicationPU");
      } catch (Exception ex) {
         ex.printStackTrace ();
         throw new ExceptionInInitializerError ("Exception with transaction manager setup object.");
      }
   }


   public void insertMessage () throws Exception {
      UserTransaction tx = TMS.getUserTransaction ();
      tx.begin();

      EntityManager em = EMF.createEntityManager ();

      // em is thread safe
      Message message = new Message ();
      //message.setId (1L);
      message.setText ("Hello World!");
      em.persist (message);
      tx.commit ();
      em.close();
   }

   public static void main (String[] args) throws Exception {
      SubscriptionApp app = new SubscriptionApp ();
      app.insertMessage();

   }
}
