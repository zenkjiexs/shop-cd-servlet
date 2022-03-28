package com.dots.shoptest.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

  private static ConnectionPool pool = null;
  private DataSource dataSource;

  private ConnectionPool() {
    try {
      InitialContext context = new InitialContext();
       dataSource = (DataSource) context.lookup("java:comp/env/jdbc/shoptest");
      if(dataSource != null) {
        System.out.println("ConnectionPool: DataSource found");
      } else {
        throw new RuntimeException("DataSource not found");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static synchronized ConnectionPool getInstance() {
    if (pool == null) {
      pool = new ConnectionPool();
    }
    return pool;
  }

  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      System.out.println(e);
      return null;
    }
  }

  public void freeConnection(Connection c) {
    try {
      c.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }
}
