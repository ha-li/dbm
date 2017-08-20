package com.gecko.core.repository;

import com.gecko.core.application.Application;
import com.gecko.subscription.domain.Identity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

public class RepositoryUtil {

   public static <T extends Identity> boolean isTransient (T entity) {
      PersistenceUnitUtil puu = Application.createPersistenceUnitUtil ();

      boolean isTransient = true;
      if (puu.getIdentifier (entity) != null) {
         isTransient = false;
      }
      return isTransient;
   }

   public static <T extends Identity> boolean isPersistent (T entity) {
      EntityManager em = Application.createEntityManager ();

      boolean isPersistent = false;
      if (em.contains(entity)) {
         isPersistent = true;
      }
      em.close ();
      return isPersistent;
   }

   public static <T extends Identity> boolean isDetached (T entity) {
      PersistenceUnitUtil puu = Application.createPersistenceUnitUtil ();

      boolean isDetached = false;
      if ( ! isPersistent(entity) && puu.getIdentifier (entity) != null) {
         isDetached = true;
      }
      // em.close ();
      return isDetached;
   }
}
