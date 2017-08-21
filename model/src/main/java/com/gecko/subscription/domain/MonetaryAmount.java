package com.gecko.subscription.domain;

import java.math.BigDecimal;

/**
 * Created by hlieu on 08/6/17.
 */
public class MonetaryAmount {

   private BigDecimal amount;

   private String currency;

   public MonetaryAmount () {}

   public MonetaryAmount (String currency, BigDecimal amount) {
      this.currency = currency;
      this.amount = amount;
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

   @Override
   public boolean equals (Object o) {
      if (o == this) return true;
      if (o == null) return false;

      if( !(o instanceof MonetaryAmount) ) return false;

      final MonetaryAmount otherAmount = (MonetaryAmount) o;

      return this.getAmount ().equals (otherAmount.getAmount ())
              && this.getCurrency ().equals (otherAmount.getCurrency ());
   }

   @Override
   public int hashCode () {
      return amount.hashCode () + currency.hashCode ();
   }

   public String toString () {
      return currency + " " + amount;
   }

   public static MonetaryAmount fromString (String s) {
      String[] vals = s.split (" ");
      BigDecimal d = new BigDecimal(vals[1]);
      return new MonetaryAmount (vals[0], d);
   }
}
