package com.gecko.subscription.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hlieu on 07/30/17.
 */
public class Item implements Serializable {

   private static final long serialVersionUID = -8252943712271485652L;

   private String id;
   private String name;
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
