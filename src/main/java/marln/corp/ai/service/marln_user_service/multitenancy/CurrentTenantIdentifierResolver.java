package marln.corp.ai.service.marln_user_service.multitenancy;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentTenantIdentifierResolver implements org.hibernate.context.spi.CurrentTenantIdentifierResolver {

	private static final String DEFAULT_TENANT = "default";

	@Override
	public String resolveCurrentTenantIdentifier() {
		return Optional.ofNullable(TenantContext.getCurrentTenant())
				.orElse(DEFAULT_TENANT);
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}