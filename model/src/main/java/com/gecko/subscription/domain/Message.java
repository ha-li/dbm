package com.gecko.subscription.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by hlieu on 07/8/17.
 */
@javax.persistence.NamedQueries ({
     @javax.persistence.NamedQuery (
             name="findMessageById",
             query = "select m from SUB_MESSAGES m"
     )
})


@Entity (name="SUB_MESSAGES")
/*
   this requires something special
@org.hibernate.annotations.Cache (
   usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE
) */
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
