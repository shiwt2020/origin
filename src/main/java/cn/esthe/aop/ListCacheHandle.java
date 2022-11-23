package cn.esthe.aop;

import cn.esthe.UtopiaApplication;
import cn.esthe.annotation.ListCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ListCacheHandle {
    private Logger logger = LoggerFactory.getLogger(ListCacheHandle.class);

    @Pointcut("@annotation(cn.esthe.annotation.ListCache)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(listCache)")
    public String around(ProceedingJoinPoint joinPoint, ListCache listCache) {

        logger.info("begin to execute around");

        return "test";
    }
}
