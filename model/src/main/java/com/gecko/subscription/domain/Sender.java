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
}
