package com.gecko.app.subscription;

import com.gecko.core.application.Application;
import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.TestRepositoryUtil;
import com.gecko.subscription.domain.Item;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class DetachablePersistenceTest {

   @After
   public void cleanUp () {
      TestRepositoryUtil.truncateTables ();
   }

   @Test
   public void test_detached () {
      Item item = ItemProvider.getItem ();

      EntityManager em = Application.createEntityManager ();
      UnitOfWork uow = UnitOfWork.beginUnitOfWork ();
      em.persist (item.getDescription ());
      em.persist (item.getMessage ());
      em.persist (item);
      UnitOfWork.commitUnitOfWork ();

      uow = UnitOfWork.beginUnitOfWork ();
      Item attached = em.find(Item.class, item.getId());

      Assert.assertEquals (attached.getName(),item.getName());
      Assert.assertNotNull (attached.getId());

      attached.setName ("This is not in the database");

      // I don't need to save, just setting the field and then
      // committing the transaction will cause it to update
      UnitOfWork.commitUnitOfWork ();

      uow = UnitOfWork.beginUnitOfWork ();
      Item isCommited = em.find (Item.class, item.getId ());
      Assert.assertEquals (isCommited.getName(), "This is not in the database");
      UnitOfWork.commitUnitOfWork ();
      em.close ();
   }

}
