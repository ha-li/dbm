package com.gecko.subscription.domain;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class Message implements Serializable {

   private static final long serialVersionUID = 8404396176131219022L;

   @Id @Basic (optional=false)
   @GeneratedValue (generator = "UUID")
   @GenericGenerator (
           name="UUID",
           strategy = "org.hibernate.id.UUIDGenerator"
   )
   private String id;

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

   //@Transient
   private transient String transientText;

   public String getId () {
      return id;
   }

   public void setId (String id) {
      this.id = id;
   }

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
}
