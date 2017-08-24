package com.gecko.subscription.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hlieu on 07/8/17.
 */
@javax.persistence.NamedQueries ({
        @javax.persistence.NamedQuery (
                name="findMessageById",
                query = "select m from SUB_MESSAGES m"
        )
})


// these two annotations tell hibernate to not create the
// CRUD insert/update until runtime, otherwise hibernate
// will cache them on start up an update all columns
// by disabling them, at runtime, only updated columns will
// be updated
/*   @org.hibernate.annotations.DynamicInsert
     @org.hibernate.annotations.DynamicUpdate
*/
@Entity (name="SUB_MESSAGES")
/*
   this requires something special
@org.hibernate.annotations.Cache (
   usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE
) */
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.Proxy(lazy=true)
public class Message extends Identity implements Serializable {

   private static final long serialVersionUID = 8404396176131219022L;

   // there are 3 ways to specify a column is not-nullable
   // 1. @Column (..., nullable=false)
   // 2. @Basic (optional=false)
   // 3. @NotNull annotation in in validation (wonky - requires calling validation api)
   @Column (name="TEXT")
   private String text;


   @Temporal (TemporalType.TIMESTAMP)
   @Column(name="MODIFIED_DATE")
   @org.hibernate.annotations.UpdateTimestamp
   private Date modifiedDate;

   @Temporal (TemporalType.TIMESTAMP)
   @Column(name="CREATED_DATE")
   @org.hibernate.annotations.CreationTimestamp
   private Date createdDate;

   @Enumerated(EnumType.STRING)
   @Column(name="TYPE")
   private MessageType type;

   private Sender sender;

   @Version
   private short version;

   //@Transient
   private transient String transientText;

   public String getText () {
      return text;
   }

   public void setText (String text) {
      this.text = text;
   }

   public String getTransientText () {
      return transientText;
   }

   public void setTransientText (String transientText) {
      this.transientText = transientText;
   }

   public Date getModifiedDate () {
      return modifiedDate;
   }

   public void setModifiedDate (Date modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public Date getCreatedDate () {
      return createdDate;
   }

   public void setCreatedDate (Date createdDate) {
      this.createdDate = createdDate;
   }

   public MessageType getType () {
      return type;
   }

   public void setType (MessageType type) {
      this.type = type;
   }

   public Sender getSender () {
      return sender;
   }

   public void setSender (Sender sender) {
      this.sender = sender;
   }

   public short getVersion () {
      return version;
   }

   @Override
   public boolean equals (Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;

      if (obj instanceof Message) {
         Message otherMessage = (Message)obj;
         return this.getId().equals (otherMessage.getId()) &&
                 getText().equals (otherMessage.getText()) &&
                 getSender().equals (otherMessage.getSender ()) &&
                 getType ().equals (otherMessage.getType ());
      }
      return false;
   }

   @Override
   public int hashCode () {
      return getText().hashCode() + getSender().hashCode () + getType().hashCode() + getId().hashCode ();
   }
}
