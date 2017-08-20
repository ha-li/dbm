package com.gecko.core.application;

import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 * An application manageable UserTransaction object.
 * 
 */
public class UnitOfWork implements AutoCloseable {

   private static UserTransaction tx;

   public UnitOfWork () {}

   public static UnitOfWork beginUnitOfWork () {
      try {
         UnitOfWork unit = new UnitOfWork ();
         tx = Application.getUserTransaction ();
         tx.begin ();
         return unit;
      } catch (Exception e) {
         throw new RuntimeException ("UnitOfWork failed to begin.", e);
      }
   }

   public static void rollbackUnitOfWork () {
      try {
         int status = tx.getStatus ();
         if (status == Status.STATUS_NO_TRANSACTION) {
            return;
         }
         tx.rollback ();
      } catch (Exception e) {
         throw new RuntimeException ("UnitOfWork failed to rollback.", e);
      }
   }

   public static void commitUnitOfWork () {
      try {
         if (tx != null) {
            tx.commit ();
         }
      } catch (Exception e) {
         throw new RuntimeException ("UnitOfWork failed to commit.", e);
      }
   }

   public void rollback () {
      UnitOfWork.rollbackUnitOfWork ();
   }

   public void commit () {
      UnitOfWork.commitUnitOfWork ();
   }

   @Override
   public void close () {
      UnitOfWork.rollbackUnitOfWork ();
   }
}
