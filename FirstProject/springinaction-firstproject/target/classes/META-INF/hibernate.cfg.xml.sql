<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
 <session-factory>
  
    <property name="connection.url">jdbc:mysql://localhost:3306/mysqldb</property>
    <property name="connection.username">user</property>
    <property name="connection.password">password</property>
    <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
  
    <property name="show_sql">true</property>
  
    <property name="format_sql">true</property>
    <property name="hbm2ddl.auto">create</property>
  
    <!-- JDBC connection pool (use the built-in) -->
    <property name="connection.pool_size">1</property>
    <property name="current_session_context_class">thread</property>
 
     <mapping class="com.mastertheboss.domain.Employee" />
     <mapping class="com.mastertheboss.domain.Department" />
 
 <!-- <mapping resource="com/mkyong/user/DBUser.hbm.xml"></mapping> -->
</session-factory>
</hibernate-configuration>