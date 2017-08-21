package com.gecko.subscription.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity (name="SUB_DESCRIPTION")
public class Description extends Identity {

   @Temporal (TemporalType.TIMESTAMP)
   @Column(name="CREATED_DATE")
   @org.hibernate.annotations.CreationTimestamp
   private Date createdDate;

   @Temporal (TemporalType.TIMESTAMP)
   @Column(name="MODIFIED_DATE")
   @org.hibernate.annotations.UpdateTimestamp
   private Date modifiedDate;

   @Column (name="DESCRIPTION")
   private String description;

   public Date getCreatedDate () {
      return createdDate;
   }

   public void setCreatedDate (Date createdDate) {
      this.createdDate = createdDate;
   }

   public Date getModifiedDate () {
      return modifiedDate;
   }

   public void setModifiedDate (Date modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public String getDescription () {
      return description;
   }

   public void setDescription (String description) {
      this.description = description;
   }

   @Override
   public boolean equals (Object obj) {
      if (obj == this) return true;
      if (obj == null) return false;

      if (obj instanceof Description) {
         Description otherD = (Description) obj;
         return otherD.getDescription ().equals (this.getDescription ());
      }
      return false;
   }

   @Override
   public int hashCode () {
      return getDescription ().hashCode ();
   }
}
