package marln.corp.ai.service.marln_user_service.multitenancy;

public class TenantContext {
	private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
	public static void setTenantId(String tenant) { CURRENT_TENANT.set(tenant); }
	public static String getTenantId() { return CURRENT_TENANT.get(); }
	public static void clear() { CURRENT_TENANT.remove(); }
}