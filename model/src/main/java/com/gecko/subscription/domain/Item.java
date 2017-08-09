package com.gecko.subscription.domain;

import com.gecko.util.ZipcodeConverter;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hlieu on 07/30/17.
 */
@Entity (name="SUB_ITEM")
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

   @Column(name="AUCTION_END")
   private LocalDateTime auctionEnd;


   @org.hibernate.annotations.Type (
       type="com.gecko.subscription.domain.MonetaryAmountCustomUserType"
   )
   @Columns (columns = {
       @Column(name="AMOUNT"),
       @Column(name="CURRENCY")
   })
   private MonetaryAmount bidAmount;

   @Convert (
      converter = ZipcodeConverter.class,
           disableConversion = false
   )
   @Column(name="ZIPCODE")
   private Zipcode zipcode;

   @Transient
   private String signature;

   @org.hibernate.annotations.Formula (
       "(select sum(i.amount) from sub_item i)"
   )
   private BigDecimal auctionTotal;

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

   public MonetaryAmount getBidAmount () {
      return bidAmount;
   }

   public void setBidAmount (MonetaryAmount bidAmount) {
      this.bidAmount = bidAmount;
   }

   public String getSignature () {
      return signature;
   }

   public void setSignature (String signature) {
      this.signature = signature;
   }

   public BigDecimal getAuctionTotal () {
      return auctionTotal;
   }

   public void setAuctionTotal (BigDecimal auctionTotal) {
      this.auctionTotal = auctionTotal;
   }

   public Zipcode getZipcode () {
      return zipcode;
   }

   public void setZipcode (Zipcode zipcode) {
      this.zipcode = zipcode;
   }
}
