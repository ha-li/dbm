package com.gecko.app.subscription;

import com.gecko.core.application.UnitOfWork;
import com.gecko.core.repository.MessageRepository;
import com.gecko.core.repository.JpaRepository;
import com.gecko.core.repository.TestRepositoryUtil;
import com.gecko.subscription.domain.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by hlieu on 07/11/17.
 */
public class SubscriptionAppTest {

   @After
   public void cleanUp () {
      TestRepositoryUtil.truncateTables ();
   }

   @Test
   public void testUpdate () throws Exception {

      SubscriptionApp app = new SubscriptionApp ();
      Message message = new Message ();
      message.setText ("Hello World!");

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.save (message);
         UnitOfWork.commitUnitOfWork ();
      }

      List<Message> list;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         list = MessageRepository.getMessages ();
         UnitOfWork.commitUnitOfWork ();
      }
      Assert.assertEquals (list.get(0).getText(), "Hello World!");

      message.setText("Bottoms up!");

      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         JpaRepository.update (message);
         UnitOfWork.commitUnitOfWork ();
      }

      List<Message> list2;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         list2 = MessageRepository.getMessages ();
         UnitOfWork.commitUnitOfWork ();
      }

      Assert.assertEquals (list2.get(0).getText(), "Bottoms up!");

      List<Message> listAll;
      try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         listAll = MessageRepository.getAllMessages ();
         UnitOfWork.commitUnitOfWork ();
      }

      Assert.assertEquals (listAll.get(0).getText(), "Bottoms up!");
   }

}