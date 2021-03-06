package com.gecko.subscription.domain;

import com.gecko.util.ZipcodeConverter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by hlieu on 08/6/17.
 */
public abstract class Zipcode {
   private static ZipcodeConverter converter = new ZipcodeConverter ();

   protected String value;

   public Zipcode (String value) {
      this.value = value;
   }

   public String getValue () {
      return value;
   }

   public void setValue (String value) {
      this.value = value;
   }

   @Override
   public boolean equals (Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (! (o instanceof Zipcode) ) return false;

      Zipcode otherZip = (Zipcode) o;
      return getValue().equals(otherZip.getValue());
   }

   @Override
   public String toString () {
      return getValue();
   }

   public static Zipcode valueOf (String v) {
      return converter.convertToEntityAttribute (v);
   }

   @Override
   public int hashCode () {
      return getValue().hashCode();
   }
}
