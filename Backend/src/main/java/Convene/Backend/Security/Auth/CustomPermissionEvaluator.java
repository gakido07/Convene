package Convene.Backend.Security.Auth;

import Convene.Backend.Security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final SecurityUtil securityUtil;

    public CustomPermissionEvaluator(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object object, Object permission) {

        if((authentication == null) || (object == null) || !(permission instanceof String)) {
            return false;
        }
        String targetType = object.getClass().getSimpleName().toUpperCase();
        if(permission.equals("ADMIN")) {
            return hasAdminPrivilege(authentication, targetType, object.toString());
        }

        if(permission.equals("MEMBER")) {
            return (hasMemberPrivilege(authentication, targetType, object.toString())
                    ||
                    hasAdminPrivilege(authentication, targetType, object.toString()));
        }

        log.info("Denying access for user " + authentication.getPrincipal().toString() + " without permission "
                + permission.toString() );

        return false;
    }



    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    private Boolean hasMemberPrivilege(Authentication authentication, String targetType, String permission) {
        List<Long> memberPrivileges = securityUtil.extractUserPrivileges(authentication.getAuthorities())
                .getMemberPrivileges();
        if(memberPrivileges.contains(Long.valueOf(permission))){
            return true;
        }
        return false;
    }

    private Boolean hasAdminPrivilege(Authentication authentication, String targetType, String permission) {
        List<Long> adminPrvileges = securityUtil.extractUserPrivileges(authentication.getAuthorities())
                .getAdminPrivileges();
        if(adminPrvileges.contains(Long.valueOf(permission))) {
            return true;
        }

        return false;
    }
}
