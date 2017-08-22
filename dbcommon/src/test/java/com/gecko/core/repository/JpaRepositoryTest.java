package com.gecko.core.repository;

import com.gecko.core.application.UnitOfWork;
import com.gecko.subscription.domain.Message;
import com.gecko.subscription.domain.MessageType;
import org.junit.Assert;
import org.junit.Test;

import javax.transaction.Transactional;
import java.util.List;

public class JpaRepositoryTest {

   @Test
   public void test_list () {

      int count = 2;

      for (int i = 0; i < count; i++) {
         try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
            Message message = new Message ();
            message.setText ("Job " + i);
            message.setType (i % 2 == 0 ? MessageType.EMAIL : MessageType.JMS);

            JpaRepository.save (message);
            UnitOfWork.commitUnitOfWork ();
         }
      }

      List<Message> mAll = null;
      //try (UnitOfWork uow = UnitOfWork.beginUnitOfWork ()) {
         mAll = JpaRepository.findAll (Message.class);
         UnitOfWork.commitUnitOfWork ();
      //}

      Assert.assertEquals (mAll.size(), count);
   }
}
