package com.gecko.subscription.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

@Entity (name="SUB_DESCRIPTION")
public class Description {

   @Id
   @Column (name="ID")
   @GeneratedValue (generator = "UUID")
   @GenericGenerator (
           name="UUID",
           strategy = "org.hibernate.id.UUIDGenerator"
   )
   private String id;

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


   public String getId () {
      return id;
   }

   public void setId (String id) {
      this.id = id;
   }

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
}
