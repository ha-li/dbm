package com.gecko.core.repository;

import com.gecko.core.application.Application;
import com.gecko.subscription.domain.Identity;

import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by hlieu on 07/31/17.
 */
public interface JpaRepository<T extends Identity>  {

   public static <T extends Identity> T save (T entity) {
      EntityManager em = null;

      try {
         em = Application.createEntityManager ();
         em.persist (entity);

         return entity;
      } catch (Throwable t) {
         throw new RuntimeException ("Exception while persisting " + entity.getClass());
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> T getById (final Class<T> t, String id) {
      EntityManager em = null;
      try {

         em = Application.createEntityManager ();
         T item = em.find (t, id);

         return item;
      } catch (Throwable th) {
         throw new RuntimeException ("Exception while retrieving id " + id );
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> T update (T message) {
      EntityManager em = null;

      try {
         em = Application.createEntityManager ();
         T m = em.merge (message);

         return m;
      } catch (Throwable t) {
         throw new RuntimeException ("Exception while updating entity", t);
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> void remove (T t) throws SystemException {

      EntityManager em = null;
      try {
         em = Application.createEntityManager ();

         if (t != null) {
            Identity toRemove = em.find (t.getClass (), t.getId ());

            if (toRemove != null) {
               em.remove (toRemove);
            }
         }

      } catch (Throwable th) {
         throw new RuntimeException ("Exception while removing entity " + t.getClass ().getName () + " with id " + t.getId (), th);
      } finally {
         em.close ();
      }
   }

   public static <T extends Identity> void remove (final Class<T> t, String id) throws SystemException {

      EntityManager em = null;
      try {
         em = Application.createEntityManager ();

         T item = em.find(t, id);
         if (item != null) {
            em.remove(item);
         }

      } catch (Throwable th)  {
         throw new RuntimeException ("Exception while removeing entity " + t.getName () + " with id " + id);
      } finally {
         em.close();
      }
   }

   // a declarative transaction annotation
   @Transactional
   public static <T extends Identity> List<T> findAll(Class<T> t) {
      EntityManager em = null;
      try {
         em = Application.createEntityManager ();
         List<T> list = em.createQuery ("select a from " + t.getName() + " a").getResultList ();
         em.close ();
         return list;
      } catch (Throwable th) {
         throw new RuntimeException ("Exception while retrieving all " + t.getName() + ".", th);
      }
   }
}
