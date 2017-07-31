package com.gecko.app.subscription;

import com.gecko.core.application.Application;
import com.gecko.subscription.domain.Message;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by hlieu on 07/8/17.
 */
public class SubscriptionApp {

   public SubscriptionApp() {
   }

   public void updateMessage (Message message) throws Exception {
      UserTransaction tx = Application.getUserTransaction ();
      tx.begin();

      EntityManager em = Application.createEntityManager ();

      em.merge (message);
      tx.commit ();
      em.close();
   }

   public List<Message> getMessages () throws Exception {

      UserTransaction tx = Application.getUserTransaction ();
      tx.begin ();

      EntityManager em = Application.createEntityManager ();
      List<Message> list = em.createQuery ("select m from com.gecko.subscription.domain.Message m ").getResultList ();

      tx.commit ();
      em.close();
      return list;
   }

   public void saveMessage (Message message) throws Exception {
      UserTransaction tx = Application.getUserTransaction ();
      tx.begin ();

      EntityManager em = Application.createEntityManager ();
      em.persist (message);

      tx.commit ();
      em.close();
   }

   public List<Message> getAllMessages () throws Exception {
      UserTransaction tx = Application.getUserTransaction();
      tx.begin();

      EntityManager em = Application.createEntityManager ();
      TypedQuery<Message> query = em.createNamedQuery("findMessageById", Message.class);
      List<Message> results = query.getResultList ();

      tx.commit ();
      return results;
   }

   public static void main (String[] args) throws Exception {
      SubscriptionApp app = new SubscriptionApp ();

      Message message = new Message();
      message.setText ("Hello World!");
      app.saveMessage(message);

      List<Message> list = app.getMessages();

      list.get(0).setText("I'm here to rule the world!");
      app.updateMessage (list.get(0));
   }
}
