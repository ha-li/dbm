package com.gecko.core.repository;

import com.gecko.core.application.Application;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRepositoryUtil {

   public static void truncateTables () {
      URL path = ClassLoader.getSystemResource ("truncate.sql");
      try {
         if (null == path) {
            throw new FileNotFoundException ("Failed to find truncate tables");
         }
         BufferedReader bf = new BufferedReader (new FileReader (path.getFile ()));
         String line = null;
         while ( (line = bf.readLine ()) != null ) {
            truncateTable (line);
         }
      } catch (Exception e) {
         throw new RuntimeException (e);
      }
   }

   public static void truncateTable (String ddl) {
      EntityManager em;
      try {
         em = Application.createEntityManager ();
         Session session = (Session) em;
         session.doWork (
              new Work () {
                 public void execute (Connection connection) throws SQLException {
                    Statement statement = connection.createStatement ();
                    statement.executeUpdate(ddl);
                    connection.commit ();
                 }
              }
         );
      } catch (Throwable t) {
         throw new RuntimeException ("Failed truncating table " + ddl, t);
      }
   }
}
