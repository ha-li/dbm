package com.gecko.core.application;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import java.util.Properties;

/**
 * Created by hlieu on 07/8/17.
 */
public enum Database {


   ORACLE (
           new DataSourceConfiguration () {
              @Override
              public void configure (PoolingDataSource ds, String connectionUrl) {
                 ds.setClassName ( oracle.jdbc.xa.client.OracleXADataSource.class.getName() );
                 ds.getDriverProperties().put (
                         "URL",
                         connectionUrl != null
                            ? connectionUrl :
                            "jdbc:oracle:thin:subadm/subadm@localhost:1521:xe"
                 );

                 Properties connectionProperties = new Properties();
                 connectionProperties.put("useFetchSizeWithLongColumn", "true");
                 ds.getDriverProperties().put("connectionProperties", connectionProperties);
                 ds.setIsolationLevel("READ_COMMITTED");
                 ds.setAllowLocalTransactions (Boolean.TRUE);
                 ds.setUniqueName(DATASOURCE_NAME);
                 ds.setMinPoolSize(1);
                 ds.setMaxPoolSize(5);
                 ds.setPreparedStatementCacheSize(10);
                 ds.init ();
              }
           },
           org.hibernate.dialect.Oracle10gDialect.class.getName()
   );

   public static final String DATASOURCE_NAME = "subadm";

   public DataSourceConfiguration configuration;
   public String hibernateDialect;

   private Database (DataSourceConfiguration configuration,
                     String hibernateDialect)
   {
      this.configuration = configuration;
      this.hibernateDialect = hibernateDialect;
   }

   public interface DataSourceConfiguration {
      void configure (PoolingDataSource ds, String connectionUrl);
   }
}
