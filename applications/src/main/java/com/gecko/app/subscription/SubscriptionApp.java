package com.gecko.app.subscription;

import com.gecko.core.repository.ItemRepository;
import com.gecko.core.repository.MessageRepository;
import com.gecko.subscription.domain.Item;
import com.gecko.subscription.domain.Message;
import com.gecko.subscription.domain.MessageType;
import com.gecko.subscription.domain.MonetaryAmount;
import com.gecko.subscription.domain.Sender;

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
      MessageRepository.save(message);

      List<Message> list = MessageRepository.getMessages();

      list.get(0).setText("I'm here to rule the world! Booyah");

      MessageRepository.updateMessage (list.get(0));
   }

   public static void items () throws Exception {
      Item item = new Item ();

      MonetaryAmount m = new MonetaryAmount ("USD", 5.00);
      item.setBidAmount (m);
      item.setName ("Jesus Christ statue");
      item.setAuctionEnd (LocalDateTime.now());
      item.setSignature ("Bob Leftner");
      ItemRepository.save (item);
   }

   public static void itemsAvg () throws Exception {
      Item item = ItemRepository.getById ("c91f108b-d9c1-4214-a6a4-fb0d07d79fc7");
      System.out.println (item.getAuctionTotal ());
   }

   public static void main (String[] args) throws Exception {
      //SubscriptionApp.messages ();
      SubscriptionApp.items ();
      //SubscriptionApp.itemsAvg ();
   }
}
