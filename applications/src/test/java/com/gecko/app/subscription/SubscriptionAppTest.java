package com.gecko.app.subscription;

import com.gecko.core.application.Application;
import com.gecko.core.repository.MessageRepository;
import com.gecko.subscription.domain.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by hlieu on 07/11/17.
 */
public class SubscriptionAppTest {

   @After
   public void cleanUp () {
      //TestUtil.truncateTables ();
   }

   @Test
   public void testUpdate () throws Exception {

      SubscriptionApp app = new SubscriptionApp ();
      Message message = new Message ();
      message.setText ("Hello World!");

      MessageRepository.saveMessage(message);

      List<Message> list = MessageRepository.getMessages ();
      Assert.assertEquals (list.get(0).getText(), "Hello World!");

      message.setText("Bottoms up!");
      MessageRepository.updateMessage (message);

      List<Message> list2 = MessageRepository.getMessages ();
      Assert.assertEquals (list2.get(0).getText(), "Bottoms up!");

      List<Message> listAll = MessageRepository.getAllMessages ();
      Assert.assertEquals (listAll.get(0).getText(), "Bottoms up!");
   }
}