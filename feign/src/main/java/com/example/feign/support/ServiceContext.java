package com.example.feign.support;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

import java.util.concurrent.Callable;

/**
 * 租户上下文
 */
public class ServiceContext {

	public static final String TENANT_HEADER = "x-tenant-id";

	// HystrixRequestVariableDefault 实现的伪线程上下文传递
	public static HystrixRequestVariableDefault<Tenant> TENANT = new HystrixRequestVariableDefault<>();
	// InheritableThreadLocal 实现的线程间上下文传递
	public static InheritableThreadLocal<Long> THREAD_LOCAL_TENANT = new InheritableThreadLocal<>();

	public static void setTenant(Tenant tenant) {
		initHystrixRequestContext();
		TENANT.set(tenant);
	}

	public static Tenant getTenant() {
		return TENANT.get();
	}

	public static <V> V executeWithTenant(Tenant tenant, Callable<V> callable) {

		Tenant oldTenant = TENANT.get();
		try {
			initHystrixRequestContext();
			TENANT.set(tenant);
			return callable.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			TENANT.set(oldTenant);
		}
	}

	private static void initHystrixRequestContext() {
		if (!HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.initializeContext();
		}
	}
}
