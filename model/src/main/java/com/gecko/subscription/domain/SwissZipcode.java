package com.gecko.subscription.domain;

/**
 * Created by hlieu on 08/6/17.
 */
public class SwissZipcode extends Zipcode {

   public SwissZipcode (String value) {
      super (value);
   }

   @Override
   public boolean equals (Object o) {
      if (this == o) return true;
      if (o == null) return false;

      if (o instanceof SwissZipcode) {
         SwissZipcode otherZipCode = (SwissZipcode) o;
         return otherZipCode.getValue () .equals (this.getValue());
      }
      return false;
   }

   @Override
   public int hashCode () {
      return getValue().hashCode();
   }
}
