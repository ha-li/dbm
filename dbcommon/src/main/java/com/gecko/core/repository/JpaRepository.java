package com.gecko.core.repository;

import com.gecko.core.application.Application;
import com.gecko.subscription.domain.Identity;

import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 07/31/17.
 */
public interface JpaRepository<T extends Identity>  {

   // T save (T entity) throws Exception;
   // T getById(String id) throws Exception;

   public static <T extends Identity> T save (T entity) throws SystemException {
      EntityManager em = null;
      UserTransaction tx = Application.getUserTransaction ();

      try {
         tx.begin ();
         em = Application.createEntityManager ();

         em.persist (entity);

         tx.commit ();
         return entity;
      } catch (Throwable t) {
         tx.rollback ();
         throw new RuntimeException ("Exception while persisting " + entity.getClass());
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> T getById (final Class<T> t, String id) throws SystemException {
      EntityManager em = null;
      UserTransaction tx = Application.getUserTransaction ();
      try {
         tx.begin ();

         em = Application.createEntityManager ();
         T item = em.find (t, id);

         tx.commit ();
         return item;
      } catch (Throwable th) {
         tx.rollback ();
         throw new RuntimeException ("Exception while retrieving id " + id );
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> T update (T message) throws SystemException {
      EntityManager em = null;
      UserTransaction tx = Application.getUserTransaction ();

      try {
         tx.begin ();

         em = Application.createEntityManager ();
         T m = em.merge (message);

         tx.commit ();
         return m;
      } catch (Throwable t) {
         tx.rollback ();
         throw new RuntimeException ("Exception while updating entity", t);
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> void remove (T t) throws SystemException {

      EntityManager em = null;
      UserTransaction tx = Application.getUserTransaction ();
      try {
         tx.begin ();
         em = Application.createEntityManager ();

         if (t != null) {
            Identity toRemove = em.find (t.getClass (), t.getId ());

            if (toRemove != null) {
               em.remove (toRemove);
            }
         }

         tx.commit ();
      } catch (Throwable th) {
         tx.rollback ();
         throw new RuntimeException ("Exception while removing entity " + t.getClass ().getName () + " with id " + t.getId (), th);
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> void remove (final Class<T> t, String id) throws SystemException {

      EntityManager em = null;
      UserTransaction tx = Application.getUserTransaction ();
      try {
         tx.begin ();
         em = Application.createEntityManager ();

         T item = em.find(t, id);
         if (item != null) {
            em.remove(item);
         }

         tx.commit();
      } catch (Throwable th)  {
         tx.rollback ();
         throw new RuntimeException ("Exception while removeing entity " + t.getName () + " with id " + id);
      } finally {
         em.close();
      }
   }
}
