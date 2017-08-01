package com.gecko.subscription.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hlieu on 07/30/17.
 */
@Table (name="SUB_ITEM")
public class Item implements Serializable {

   private static final long serialVersionUID = -8252943712271485652L;

   @Id
   @Column (name="ID")
   @GeneratedValue (generator = "UUID")
   @GenericGenerator (
           name="UUID",
           strategy = "org.hibernate.id.UUIDGenerator"
   )
   private String id;

   @Column(name="NAME")
   private String name;

   @Column(name="ITEM")
   private LocalDateTime auctionEnd;

   public String getId () {
      return id;
   }

   public void setId (String id) {
      this.id = id;
   }

   public String getName () {
      return name;
   }

   public void setName (String name) {
      this.name = name;
   }

   public LocalDateTime getAuctionEnd () {
      return auctionEnd;
   }

   public void setAuctionEnd (LocalDateTime auctionEnd) {
      this.auctionEnd = auctionEnd;
   }
}
