package com.gecko.app.subscription;

import com.gecko.core.application.Application;
import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.JpaRepository;
import com.gecko.core.repository.TestRepositoryUtil;
import com.gecko.subscription.domain.Item;
import com.gecko.subscription.domain.Message;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

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

      Set<Item> uniqueItems = new HashSet<>();
      uniqueItems.add(retrievedItem);
      uniqueItems.add(s2ndRetrievedItem);

      Assert.assertEquals (uniqueItems.size(), 1);
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

      // we override the equals so this is true
      Assert.assertTrue ( retrievedItem.equals(s2ndRetrievedItem) );

      // however they still have the same id value because they represent the same
      // record in the database
      Assert.assertEquals (retrievedItem.getId(), s2ndRetrievedItem.getId());

      Set<Item> uniqueItems = new HashSet<> ();
      uniqueItems.add (retrievedItem);
      uniqueItems.add (s2ndRetrievedItem);

      // Set.add (Object) calls object.equals to prevent duplicates
      // since we override equals so that the same object
      // is the same object, so now this set is 1
      Assert.assertEquals (uniqueItems.size () , 1);
   }

   @Test
   public void testMessageIdentity () {
      Message m1 = MessageProvider.getMessage();

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (m1);
         UnitOfWork.commitUnitOfWork ();
      }

      Message retrieveM1 = JpaRepository.getById (Message.class, m1.getId());
      Message retrieveM2 = JpaRepository.getById (Message.class, m1.getId());


      // since they are from different persistent context, they
      // have differnet address space, so this is false
      Assert.assertFalse (retrieveM1 == retrieveM2);


      // since Message has change equals to be based on id, this is true
      Assert.assertTrue (retrieveM1.equals (retrieveM2));

      Set<Message> uniqueSet = new HashSet<>();
      uniqueSet.add(retrieveM1);
      uniqueSet.add (retrieveM2);

      // since Set.add tests equals, and inserts only if they are different,
      // this set is size 1,
      Assert.assertEquals ( 1, uniqueSet.size());
   }
}
