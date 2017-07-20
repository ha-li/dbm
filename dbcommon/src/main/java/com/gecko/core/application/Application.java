package com.gecko.core.application;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 07/19/17.
 */
public class Application {
   private static TransactionManagerSetup TMS;
   private static EntityManagerFactory EMF;
   private static String persistenceUnit = "ApplicationPU";

   static {
      try {
         TMS = new TransactionManagerSetup (Database.ORACLE);
         createEntityManagerFactory ();
      } catch (Exception ex) {
         ex.printStackTrace ();
         throw new ExceptionInInitializerError ("Exception with transaction manager setup object.");
      }
   }

   public static void createEntityManagerFactory() {
      try {
         EMF = Persistence.createEntityManagerFactory (persistenceUnit);
      } catch (Exception ex) {
         ex.printStackTrace ();
         throw new ExceptionInInitializerError ("Exception with transaction manager setup object.");
      }
   }

   public static EntityManager createEntityManager () {
      return EMF.createEntityManager ();
   }

   public static UserTransaction getUserTransaction () {
      return TMS.getUserTransaction ();
   }
}
