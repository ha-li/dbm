package com.gecko.subscription.domain;

/**
 * Created by hlieu on 08/6/17.
 */
public class GermanZipcode extends Zipcode {

   public GermanZipcode (String value) {
      super(value);
   }

   @Override
   public boolean equals (Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;

      if (obj instanceof GermanZipcode) {
         GermanZipcode otherZip = (GermanZipcode) obj;
         return getValue().equals (otherZip.getValue());
      }
      return false;
   }

   @Override
   public int hashCode () {
      return getValue().hashCode ();
   }
}
