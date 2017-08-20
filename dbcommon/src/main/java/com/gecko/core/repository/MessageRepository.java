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
public class MessageRepository implements JpaRepository<Message> {

   public static List<Message> getMessages () {
      try {

         EntityManager em = Application.createEntityManager ();
         List<Message> list = em.createQuery ("select m from com.gecko.subscription.domain.Message m ").getResultList ();

         em.close ();
         return list;
      } catch (Throwable e) {
         throw new RuntimeException ("Getting all messages failed.", e);
      }
   }

   public static List<Message> getAllMessages () {

      try {
         EntityManager em = Application.createEntityManager ();
         TypedQuery<Message> query = em.createNamedQuery ("findMessageById", Message.class);
         List<Message> results = query.getResultList ();

         return results;
      } catch (Throwable t) {
         throw new RuntimeException ("Get All messages failed.", t);
      }
   }
}
