package marln.corp.ai.service.marln_user_service.multitenancy;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Component
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.flyway.schemas}")
	private String schemaName;

	private final DataSource dataSource;

	public static final String DEFAULT_SCHEMA = "default";

	@Autowired
	public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(Object tenantIdentifier) throws SQLException {
		Connection connection = getAnyConnection();
		String schemaName = Objects.requireNonNullElse(tenantIdentifier, DEFAULT_SCHEMA) + "_" + (!StringUtils.isEmpty(this.schemaName) ? this.schemaName : applicationName);
		schemaName = schemaName.toLowerCase();
		// Set the schema based on the tenantIdentifier
		connection.createStatement().execute("SET SCHEMA '" + schemaName + "'");
		return connection;
	}

	@Override
	public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
		connection.createStatement().execute("SET SCHEMA 'public'"); // Reset schema
		releaseAnyConnection(connection);
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false; // Or implement unwrapping if needed
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		throw new UnsupportedOperationException("Not implemented");
	}
}