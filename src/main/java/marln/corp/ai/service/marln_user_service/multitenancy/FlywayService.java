package marln.corp.ai.service.marln_user_service.multitenancy;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

@Service
@Log4j2
public class FlywayService {

	public static final String DEFAULT_SCHEMA = "default";

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.flyway.schemas}")
	private String schemaName;

	@Autowired
	private DataSource dataSource;

	public void migrateTenant (String tenantId) {
		String schemaName = Objects.requireNonNullElse(tenantId, DEFAULT_SCHEMA) + "_" + (!StringUtils.isEmpty(this.schemaName) ? this.schemaName : applicationName);
		schemaName = schemaName.toLowerCase();
		log.info("Schema Name:" + schemaName);
		try (Connection conn = dataSource.getConnection();
		     Statement stmt = conn.createStatement()) {
			log.info("Creating schema - {}", schemaName);
			stmt.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
		} catch (Exception e) {
			log.error("Failed to create schema for tenant - {}", tenantId);
		}
		Flyway flyway = Flyway.configure()
				                .dataSource(dataSource)
				                .schemas(schemaName)
				                .defaultSchema(schemaName)
				                .locations("classpath:db/migration/release-0.0.1")
				                .baselineOnMigrate(true)
				                .load();
		flyway.migrate();
	}
}

