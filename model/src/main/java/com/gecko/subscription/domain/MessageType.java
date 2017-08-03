package com.gecko.subscription.domain;

/**
 * Created by hlieu on 08/2/17.
 */
public enum MessageType {
   JMS,
   EMAIL,
   TEXT;

   public MessageType Type (String value) {
      return valueOf (value);
   }

   public String Name () {
      return name ();
   }
}
