package com.gecko.core.repository;

import com.gecko.core.application.Application;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 07/31/17.
 */
public interface Repository<T extends Object>  {

   // T save (T entity) throws Exception;
   // T getById(String id) throws Exception;

   public static <T> T save (T entity) throws Exception {
      UserTransaction tx = Application.getUserTransaction();
      tx.begin();

      EntityManager em = Application.createEntityManager ();
      em.persist (entity);
      em.close();

      tx.commit ();
      return entity;
   }

   public static <T> T getById (final Class<T> t, String id) throws Exception {
      UserTransaction tx = Application.getUserTransaction ();
      tx.begin();

      EntityManager em = Application.createEntityManager ();
      T item = em.find(t, id);
      tx.commit ();
      em.close();

      return item;
   }
}
