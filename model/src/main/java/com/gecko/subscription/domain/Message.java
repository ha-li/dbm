package com.gecko.subscription.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

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

   @Id
   @GeneratedValue (generator = "UUID")
   @GenericGenerator (
           name="UUID",
           strategy = "org.hibernate.id.UUIDGenerator"
   )
   private String id;

   @Column (name="TEXT")
   private String text;


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

}
