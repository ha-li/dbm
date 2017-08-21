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

   @Override
   public int hashCode () {
      return getId().hashCode ();
   }

   @Override
   public boolean equals (Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;

      if (obj instanceof Identity) {
         Identity otherId = (Identity) obj;
         return this.getId ().equals (otherId.getId());
      }
      return false;
   }

}
