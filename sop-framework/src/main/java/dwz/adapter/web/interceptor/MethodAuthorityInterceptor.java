package dwz.adapter.web.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Controller;

/**
 * @Author: LCF
 * @Date: 2020/1/8 11:36
 * @Package: dwz.adapter.web.interceptor
 */

public class MethodAuthorityInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {

        Class<?> clazz = invocation.getThis().getClass();

        if (clazz.isAnnotationPresent(Controller.class)) {
            Controller controller = clazz.getAnnotation(Controller.class);
            String controllerName = controller.value().trim();
            String methodName = invocation.getMethod().getName();

            System.out.println("controllerName:" + controllerName);
            System.out.println("methodName:" + methodName);
        }

//		if (noAuthorith) {
//			return null;
//		}
//		SpringContextHolder.getApplicationContext().findAnnotationOnBean(arg0, arg1)
//		HttpServletRequest request = null;
//        ActionMapping mapping = null;
//        Object[] args = invocation.getArguments();
//        for (int i = 0 ; i < args.length ; i++ )
//        {
//            if (args[i] instanceof HttpServletRequest) request = (HttpServletRequest)args[i];
//            if (args[i] instanceof ActionMapping) mapping = (ActionMapping)args[i];
//        }


        return invocation.proceed();
    }

}
