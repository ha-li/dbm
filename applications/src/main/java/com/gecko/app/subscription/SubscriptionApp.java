package com.gecko.app.subscription;

import com.gecko.core.repository.ItemRepository;
import com.gecko.core.repository.MessageRepository;
import com.gecko.core.repository.Repository;
import com.gecko.subscription.domain.Item;
import com.gecko.subscription.domain.Message;
import com.gecko.subscription.domain.MessageType;
import com.gecko.subscription.domain.MonetaryAmount;
import com.gecko.subscription.domain.Sender;
import com.gecko.subscription.domain.Zipcode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hlieu on 07/8/17.
 */
public class SubscriptionApp {

   public static void messages () throws Exception {
      Message message = new Message();
      message.setText ("Ninja fighting machine!");
      message.setType(MessageType.JMS);

      Sender sender = new Sender ("Bob", "Caygeon");
      message.setSender(sender);
      Repository.save(message);

      List<Message> list = MessageRepository.getMessages();

      list.get(0).setText("I'm here to rule the world! Booyah");

      MessageRepository.updateMessage (list.get(0));
   }

   // inserts an item. Item has 2 custom types, a custom composite type
   // MonetaryAmountCustomUserType and
   // SpecialEncryptedCustomUserType
   public static void items () throws Exception {
      Message message = Repository.getById(Message.class, "370dee85-0fba-4d42-ac7b-d1a2fc0e477d");

      Item item = new Item ();

      // the MonetaryAmountCustomUserType will multiply the amount by 2
      // ex to simulate a foreign currency change
      MonetaryAmount m = new MonetaryAmount ("USD", new BigDecimal (5.00));
      item.setBidAmount (m);
      item.setName ("Jesus Christ statue");
      item.setAuctionEnd (LocalDateTime.now());
      item.setSignature ("Bob Leftner");
      item.setZipcode (Zipcode.valueOf ("34234"));
      item.setMessage(message);
      // SpecialEncryptedCustomUserType will encrypt the string in a "special" way
      // and decrypt during the nullSafeGet
      item.setEncryptedValue ("DoRaMe");
      Repository.save (item);
   }

   public static void getItem (String uuid) throws Exception {
      Item item = Repository.getById (Item.class, uuid);
      System.out.println (item.getBidAmount ().getAmount ());
      System.out.println (item.getEncryptedValue ());
   }

   public static void itemsAvg () throws Exception {
      Item item = Repository.getById (Item.class, "c91f108b-d9c1-4214-a6a4-fb0d07d79fc7");
      System.out.println (item.getAuctionTotal ());
   }

   public static void main (String[] args) throws Exception {
      /* ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder ().build ();
      MetadataSources sources = new MetadataSources (standardRegistry);
      MetadataBuilder metadataBuilder = sources.getMetadataBuilder ();
      metadataBuilder.applyBasicType (
              new MonetaryAmountCustomUserType(),
              new String[]{"AMOUNT, CURRENCY"}
      );
      Metadata metadata = metadataBuilder.build(); */


      SubscriptionApp.messages ();
      SubscriptionApp.items ();
      //SubscriptionApp.getItem ("29825c65-d69c-407a-b571-2b729dd549a4");
      //SubscriptionApp.itemsAvg ();
   }
}
