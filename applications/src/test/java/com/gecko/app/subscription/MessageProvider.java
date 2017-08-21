package com.gecko.app.subscription;

import com.gecko.subscription.domain.Message;
import com.gecko.subscription.domain.MessageType;

public class MessageProvider {
   public static Message getMessage () {
      Message message = new Message ();
      message.setSender(SenderProvider.getSender ());
      message.setText ("A message in a bottle, as JMS");
      message.setType (MessageType.EMAIL);
      message.setTransientText ("A temporary string");
      return message;
   }
}
