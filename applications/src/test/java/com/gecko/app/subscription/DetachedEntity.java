package com.gecko.app.subscription;

import com.gecko.core.application.Application;
import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.JpaRepository;
import com.gecko.core.repository.TestRepositoryUtil;
import com.gecko.subscription.domain.Item;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class DetachedEntity {

   @AfterClass
   public static void afterClass () {
      TestRepositoryUtil.truncateTables ();
   }

   @Test
   public void testIdentityWhileAttached () {
      Item item = ItemProvider.getItem ();

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save(item.getDescription ());
         JpaRepository.save(item.getMessage ());
         JpaRepository.save(item);
         UnitOfWork.commitUnitOfWork ();
      }

      Item retrievedItem;
      Item s2ndRetrievedItem;

      // this is the persistence context
      EntityManager em = Application.createEntityManager ();

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {

         // objects of the same type with the same id attached to the
         // persistence context are the same object
         retrievedItem = em.find (Item.class, item.getId());
         s2ndRetrievedItem = em.find (Item.class, item.getId());

         Assert.assertTrue (retrievedItem == s2ndRetrievedItem );
         Assert.assertTrue ( retrievedItem.equals(s2ndRetrievedItem) );


         UnitOfWork.commitUnitOfWork ();
      } finally {
         em.close ();
      }

   }

   @Test
   public void testIdentityAfterDetached () {
      Item item = ItemProvider.getItem ();

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save(item.getDescription ());
         JpaRepository.save(item.getMessage ());
         JpaRepository.save(item);
         UnitOfWork.commitUnitOfWork ();
      }

      Item retrievedItem;
      Item s2ndRetrievedItem;

      // this is the persistence context
      EntityManager em = Application.createEntityManager ();

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {

         // objects of the same type with the same id attached to the
         // persistence context are the same object
         retrievedItem = em.find (Item.class, item.getId());
         s2ndRetrievedItem = em.find (Item.class, item.getId());

         Assert.assertTrue (retrievedItem == s2ndRetrievedItem );
         Assert.assertTrue ( retrievedItem.equals(s2ndRetrievedItem) );


         UnitOfWork.commitUnitOfWork ();
      } finally {
         em.close ();
      }

      // this is still true because they were looked up using the same persistence context
      Assert.assertTrue (retrievedItem == s2ndRetrievedItem );
      Assert.assertTrue ( retrievedItem.equals(s2ndRetrievedItem) );
   }

   @Test
   public void testIdentityFromDifferentPersistentContext () {
      Item item = ItemProvider.getItem ();

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save(item.getDescription ());
         JpaRepository.save(item.getMessage ());
         JpaRepository.save(item);
         UnitOfWork.commitUnitOfWork ();
      }

      Item retrievedItem;
      Item s2ndRetrievedItem;

      // this is the persistence context
      EntityManager em = Application.createEntityManager ();
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {

         // objects of the same type with the same id attached to the
         // persistence context are the same object
         retrievedItem = em.find (Item.class, item.getId());

         UnitOfWork.commitUnitOfWork ();
      } finally {
         em.close ();
      }

      // this is the persistence context
      em = Application.createEntityManager ();
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {

         // objects of the same type with the same id attached to the
         // persistence context are the same object
         s2ndRetrievedItem = em.find (Item.class, item.getId());

         UnitOfWork.commitUnitOfWork ();
      } finally {
         em.close ();
      }

      // this is still true because they were looked up using the same persistence context
      Assert.assertFalse (retrievedItem == s2ndRetrievedItem );
      Assert.assertFalse ( retrievedItem.equals(s2ndRetrievedItem) );
   }
}
