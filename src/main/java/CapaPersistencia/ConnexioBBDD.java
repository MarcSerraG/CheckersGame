package CapaPersistencia;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public final class ConnexioBBDD {
 
	private static DataSource ds ;
 
	static {
	 PoolProperties p = new PoolProperties();
	 p.setUrl("jdbc:oracle:thin:@kali.tecnocampus.cat:1521:sapiens" );
	 p.setDriverClassName("oracle.jdbc.driver.OracleDriver");
	 p.setUsername("g3geilab1");
	 p.setPassword("g3geilab1");
	 p.setJmxEnabled(true);
	 p.setTestWhileIdle(false);
	 p.setTestOnBorrow(true);
	 p.setValidationQuery("SELECT 1 FROM DUAL");//"SELECT 1" per mysql
	 p.setTestOnReturn(false);
	 p.setValidationInterval(30000);
	 p.setTimeBetweenEvictionRunsMillis(30000);
	 p.setMaxActive(100);
	 p.setInitialSize(10);
	 p.setMaxWait(10000);
	 p.setRemoveAbandonedTimeout(60);
	 p.setMinEvictableIdleTimeMillis(30000);
	 p.setMinIdle(10);
	 p.setLogAbandoned(true);
	 p.setRemoveAbandoned(true);
	 p.setJdbcInterceptors(
	 "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
	 ds = new DataSource();
	 ds.setPoolProperties(p);
	 }
	
	 public static Connection connexio() throws SQLException {
	 return ds.getConnection();
	 }
}