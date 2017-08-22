package com.gecko.subscription.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity (name="SUB_BID")
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
public class Bid extends Identity implements Serializable {

   private static final long serialVersionUID = -6002635518885479349L;

   @Column (name="TEXT")
   private String text;

   @Temporal (TemporalType.TIMESTAMP)
   @Column(name="MODIFIED_DATE")
   @org.hibernate.annotations.UpdateTimestamp
   private Date modifiedDate;

   @Temporal (TemporalType.TIMESTAMP)
   @Column(name="CREATED_DATE")
   @org.hibernate.annotations.CreationTimestamp
   private Date createdDate;

   @Column (name="AMOUNT")
   private BigDecimal amount;


   @ManyToOne(fetch= FetchType.LAZY)
   @JoinColumn (name="ITEM_FK")
   private Item item;

   public String getText () {
      return text;
   }

   public void setText (String text) {
      this.text = text;
   }

   public Date getModifiedDate () {
      return modifiedDate;
   }

   public void setModifiedDate (Date modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public Date getCreatedDate () {
      return createdDate;
   }

   public void setCreatedDate (Date createdDate) {
      this.createdDate = createdDate;
   }

   public BigDecimal getAmount () {
      return amount;
   }

   public void setAmount (BigDecimal amount) {
      this.amount = amount;
   }

   public Item getItem () {
      return item;
   }

   public void setItem (Item item) {
      this.item = item;
   }

   @Override
   public int hashCode () {
      return getText().hashCode () + getAmount().hashCode () + getItem().hashCode ();
   }

   @Override
   public boolean equals (Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;

      if (obj instanceof Bid) {
         Bid obid = (Bid) obj;
         return getText().equals(obid.getText ()) &&
            getAmount ().equals (obid.getAmount()) &&
            getItem ().equals (obid.getItem());
      }
      return false;
   }
}
