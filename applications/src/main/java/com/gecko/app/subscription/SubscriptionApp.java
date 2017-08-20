package com.gecko.app.subscription;

import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.MessageRepository;
import com.gecko.core.repository.JpaRepository;
import com.gecko.subscription.domain.Bid;
import com.gecko.subscription.domain.Description;
import com.gecko.subscription.domain.Item;
import com.gecko.subscription.domain.Message;
import com.gecko.subscription.domain.MessageType;
import com.gecko.subscription.domain.MonetaryAmount;
import com.gecko.subscription.domain.Sender;
import com.gecko.subscription.domain.Zipcode;
import sun.tools.jconsole.Plotter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Created by hlieu on 07/8/17.
 */
public class SubscriptionApp {

   public static Message messages () throws Exception {

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
      return updatedMessage;
   }

   // inserts an item. Item has 2 custom types, a custom composite type
   // MonetaryAmountCustomUserType and
   // SpecialEncryptedCustomUserType
   public static Item items () throws Exception {
      Message message = messages ();
      Message savedMessage;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         savedMessage = JpaRepository.getById (Message.class, message.getId ());
         UnitOfWork.commitUnitOfWork ();
      }

      Description description = new Description ();
      description.setDescription ("Randomly walking on the sidewalks.");

      Item item = new Item ();
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         item.setMessage (savedMessage);
         UnitOfWork.commitUnitOfWork ();
      }

      item.setDescription (description);

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (description);
         UnitOfWork.commitUnitOfWork ();
      }

      // the MonetaryAmountCustomUserType will multiply the amount by 2
      // ex to simulate a foreign currency change
      Random random = new Random();
      MonetaryAmount m = new MonetaryAmount ("USD", new BigDecimal (random.nextDouble ()));
      item.setBidAmount (m);

      item.setName ("Underwater playground");
      item.setAuctionEnd (LocalDateTime.now());
      item.setSignature ("Guy Lafleur");
      item.setZipcode (Zipcode.valueOf (String.valueOf(random.nextInt(100000))));
      item.setMessage(message);
      // SpecialEncryptedCustomUserType will encrypt the string in a "special" way
      // and decrypt during the nullSafeGet
      item.setEncryptedValue ("DoRaMe2");
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save(item);
         UnitOfWork.commitUnitOfWork ();
      }

      return item;
   }

   public static void getItem (String uuid) throws Exception {
      Item item = items();
      Item retrievedItem;

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         retrievedItem = JpaRepository.getById (Item.class, item.getId ());
         UnitOfWork.commitUnitOfWork ();
      }

      System.out.println (retrievedItem.getBidAmount ().getAmount ());
      System.out.println (retrievedItem.getEncryptedValue ());
   }

   public static void itemsAvg () throws Exception {
      Item item = items();
      Item retrieveItem = JpaRepository.getById (Item.class, item.getId());
      System.out.println (retrieveItem.getAuctionTotal ());
   }

   public static void bids () throws Exception {
      Item item = new Item();

      Description description = new Description ();
      description.setDescription ("Hello description.");
      item.setDescription (description);

      // this is required to prevent transitive objects, or else
      // you can set cascade = CascadeType.PERSIST in the annotation
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (description);
         UnitOfWork.commitUnitOfWork ();
      }

      // the MonetaryAmountCustomUserType will multiply the amount by 2
      // ex to simulate a foreign currency change
      Random random = new Random();
      MonetaryAmount m = new MonetaryAmount ("USD", new BigDecimal (random.nextDouble ()));
      item.setBidAmount (m);
      item.setName ("Reefer madness");
      item.setAuctionEnd (LocalDateTime.now());
      item.setSignature ("Jacob Marley");
      item.setZipcode (Zipcode.valueOf (String.valueOf(random.nextInt (100000))));

      // SpecialEncryptedCustomUserType will encrypt the string in a "special" way
      // and decrypt during the nullSafeGet
      item.setEncryptedValue ("FloRayFlowSo");

      // this is required, otherwise you get a unsaved transient instance error
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item);
         UnitOfWork.commitUnitOfWork ();
      }

      Bid bid = new Bid ();
      bid.setAmount(new BigDecimal (4.00));

      bid.setText("Random text");
      bid.setItem(item);

      item.getBids ().add (bid);

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (bid);
         UnitOfWork.commitUnitOfWork ();
      }
   }

   public static void remove (String id) throws Exception {
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         Item item = JpaRepository.getById (Item.class, id);
         JpaRepository.remove (item);
         UnitOfWork.commitUnitOfWork ();
      }
   }

   public static void removeById (String id) throws Exception {
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.remove (Item.class, id);
         UnitOfWork.commitUnitOfWork ();
      }
   }

   public static void rollbackTest () {
      Item item = new Item();

      Description description = new Description ();
      description.setDescription ("This is there.");
      item.setDescription (description);

      // this is required to prevent transitive objects, or else
      // you can set cascade = CascadeType.PERSIST in the annotation
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (description);
         UnitOfWork.commitUnitOfWork ();
      }

      // the MonetaryAmountCustomUserType will multiply the amount by 2
      // ex to simulate a foreign currency change
      Random random = new Random();
      MonetaryAmount m = new MonetaryAmount ("USD", new BigDecimal (random.nextDouble ()));
      item.setBidAmount (m);
      item.setName ("Disappear");
      item.setAuctionEnd (LocalDateTime.now());
      item.setSignature ("Death passes by");
      item.setZipcode (Zipcode.valueOf (String.valueOf(random.nextInt (100000))));

      // SpecialEncryptedCustomUserType will encrypt the string in a "special" way
      // and decrypt during the nullSafeGet
      item.setEncryptedValue ("Top Secret");

      // this is required, otherwise you get a unsaved transient instance error
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (item);
         UnitOfWork.rollbackUnitOfWork ();
      }
   }

   public static void main (String[] args) throws Exception {

      SubscriptionApp.messages ();
      // SubscriptionApp.items ();
      //SubscriptionApp.bids();
      //SubscriptionApp.rollbackTest ();
      //SubscriptionApp.remove ("15d5c00e-ffae-4267-a1d3-0f127de95ced");
      //SubscriptionApp.removeById ("c7e03042-16af-4dba-b292-ebebe4ece533");
      //SubscriptionApp.getItem ("94824201-3f9a-4306-ad46-cf226cf4a42f");
      //SubscriptionApp.itemsAvg ();
   }
}
