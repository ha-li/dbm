package com.gecko.subscription.domain;

import com.gecko.util.ZipcodeConverter;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hlieu on 07/30/17.
 */
@Entity (name="SUB_ITEM")
@org.hibernate.annotations.Proxy (lazy=true)
public class Item extends Identity
        implements Serializable {

   private static final long serialVersionUID = -8252943712271485652L;

   @Column(name="NAME")
   private String name;

   @Column(name="AUCTION_END")
   private LocalDateTime auctionEnd;

   @org.hibernate.annotations.Type (
      type="com.gecko.subscription.domain.SpecialEncryptedCustomUserType"
   )
   @Column(name="ENC_VALUE")
   private String encryptedValue;

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

   // By default children objects of a join are eager loaded.
   // lazy loading is a design pattern where you defer initializing the
   // child object until you actually access the child, or do some action
   // that requires knowing the children, such as iterating over them,
   // or querying the size of them.
   // Lazy loading is a performance optimization. You delay the most expensive
   // operation until as needed basis.
   @JoinColumn (name="MESSAGE_FK")
   @ManyToOne // (fetch= FetchType.LAZY)
   private Message message;

   // In a OneToMany mapping, you need the 'mappedBy' which points
   // to the field name on the other side of the relationship
   @OneToMany (mappedBy="item", fetch= FetchType.LAZY)
   protected Set<Bid> bids = new HashSet<> ();

   @OneToOne (fetch = FetchType.LAZY, optional = false)
   @JoinColumn (name="DESCRIPTION_FK")
   private Description description;


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

   public String getEncryptedValue () {
      return encryptedValue;
   }

   public void setEncryptedValue (String encryptedValue) {
      this.encryptedValue = encryptedValue;
   }

   public Message getMessage () {
      return message;
   }

   public void setMessage (Message message) {
      this.message = message;
   }


   public Set<Bid> getBids () {
      return bids;
   }

   public void setBids (Set<Bid> bids) {
      this.bids = bids;
   }

   public void add (Bid bid) {
      if (null == bid) {
         throw new NullPointerException ("Bid cannot be null");
      }
      if (bid.getItem () != null) {
         throw new IllegalArgumentException ("Bid is already assigned to an item.");
      }

      getBids ().add(bid);
      bid.setItem(this);
   }

   public Description getDescription () {
      return description;
   }

   public void setDescription (Description description) {
      this.description = description;
   }
}
