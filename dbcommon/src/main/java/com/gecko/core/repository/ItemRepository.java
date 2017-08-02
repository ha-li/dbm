package com.gecko.core.repository;

import com.gecko.core.application.Application;
import com.gecko.subscription.domain.Item;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 08/1/17.
 */
public class ItemRepository extends Repository {


   public static Item getById (String id) throws Exception {
      UserTransaction tx = Application.getUserTransaction ();
      tx.begin();
      EntityManager em = Application.createEntityManager ();
      Item item = em.find(Item.class, id);
      tx.commit ();
      em.close();
      return item;
   }
}
