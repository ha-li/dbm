package com.gecko.app.subscription;

import com.gecko.subscription.domain.Item;
import com.gecko.subscription.domain.MonetaryAmount;
import com.gecko.subscription.domain.Zipcode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

public class ItemProvider {
   public static Item getItem () {
      Item item = new Item ();
      item.setDescription (DescriptionProvider.getDescription ());
      item.setMessage (MessageProvider.getMessage ());

      item.setName ("Underwater playground");
      item.setAuctionEnd (LocalDateTime.now());
      item.setSignature ("Guy Lafleur");

      Random random = new Random();
      MonetaryAmount m = new MonetaryAmount ("USD", new BigDecimal (random.nextDouble ()));
      item.setBidAmount (m);
      item.setEncryptedValue ("DoRaMe2");

      item.setZipcode (Zipcode.valueOf (String.valueOf(random.nextInt(100000))));
      // item.setMessage(message);
      return item;
   }
}
