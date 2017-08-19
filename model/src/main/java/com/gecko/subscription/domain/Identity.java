package com.gecko.subscription.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Identity {

   @Id
   @Column (name="ID")
   @GeneratedValue (generator = "UUID")
   @GenericGenerator (
           name="UUID",
           strategy = "org.hibernate.id.UUIDGenerator"
   )
   private String id;

   public String getId () {
      return id;
   }

   public void setId (String id) {
      this.id = id;
   }
}
