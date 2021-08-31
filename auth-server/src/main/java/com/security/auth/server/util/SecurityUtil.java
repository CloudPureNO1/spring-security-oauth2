package com.security.auth.server.util;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <p>编辑校验security</p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/4/26 11:18
 * @since 1.0.0
 */
public class SecurityUtil {
    public static boolean isAllowSecurityEnabled() {
/*
        SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
*/

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return true;
        } else {
            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
