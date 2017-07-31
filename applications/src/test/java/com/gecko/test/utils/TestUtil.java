package com.gecko.test.utils;

import com.gecko.core.application.Application;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * Created by hlieu on 07/19/17.
 */
public class TestUtil {

   private static String fileName = "truncate.sql";

   public static void truncateTables () {
      URL url = ClassLoader.getSystemResource (fileName);

      try {
         if (url == null) {
            throw new FileNotFoundException ("File not found " + fileName);
         }
         UserTransaction tx = Application.getUserTransaction ();
         tx.begin ();

         EntityManager em = Application.createEntityManager ();
         BufferedReader bf = new BufferedReader (new FileReader (url.getFile ()));

         String line = null;
         while ( (line = bf.readLine()) != null ) {
            //int i =  (line).executeUpdate ();
         }

         tx.commit ();
         em.close ();
      } catch (Exception e) {
         throw new RuntimeException (e);
      }
   }


}
