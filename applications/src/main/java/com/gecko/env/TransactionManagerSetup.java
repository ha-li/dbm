package com.gecko.env;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 * Created by hlieu on 07/8/17.
 */
public class TransactionManagerSetup {

   public static final String DATASOURCE_NAME = "subadm";

   protected final Context context = new InitialContext ();
   protected final PoolingDataSource datasource;
   public final Database database;

   public TransactionManagerSetup (Database database) throws Exception {
      this(database, null);
   }

   public TransactionManagerSetup (Database database,
                                   String connectionUrl) throws Exception {

      TransactionManagerServices.getConfiguration().setServerId ("myServer1234");
      TransactionManagerServices.getConfiguration().setDisableJmx (true);
      TransactionManagerServices.getConfiguration().setJournal ("null");
      TransactionManagerServices.getConfiguration().setWarnAboutZeroResourceTransaction(false);

      datasource = new PoolingDataSource();
      this.database = database;
      database.configuration.configure(datasource, connectionUrl);
   }

   public Context getNamingContext () { return context; }

   public UserTransaction getUserTransaction () {
      try {
         return (UserTransaction) getNamingContext ().lookup("java:comp/UserTransaction");
      } catch (Exception ex) {
         throw new RuntimeException (ex);
      }
   }

   public DataSource getDataSource () {
      try {
         return (DataSource) getNamingContext().lookup (DATASOURCE_NAME);
      } catch (Exception ex) {
         throw new RuntimeException (ex);
      }
   }

   public void rollback () {
      UserTransaction tx = getUserTransaction ();
      try {
         if (tx.getStatus () == Status.STATUS_ACTIVE ||
             tx.getStatus () == Status.STATUS_MARKED_ROLLBACK) {
            tx.rollback ();
         }
      } catch (Exception ex) {
         System.err.println ("Rollback of transaction failed. trace follows!");
         ex.printStackTrace (System.err);
      }
   }

   public void stop () throws Exception {
      datasource.close ();
      TransactionManagerServices.getTransactionManager().shutdown ();
   }
}
