package com.gecko.app.subscription;

import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.JpaRepository;
import com.gecko.subscription.domain.Item;

public class Sample {
   public static void changeState (String id) throws Exception {

      try ( UnitOfWork unit = UnitOfWork.beginUnitOfWork () ) {
         Item item = JpaRepository.getById (Item.class, id);
         item.setName ("DopeAss");

         UnitOfWork.commitUnitOfWork ();
      }
   }



   public static void main (String[] args) throws Exception {
      changeState ("15d5c00e-ffae-4267-a1d3-0f127de95ced");
   }
}
