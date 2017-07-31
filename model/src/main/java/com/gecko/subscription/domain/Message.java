package com.gecko.subscription.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

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
   @GeneratedValue(generator = "UUID")
   @GenericGenerator (
      name="UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
   )
   private String id;

   @Column(name="TEXT")
   private String text;


   public String getId () {
      return id;
   }

   public void setId (String id) {
      this.id = id;
   }

   public String getText () {
      return text;
   }

   public void setText (String text) {
      this.text = text;
   }
}
