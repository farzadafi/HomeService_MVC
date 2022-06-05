package com.example.HomeService_MVC.aspect;

import com.example.HomeService_MVC.controller.exception.ForbiddenException;
import com.example.HomeService_MVC.service.impel.CaptchaValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CaptchaAspect {

    private final CaptchaValidator captchaValidator;

    private static final String CAPTCHA_HEADER_NAME = "captcha-response";

    public CaptchaAspect(CaptchaValidator captchaValidator) {
        this.captchaValidator = captchaValidator;
    }

    @Around("@annotation(RequiresCaptcha)")
    public Object validateCaptcha(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String captchaResponse = request.getHeader(CAPTCHA_HEADER_NAME);
        boolean isValidCaptcha = captchaValidator.validateCaptcha(captchaResponse);
        if(!isValidCaptcha){
            throw new ForbiddenException("لطفا الگو کپچا را به درستی وارد کنید");
        }
        return joinPoint.proceed();
    }

}
