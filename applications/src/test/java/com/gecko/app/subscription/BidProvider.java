package com.gecko.app.subscription;

import com.gecko.subscription.domain.Bid;

import java.math.BigDecimal;

public class BidProvider {
   public static Bid getBid () {
      Bid bid = new Bid ();
      bid.setAmount(new BigDecimal (4.00));

      bid.setText("Random text");
      return bid;
   }
}
