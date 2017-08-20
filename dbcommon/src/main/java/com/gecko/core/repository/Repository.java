package com.gecko.core.repository;

import com.gecko.core.application.Application;
import com.gecko.subscription.domain.Identity;
import com.gecko.subscription.domain.Message;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 07/31/17.
 */
public interface Repository<T extends Identity>  {

   // T save (T entity) throws Exception;
   // T getById(String id) throws Exception;

   public static <T extends Identity> T save (T entity) {

      try {
         UserTransaction tx = Application.getUserTransaction ();
         tx.begin ();

         EntityManager em = Application.createEntityManager ();
         em.persist (entity);
         em.close ();

         tx.commit ();
         return entity;
      } catch (Throwable t) {
         throw new RuntimeException ("Exception while persisting " + entity.getClass());
      }
   }

   public static <T extends Identity> T getById (final Class<T> t, String id) {
      try {
         UserTransaction tx = Application.getUserTransaction ();
         tx.begin ();

         EntityManager em = Application.createEntityManager ();
         T item = em.find (t, id);
         tx.commit ();
         em.close ();

         return item;
      } catch (Throwable th) {
         throw new RuntimeException ("Exception while retrieving id " + id );

      }
   }

   public static <T extends Identity> T update (T message) {
      try {
         UserTransaction tx = Application.getUserTransaction ();
         tx.begin ();

         EntityManager em = Application.createEntityManager ();
         T m = em.merge (message);
         em.close ();

         tx.commit ();
         return m;
      } catch (Throwable t) {
         throw new RuntimeException ("Exception while updating entity", t);
      }
   }

   public static <T extends Identity> void remove (T t) {
      try {
         UserTransaction tx = Application.getUserTransaction ();
         tx.begin ();

         EntityManager em = Application.createEntityManager ();

         if ( t != null) {
            Identity toRemove = em.find (t.getClass (), t.getId ());
            
            if (toRemove != null) {
               em.remove (toRemove);
            }
         }

         em.close ();
         tx.commit ();
      } catch (Throwable th) {
         throw new RuntimeException ("Exception while removing entity", th);
      }
   }
}
