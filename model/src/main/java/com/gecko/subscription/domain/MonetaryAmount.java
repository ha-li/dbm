package com.gecko.subscription.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * Created by hlieu on 08/6/17.
 */
public class MonetaryAmount {

   @Column (name="AMOUNT")
   private BigDecimal amount;

   @Column (name="CURRENCY")
   private String currency;

   public MonetaryAmount () {}

   public MonetaryAmount (String currency, double amount) {
      this.currency = currency;
      this.amount = BigDecimal.valueOf(amount);
   }

   public BigDecimal getAmount () {
      return amount;
   }

   public void setAmount (BigDecimal amount) {
      this.amount = amount;
   }

   public String getCurrency () {
      return currency;
   }

   public void setCurrency (String currency) {
      this.currency = currency;
   }

   public boolean equals (Object o) {
      if (o == this) return true;

      if( !(o instanceof MonetaryAmount) ) return false;

      final MonetaryAmount monetaryAmount = (MonetaryAmount) o;
      if ( this.amount  != monetaryAmount.amount ) return false;
      if ( ! this.currency.equals(monetaryAmount.currency) ) return false;

      return true;
   }

   public String toString () {
      return currency + " " + amount;
   }
}
