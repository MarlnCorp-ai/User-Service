package marln.corp.ai.service.marln_user_service.multitenancy;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiTenantConfig {

	private final SchemaMultiTenantConnectionProvider multiTenantConnectionProvider;
	private final SchemaTenantIdentifierResolver currentTenantIdentifierResolver;

	@Autowired
	public MultiTenantConfig(SchemaMultiTenantConnectionProvider multiTenantConnectionProvider,
	                         SchemaTenantIdentifierResolver currentTenantIdentifierResolver) {
		this.multiTenantConnectionProvider = multiTenantConnectionProvider;
		this.currentTenantIdentifierResolver = currentTenantIdentifierResolver;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan("marln.corp.ai.service.marln_user_service.entity");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Map<String, Object> props = new HashMap<>();
		// Inject Spring beans here, not class names
		props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
		emf.setJpaPropertyMap(props);
		return emf;
	}
}
