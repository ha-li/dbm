package com.gecko.subscription.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by hlieu on 07/8/17.
 */
@Entity (name="SUB_MESSAGES")
public class Message {
   @Id
   @GeneratedValue
   private Long id;

   private String text;


   public Long getId () {
      return id;
   }

   public void setId (Long id) {
      this.id = id;
   }

   public String getText () {
      return text;
   }

   public void setText (String text) {
      this.text = text;
   }



}
