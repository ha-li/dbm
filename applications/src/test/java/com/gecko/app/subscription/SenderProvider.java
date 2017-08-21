package com.gecko.app.subscription;

import com.gecko.subscription.domain.Sender;

public class SenderProvider {

   public static Sender getSender () {
      Sender sender = new Sender ();
      sender.setFirstName ("Devon");
      sender.setLastName ("James");
      return sender;
   }
}
