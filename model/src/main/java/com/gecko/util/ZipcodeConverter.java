package com.gecko.util;

import com.gecko.subscription.domain.GermanZipcode;
import com.gecko.subscription.domain.SwissZipcode;
import com.gecko.subscription.domain.Zipcode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by hlieu on 08/6/17.
 */
@Converter(autoApply = true)
public class ZipcodeConverter implements AttributeConverter<Zipcode, String> {

   @Override
   public String convertToDatabaseColumn (Zipcode attribute) {
      return attribute.getValue();
   }

   @Override
   public Zipcode convertToEntityAttribute (String value) {
      if (value.length () == 5) return new GermanZipcode (value);
      else if (value.length () == 4) return new SwissZipcode (value);
      throw new IllegalArgumentException ("Unsupported zipcode type in database " + value);
   }
}