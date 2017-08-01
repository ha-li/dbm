package com.gecko.core.repository;

import com.gecko.core.application.Application;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 07/31/17.
 */
public class Repository {

   public static <T> T save (T entity) throws Exception {
      UserTransaction tx = Application.getUserTransaction();
      tx.begin();

      EntityManager em = Application.createEntityManager ();
      em.persist (entity);
      em.close();

      tx.commit ();
      return entity;
   }
}
