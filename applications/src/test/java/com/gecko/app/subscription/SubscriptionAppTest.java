package com.gecko.app.subscription;

import com.gecko.subscription.domain.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by hlieu on 07/11/17.
 */
public class SubscriptionAppTest {

/*
   @Test
   public void testInsert () throws Exception {
      SubscriptionApp app = new SubscriptionApp ();
      Message message = new Message();
      message.setText ("Hello World!");
      app.saveMessage(message);

      List<Message> list = app.getMessages ();
      Assert.assertEquals (list.get(0).getText(), "Hello World!");
      Assert.assertEquals (list.get(0).getId ().longValue (), 1L);
   }
*/

   @After
   public void cleanUp () {
      //TestUtil.truncateTables ();
   }

   @Test
   public void testUpdate () throws Exception {

      SubscriptionApp app = new SubscriptionApp ();
      Message message = new Message();
      message.setText ("Hello World!");
      app.saveMessage(message);

      List<Message> list = app.getMessages ();
      Assert.assertEquals (list.get(0).getText(), "Hello World!");
      //Assert.assertEquals (list.get(0).getId ().longValue (), 1L);

      message.setText("Bottoms up!");
      app.updateMessage (message);
      List<Message> list2 = app.getMessages ();
      Assert.assertEquals (list2.get(0).getText(), "Bottoms up!");
      //Assert.assertEquals (list2.get(0).getId ().longValue (), 1L);
   }
}