package com.gecko.app.subscription;

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
      JpaRepository.save(message);

      List<Message> list = MessageRepository.getMessages();

      list.get(0).setText("I'm here to rule the world! Booyah");

      return JpaRepository.update (list.get(0));
   }

   // inserts an item. Item has 2 custom types, a custom composite type
   // MonetaryAmountCustomUserType and
   // SpecialEncryptedCustomUserType
   public static Item items () throws Exception {
      Message message = messages ();
      Message savedMessage = JpaRepository.getById(Message.class, message.getId());

      Description description = new Description ();
      description.setDescription ("An auctionable item to display in a tank.");

      Item item = new Item ();
      item.setMessage (savedMessage);

      item.setDescription (description);
      JpaRepository.save(description);
      // the MonetaryAmountCustomUserType will multiply the amount by 2
      // ex to simulate a foreign currency change
      Random random = new Random();
      MonetaryAmount m = new MonetaryAmount ("USD", new BigDecimal (random.nextDouble ()));
      item.setBidAmount (m);

      item.setName ("Jesus Christ statue");
      item.setAuctionEnd (LocalDateTime.now());
      item.setSignature ("Bob Leftner");
      item.setZipcode (Zipcode.valueOf (String.valueOf(random.nextInt(100000))));
      item.setMessage(message);
      // SpecialEncryptedCustomUserType will encrypt the string in a "special" way
      // and decrypt during the nullSafeGet
      item.setEncryptedValue ("DoRaMe");
      return JpaRepository.save(item);
   }

   public static void getItem (String uuid) throws Exception {
      Item item = items();
      Item retrievedItem = JpaRepository.getById (Item.class, item.getId ());
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
      JpaRepository.save(description);

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
      JpaRepository.save(item);

      Bid bid = new Bid ();
      bid.setAmount(new BigDecimal (4.00));

      bid.setText("Random text");
      bid.setItem(item);

      item.getBids ().add (bid);
      JpaRepository.save (bid);
   }

   public static void remove (String id) throws Exception {
      Item item = JpaRepository.getById (Item.class, id);
      JpaRepository.remove (item);
   }

   public static void removeById (String id) throws Exception {
      JpaRepository.remove (Item.class, id);
   }

   public static void main (String[] args) throws Exception {

      //SubscriptionApp.messages ();
      //SubscriptionApp.items ();
      //SubscriptionApp.bids();
      SubscriptionApp.remove ("15d5c00e-ffae-4267-a1d3-0f127de95ced");
      //SubscriptionApp.removeById ("15d5c00e-ffae-4267-a1d3-0f127de95ced");
      //SubscriptionApp.getItem ("94824201-3f9a-4306-ad46-cf226cf4a42f");
      //SubscriptionApp.itemsAvg ();
   }
}
