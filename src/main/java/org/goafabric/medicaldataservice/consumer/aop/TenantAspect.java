package org.goafabric.medicaldataservice.consumer.aop;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.goafabric.medicaldataservice.service.extensions.TenantContext;
import org.slf4j.MDC;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.annotation.RegisterReflection;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RegisterReflection(classes = TenantAspect.class, memberCategories = MemberCategory.INVOKE_DECLARED_METHODS)
public class TenantAspect {

    @Around("@annotation(tenantAwareKafkaListener)")
    public Object resolveTenantInfo(ProceedingJoinPoint joinPoint, TenantAwareKafkaListener tenantAwareKafkaListener) throws Throwable {
        Span.fromContext(Context.current()).setAttribute("tenant.id", TenantContext.getTenantId());
        MDC.put("tenantId", TenantContext.getTenantId());
        Object result = joinPoint.proceed();
        MDC.remove("tenantId");
        return result;
    }

}
