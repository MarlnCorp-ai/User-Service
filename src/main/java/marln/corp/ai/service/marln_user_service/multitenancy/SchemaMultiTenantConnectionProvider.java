package marln.corp.ai.service.marln_user_service.multitenancy;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

	@Autowired
	private DataSource dataSource;

	@Override
	public Connection getAnyConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection (Object tenantIdentifier) throws SQLException {
		Connection connection = getAnyConnection();
		try {
			// Set the schema for PostgreSQL
			connection.createStatement().execute("SET search_path TO " + tenantIdentifier);
		} catch (SQLException e) {
			throw new HibernateException("Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
		}
		return connection;
	}

	@Override
	public void releaseConnection (Object o, Connection connection) throws SQLException {
		try {
			// Reset to default schema
			connection.createStatement().execute("SET search_path TO public");
		} catch (SQLException e) {
			// Log the error but don't throw exception
		}
		releaseAnyConnection(connection);
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}
}