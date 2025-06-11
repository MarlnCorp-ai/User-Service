package marln.corp.ai.service.marln_user_service.restcall;

import marln.corp.ai.service.marln_user_service.dto.UserRoleRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface RestCall {

   void assignRoles(String userId, String roleId, List<String> permissionList);



}
