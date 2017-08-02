package com.gecko.app.subscription;

import com.gecko.core.repository.MessageRepository;
import com.gecko.subscription.domain.Message;

import java.util.List;

/**
 * Created by hlieu on 07/8/17.
 */
public class SubscriptionApp {

   public static void main (String[] args) throws Exception {
      SubscriptionApp app = new SubscriptionApp ();

      Message message = new Message ();
      message.setText ("Hello World!");
      message.setTransientText ("Do not Save me");

      MessageRepository.save(message);

      List<Message> list = MessageRepository.getMessages();

      list.get(0).setText("I'm here to rule the world!");
      MessageRepository.updateMessage (list.get(0));
   }
}
