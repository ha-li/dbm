package com.gecko.app.subscription;

import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.JpaRepository;
import com.gecko.core.repository.MessageRepository;
import com.gecko.core.repository.TestRepositoryUtil;
import com.gecko.subscription.domain.Bid;
import com.gecko.subscription.domain.Item;
import com.gecko.subscription.domain.Message;
import com.gecko.subscription.domain.MessageType;
import com.gecko.subscription.domain.Sender;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by hlieu on 07/11/17.
 */
public class SubscriptionAppTest {

   @AfterClass
   public static void cleanUp () {

      // this is in dbcommon/test/.. see build.gradle for how this is included
      TestRepositoryUtil.truncateTables ();
   }

   @Test
   public void testUpdate () throws Exception {

      Message message = new Message ();
      message.setText ("Hello World!");

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (message);
         UnitOfWork.commitUnitOfWork ();
      }

      List<Message> list;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         list = MessageRepository.getMessages ();
         UnitOfWork.commitUnitOfWork ();
      }

      message.setText("Bottoms up!");

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.update (message);
         UnitOfWork.commitUnitOfWork ();
      }

      List<Message> list2;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         list2 = MessageRepository.getMessages ();
         UnitOfWork.commitUnitOfWork ();
      }

      // Assert.assertEquals (list2.get(0).getText(), "Bottoms up!");

      List<Message> listAll;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         listAll = MessageRepository.getAllMessages ();
         UnitOfWork.commitUnitOfWork ();
      }

      boolean containsUpdateMessage = false;
      for (Message msg : listAll) {
         if (msg.getText().equals ("Bottoms up!")) {
            containsUpdateMessage = true;
         }
      }
      Assert.assertTrue (containsUpdateMessage);
   }

   @Test
   public void testMessages () {

      Message message = new Message();
      message.setText ("Ninja fighting machine!");
      message.setType(MessageType.JMS);

      Sender sender = new Sender ("Bob", "Caygeon");
      message.setSender(sender);

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()){
         JpaRepository.save (message);
         UnitOfWork.commitUnitOfWork ();
      }
      List<Message> list = null;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         list = MessageRepository.getMessages ();
      }

      list.get(0).setText("Randomly changed string");

      Message updatedMessage;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         updatedMessage = JpaRepository.update (list.get(0));
         UnitOfWork.commitUnitOfWork ();
      }

   }

   // inserts an item. Item has 2 custom types, a custom composite type
   // MonetaryAmountCustomUserType and
   // SpecialEncryptedCustomUserType
   @Test
   public void test_items () throws Exception {
      Item item = ItemProvider.getItem ();

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item.getMessage ());
         UnitOfWork.commitUnitOfWork ();
      }

      // retrieve the message we just saved
      Message savedMessage;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         savedMessage = JpaRepository.getById (Message.class, item.getMessage ().getId ());
         UnitOfWork.commitUnitOfWork ();
      }

      // do a quick assert to make sure we pulled what we just saved
      Assert.assertEquals (item.getMessage ().getText(), savedMessage.getText());
      Assert.assertEquals (item.getMessage ().getId(), savedMessage.getId());

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item.getDescription());
         UnitOfWork.commitUnitOfWork ();
      }

      // SpecialEncryptedCustomUserType will encrypt the string in a "special" way
      // and decrypt during the nullSafeGet
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save(item);
         UnitOfWork.commitUnitOfWork ();
      }

      Item retrievedItem;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         retrievedItem = JpaRepository.getById (Item.class, item.getId ());
         UnitOfWork.commitUnitOfWork ();
      }

      Assert.assertEquals (item.getId(), retrievedItem.getId());
   }

   @Test
   public void test_bid () throws Exception {
      Item item = ItemProvider.getItem ();

      // this is required to prevent transitive objects, or else
      // you can set cascade = CascadeType.PERSIST in the annotation
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item.getDescription());
         UnitOfWork.commitUnitOfWork ();
      }

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item.getMessage ());
         UnitOfWork.commitUnitOfWork ();
      }

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item);
         UnitOfWork.commitUnitOfWork ();
      }

      Bid bid = BidProvider.getBid ();
      bid.setItem(item);
      item.getBids ().add (bid);

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (bid);
         UnitOfWork.commitUnitOfWork ();
      }

      Bid retrievedBid;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         retrievedBid = JpaRepository.getById (Bid.class, bid.getId());
         UnitOfWork.commitUnitOfWork ();
      }

      Assert.assertEquals (bid.getId(), retrievedBid.getId());
   }

   @Test
   public void test_remove () throws Exception {

      Item item = ItemProvider.getItem ();

      // this is required to prevent transitive objects, or else
      // you can set cascade = CascadeType.PERSIST in the annotation
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item.getDescription());
         UnitOfWork.commitUnitOfWork ();
      }

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item.getMessage ());
         UnitOfWork.commitUnitOfWork ();
      }

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item);
         UnitOfWork.commitUnitOfWork ();
      }

      Item retrievedItem;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         retrievedItem = JpaRepository.getById (Item.class, item.getId());
         JpaRepository.remove (retrievedItem);
         UnitOfWork.commitUnitOfWork ();
      }

      Item deletedItem;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         deletedItem = JpaRepository.getById (Item.class, retrievedItem.getId());
         UnitOfWork.commitUnitOfWork ();
      }

      Assert.assertNull(deletedItem);
   }

   @Test
   public void rollbackTest () {
      Item item = ItemProvider.getItem ();

      // this is required to prevent transitive objects, or else
      // you can set cascade = CascadeType.PERSIST in the annotation
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item.getDescription());
         UnitOfWork.commitUnitOfWork ();
      }

      // this is required, otherwise you get a unsaved transient instance error
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item);
         UnitOfWork.rollbackUnitOfWork ();
      }

      Assert.assertNotNull (item.getId());
      Item failedRetrieval;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         failedRetrieval = JpaRepository.getById (Item.class, item.getId());
         UnitOfWork.commitUnitOfWork ();
      }

      Assert.assertNull (failedRetrieval);
   }

}