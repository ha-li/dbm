package com.gecko.subscription.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by hlieu on 08/2/17.
 */
@Embeddable
public class Sender {

   @Column (name="SENDER_FIRST")
   private String firstName;

   @Column (name="SENDER_LAST")
   private String lastName;

   public Sender () {}

   public Sender (String first, String last) {
      this.firstName = first;
      this.lastName = last;
   }

   public String getFirstName () {
      return firstName;
   }

   public void setFirstName (String firstName) {
      this.firstName = firstName;
   }

   public String getLastName () {
      return lastName;
   }

   public void setLastName (String lastName) {
      this.lastName = lastName;
   }

   @Override
   public boolean equals (Object other) {
      if (other == this) return true;
      if (other == null) return false;

      if (other instanceof Sender) {
         Sender otherSender = (Sender) other;
         return otherSender.getFirstName ().equals (this.getFirstName ())
                 && otherSender.getLastName ().equals (this.getLastName ());
      }
      return false;
   }

   @Override
   public int hashCode () {
      return getFirstName ().hashCode () + getLastName ().hashCode ();
   }
}
