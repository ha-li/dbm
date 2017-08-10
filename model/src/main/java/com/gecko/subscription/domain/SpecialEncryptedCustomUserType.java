package com.gecko.subscription.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialEncryptedCustomUserType
        implements UserType {

   private static final Pattern ENC_PATTERN = Pattern.compile ("^ENC\\$\\((.+)\\)$", Pattern.CASE_INSENSITIVE);

   @Override
   public int[] sqlTypes () {
      return new int[]{Types.VARCHAR};
   }

   @Override
   public Class returnedClass() {
      return String.class;
   }

   @Override
   public boolean equals (Object x, Object y) {
      return x==y || !(x==null || y==null) && x.equals(y);
   }

   @Override
   public int hashCode (Object x) {
      return x.hashCode ();
   }

   @Override
   public Object nullSafeGet (ResultSet rs, String[] names,
         SharedSessionContractImplementor sesion, Object x) throws SQLException {
      if (rs.wasNull()) return null;
      String encValue = rs.getString(names[0]);
      Matcher matcher = ENC_PATTERN.matcher(encValue);
      if (matcher.matches ()) {
         // decrypt here, this is basic decrypt by matcher.group, replace with
         // true encryption method
         String decrypted = matcher.group(1);
         return decrypted;
      }
      return encValue;
   }

   @Override
   public void nullSafeSet (PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
      if (value == null) {
         st.setNull (index, StandardBasicTypes.STRING.sqlType());
      } else {
         StringBuilder sb = new StringBuilder ("ENC$(" + value +")");
         st.setString (index, sb.toString());
      }
   }

   @Override
   public Object deepCopy (Object value) {
      return value;
   }

   @Override
   public boolean isMutable () {
      return false;
   }

   @Override
   public Serializable disassemble (Object value) throws HibernateException {
      return value.toString ();
   }

   @Override
   public Object assemble (Serializable cached, Object owner) throws HibernateException {
      return cached;
   }

   @Override
   public Object replace (Object original, Object target, Object owner) throws HibernateException {
      return original;
   }
}
