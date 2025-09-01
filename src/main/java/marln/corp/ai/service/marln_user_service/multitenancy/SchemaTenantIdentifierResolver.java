package marln.corp.ai.service.marln_user_service.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class SchemaTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	private static final String DEFAULT_TENANT = "default";

	@Override
	public String resolveCurrentTenantIdentifier() {
		System.out.println(TenantContext.getTenantId());
		return (TenantContext.getTenantId() != null) ? TenantContext.getTenantId() : DEFAULT_TENANT;
	}

	@Override
	public boolean validateExistingCurrentSessions() { return true; }
}
