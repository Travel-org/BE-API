package com.ssu.travel.security.resolver;

import com.ssu.travel.security.annotation.CurrentUser;
import com.ssu.travel.security.model.CustomUserDetails;
import com.ssu.travel.security.model.SessionUser;
import com.ssu.travel.user.User;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isUserClass && isLoginUserAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        User user = principal.getUser();

        return SessionUser.builder()
                .userId(user.getId())
                .name(user.getName())
                .profilePath(user.getProfilePath())
                .accessToken(principal.getAccessToken())
                .build();
    }

}
