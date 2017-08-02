package com.gecko.util;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by hlieu on 08/1/17.
 *
 * In hibernate 5.0.0 you will need this
 * converter since java time was not yet completed
 *
 */
//@Converter (autoApply=true)
public class LocalDateTimeAttributeConverter
        implements AttributeConverter<LocalDateTime, Timestamp> {

   @Override
   public Timestamp convertToDatabaseColumn (LocalDateTime localDateTime) {
      return (localDateTime == null ? null : Timestamp.valueOf(localDateTime));
   }

   @Override
   public LocalDateTime convertToEntityAttribute (Timestamp timestamp) {
      return (timestamp == null ? null : timestamp.toLocalDateTime ());
   }
}
