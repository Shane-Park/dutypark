package com.tistory.shanepark.dutypark.security.config

import com.tistory.shanepark.dutypark.common.exceptions.AuthenticationException
import com.tistory.shanepark.dutypark.security.domain.dto.LoginMember
import com.tistory.shanepark.dutypark.security.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class AuthResolver(
    private val authService: AuthService
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == LoginMember::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): LoginMember {
        webRequest.getNativeRequest(HttpServletRequest::class.java)?.let {
            it.cookies?.forEach { cookie ->
                if (cookie.name == "SESSION") {
                    return authService.validateToken(cookie.value)
                }
            }
        }
        throw AuthenticationException("login is required")
    }
}
