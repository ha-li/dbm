package com.gecko.core.repository;

import com.gecko.core.application.Application;
import com.gecko.subscription.domain.Message;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by hlieu on 07/31/17.
 */
public class MessageRepository extends Repository {

   public static void updateMessage (Message message) throws Exception {
      UserTransaction tx = Application.getUserTransaction ();
      tx.begin();

      EntityManager em = Application.createEntityManager ();
      em.merge (message);
      em.close();

      tx.commit ();
   }

   public static List<Message> getMessages () throws Exception {
      UserTransaction tx = Application.getUserTransaction ();
      tx.begin ();

      EntityManager em = Application.createEntityManager ();
      List<Message> list = em.createQuery ("select m from com.gecko.subscription.domain.Message m ").getResultList ();

      tx.commit ();
      em.close();
      return list;
   }

   public static List<Message> getAllMessages () throws Exception {
      UserTransaction tx = Application.getUserTransaction();
      tx.begin();

      EntityManager em = Application.createEntityManager ();
      TypedQuery<Message> query = em.createNamedQuery("findMessageById", Message.class);
      List<Message> results = query.getResultList ();

      tx.commit ();
      return results;
   }
}
