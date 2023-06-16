package com.zhourb.familyaccount_api.common.aspect.log;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/3/10 17:32
 * @description
 **/
@Aspect
@Component
public class LogAspect {

    // 设置操作日志切入点 记录操作日志 在注解的位置切入代码
    @Pointcut("@annotation(com.zhourb.familyaccount_api.common.aspect.log.OperationLog)")
    public void logOperatePoinCut() {
    }

    @Pointcut("execution(* com.zhourb.familyaccount_api.module..*.*(..))")
    public void logExceptionPoinCut() {
    }

    @AfterReturning(value = "logOperatePoinCut()", returning = "keys")
    public void operatorLogSave(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String token = request.getHeader("Authorization");
        LogOperationEntity logOperationEntity = new LogOperationEntity();
        try {
            logOperationEntity.setOperId(UUID.randomUUID().toString());
            Object[] args = joinPoint.getArgs();
            String params = "";
            StringBuilder stringBuilder = new StringBuilder();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String methodType = request.getMethod();
            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (args.length > 0) {
                if (!("GET".equals(methodType))) {
                    Object object = args[0];
                    Map<String, Object> map = getKeyAndValue(object);
                    params = JSON.toJSONString(map);
                } else {
                    Map<String, String> rtnMap = converMap(request.getParameterMap());
                    if (!pathVariables.isEmpty()) {
                        stringBuilder.append(JSON.toJSONString(pathVariables));
                    }
                    if (!rtnMap.isEmpty()) {
                        stringBuilder.append(JSON.toJSONString(rtnMap));
                    }
                    params = stringBuilder.toString();
                }
            }
            OperationLog operationLog = method.getAnnotation(OperationLog.class);
            if (operationLog != null) {
                String operationModul = operationLog.operModul();
                String operationType = operationLog.operType();
                String operationDesc = operationLog.operDesc();
                logOperationEntity.setOperModul(operationModul);
                logOperationEntity.setOperDesc(operationDesc);
                logOperationEntity.setOperType(operationType);
            }
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            logOperationEntity.setOperMethod(methodName);
            // Map<String, String> rtnMap = converMap(request.getParameterMap());
            ObjectMapper mapper = new ObjectMapper();
            // String params = mapper.writeValueAsString(rtnMap);
            // 请求参数
            logOperationEntity.setOperRequParam(params);
            // 返回结果
            logOperationEntity.setOperRespParam(mapper.writeValueAsString(keys));

            logOperationEntity.setOperUserName((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            // 请求URI
            logOperationEntity.setOperUri(request.getRequestURI());
            // 创建时间
            logOperationEntity.setOperCreateTime(new Date());
            logOperationEntity.setOperMethodType(methodType);
            logOperationEntity.setOperRequHeader(token);

            /**
             * @author ZhouGuiMing
             * @version 1.0
             * @description 将操作日志写入数据库
             */
//            logOperationEntity.setOperRespParam(logOperationEntity.getOperRespParam().substring(0, 55));
//
//            System.out.println("============================================================");
//            String json = mapper.writeValueAsString(logOperationEntity);
//            System.out.println(json);
//
//            System.out.println("====================================================================");
//            Map jsonMap = JSON.parseObject(json);
//            System.out.println(jsonMap);
//

//
//            int effectRows = logOperationService.insertSelective(jsonMap);
//            System.out.println(effectRows);
//            if (effectRows < 0) {
//                throw new MyLogException((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal() + methodType + methodName + "(" + params + ")" + "方法的操作日志添加失败");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterThrowing(value = "logExceptionPoinCut()", throwing = "e")
    public void exceptionLogSave(JoinPoint joinPoint, Throwable e) {
        LogOperationEntity logOperationEntity;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String token = request.getHeader("Authorization");
        LogExceptionEntity logExceptionEntity = new LogExceptionEntity();
        try {
            Object[] args = joinPoint.getArgs();
            String params = "";
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String methodType = request.getMethod();
            //获取请求参数集合并进行遍历拼接
            if (args.length > 0) {
                if (!("GET".equals(methodType))) {
                    Object object = args[0];
                    Map<String, Object> map = getKeyAndValue(object);
                    params = JSON.toJSONString(map);

                } else {
                    Map<String, String> rtnMap = converMap(request.getParameterMap());
                    params = JSON.toJSONString(rtnMap);
                }
            }
            logExceptionEntity.setExcId(UUID.randomUUID().toString());
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            ObjectMapper mapper = new ObjectMapper();
            // 请求参数
            logExceptionEntity.setExcRequParam(params);
            // 请求方法名
            logExceptionEntity.setOperMethod(methodName);
            // 异常名称
            logExceptionEntity.setExcName(e.getClass().getName());
            // 异常信息
            logExceptionEntity.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            // 操作员名称
            logExceptionEntity.setOperUserName((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            // 操作URI
            logExceptionEntity.setOperUri(request.getRequestURI());
            // 发生异常时间
            logExceptionEntity.setOperCreateTime(new Date());
            //异常的方法类型
            logExceptionEntity.setOperMethodType(methodType);
            //操作请求头
            logExceptionEntity.setOperRequHeader(token);


            /**
             * @author ZhouGuiMing
             * @version 1.0
             * @description 将异常日志写入数据库
             */
//            logExceptionEntity.setExcMessage(logExceptionEntity.getExcMessage().substring(0, 55));
//
//            String json = mapper.writeValueAsString(logExceptionEntity);
//            System.out.println(json);
//
//            Map jsonMap = JSON.parseObject(json);
//            System.out.println(jsonMap);
//
//            Map<String, String> stringStringMap = JSON.parseObject(JSON.toJSONString(logExceptionEntity), new TypeReference<Map<String, String>>() {
//            });
//            System.out.println(stringStringMap);
//
//
//            int effectRows = logExceptionService.insertSelective(jsonMap);
//            if (effectRows < 0) {
//                throw new MyLogException("方法：" + methodName + "（" + params + ")" + "的" + e.getClass().getName() + "异常日志添加失败");
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

    private static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (Field f : fs) {
            // 设置些属性是可以访问的
            f.setAccessible(true);
            Object val;
            try {
                // 得到此属性的值
                val = f.get(obj);
                // 设置键值
                map.put(f.getName(), val);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
