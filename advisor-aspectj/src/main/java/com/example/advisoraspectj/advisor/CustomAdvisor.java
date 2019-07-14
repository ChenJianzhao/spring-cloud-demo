package com.example.advisoraspectj.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;

@Slf4j
@Component
public class CustomAdvisor extends DefaultPointcutAdvisor {

//	private LogAdvice logAdvice;

	CustomAdvisor(LogAdvice advice) {
		super(advice);
	}

	@PostConstruct
	public void postConstruct() {
//		this.setAdvice(this.logAdvice);
		this.setPointcut(new ControllerAnnotationPointCut(RestController.class, RequestMapping.class));
	}


	class ControllerAnnotationPointCut extends AnnotationMatchingPointcut {

		ControllerAnnotationPointCut(Class<? extends Annotation> classAnnotationType,
									 Class<? extends Annotation> methodAnnotationType) {
			super(classAnnotationType, methodAnnotationType);
		}
	}
}
