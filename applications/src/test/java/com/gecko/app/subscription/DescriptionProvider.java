package com.gecko.app.subscription;

import com.gecko.subscription.domain.Description;

public class DescriptionProvider {
   public static Description getDescription () {
      Description description = new Description ();
      description.setDescription ("A colorful description of an object.");
      return description;
   }
}
