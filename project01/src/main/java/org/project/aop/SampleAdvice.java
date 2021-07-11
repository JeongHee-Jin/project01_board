package org.project.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice{
	
	  @Before("execution(* org.project01.service.MessageService*.*(..))")
	  public void startLog(JoinPoint jp) {
		  
		  System.out.println("----------------------------");
		  System.out.println("----------------------------");
		  System.out.println(Arrays.toString(jp.getArgs()));

	  }
	  
	  @Around("execution(* org.project01.service.MessageService*.*(..))")
	  public Object timeLog(ProceedingJoinPoint pjp)throws Throwable{
	    
	    long startTime = System.currentTimeMillis();
	    System.out.println(Arrays.toString(pjp.getArgs()));
	    
	    Object result = pjp.proceed();
	    
	    long endTime = System.currentTimeMillis();
	    System.out.println( pjp.getSignature().getName()+ " : " + (endTime - startTime) );
	    System.out.println("=============================================");
	    
	    return result;
	  }
}
