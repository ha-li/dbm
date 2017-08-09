package com.gecko.subscription.domain;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by hlieu on 08/8/17.
 */
public class MonetaryAmountCustomUserType
        implements CompositeUserType, DynamicParameterizedType {


   protected String convertTo;

   /*
    * Allows you to access dynamic paramters, such as mapped columns or table
    * or the annotations on the field/getters. However in this example,
    * we only need the 'convertTo' property which specifies which
    * currency to use, defaulting to USD if not set.
    */
   @Override
   public void setParameterValues (Properties parameters) {
      ParameterType parameterType = (ParameterType) parameters.get(PARAMETER_TYPE);
      String[] columns = parameterType.getColumns ();
      String table = parameterType.getTable ();
      Annotation[] annotations = parameterType.getAnnotationsMethod ();

      String convertToParameter = parameters.getProperty("convertTo");
      this.convertTo = (
         convertToParameter != null ? convertToParameter : "USD"
      );
   }

   public Object nullSafeGet (ResultSet resultSet, String[] names,
                              SharedSessionContractImplementor session, Object owner) throws SQLException {
      BigDecimal amount = resultSet.getBigDecimal (names[0]);

      if (resultSet.wasNull ()) return null;

      String currency = resultSet.getString(names[1]);
      return new MonetaryAmount (currency, amount);
   }

   public void nullSafeSet (PreparedStatement statement, Object value,
                            int indx, SharedSessionContractImplementor session) throws SQLException {
      if (value == null) {
         statement.setNull (indx, StandardBasicTypes.BIG_DECIMAL.sqlType());
         statement.setNull (indx+1, StandardBasicTypes.STRING.sqlType());
      } else {
          MonetaryAmount amount = (MonetaryAmount) value;
          MonetaryAmount dbAmount = convert(amount, convertTo);
          statement.setBigDecimal (indx, dbAmount.getAmount());
          statement.setString (indx + 1, convertTo);
      }
   }


   protected MonetaryAmount convert (MonetaryAmount amount, String currency) {
      return new MonetaryAmount (currency,
           amount.getAmount ().multiply(new BigDecimal(2))
      );
   }
   /*
    * these are all scaffolding code.
    */
   public Class returnedClass () {
      return MonetaryAmount.class;
   }

   public boolean isMutable () { return false; }

   public Object deepCopy (Object value) { return value; }

   public Serializable disassemble (Object value, SharedSessionContractImplementor session) {
      return value.toString ();
   }

   public Object assemble (Serializable cached, SharedSessionContractImplementor session, Object owner) {
      return MonetaryAmount.fromString((String) cached);
   }

   public Object replace (Object original, Object target, SharedSessionContractImplementor session, Object owner) {
      return original;
   }

   /*
    * Uses MonetaryAmount.equals to compare for equality
    */
   public boolean equals (Object x, Object y) {
      return x==y || !(x == null || y == null) && x.equals(y);
   }

   public int hashCode (Object x) {
      return x.hashCode ();
   }

   public String[] getPropertyNames () {
      return new String[] {"amount", "currency"};
   }

   public Type[] getPropertyTypes () {
      return new Type[] {
         StandardBasicTypes.BIG_DECIMAL,
         StandardBasicTypes.STRING
      };
   }

   public Object getPropertyValue (Object component, int property) {
      MonetaryAmount monetaryAmount = (MonetaryAmount) component;
      if (property == 0) {
         return monetaryAmount.getAmount();
      } else {
         return monetaryAmount.getCurrency ();
      }
   }

   public void setPropertyValue (Object component, int property, Object value) {
      throw new UnsupportedOperationException ("MonetaryAmount is immutable");
   }
}
