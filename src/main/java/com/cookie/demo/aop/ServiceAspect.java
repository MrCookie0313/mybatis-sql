package com.cookie.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @program: industry
 * @description:
 * @author: zhangshulong
 * @create: 2020-02-18 19:51
 **/

@Aspect
@Component
@Slf4j
public class ServiceAspect {
    /**
     * Pointcut定义切点
     * public修饰符的   返回值任意  com.cy.controller包下面的任意类的任意方法任意参数
     */
    @Pointcut("execution(public * com.slife.zphw.mapper.postgresql..*(..))")
    public void log(){

    }
//    @Resource(name = "zpcgSqlSessionFactory")
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
//    @Resource
 //   private SqlSessionFactoryBean sqlSessionFactoryBean;
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
//        System.out.println("进入切面方法执行前...");
//        ServletRequestAttributes sra =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (!Objects.isNull(sra) && !Objects.isNull(sra.getRequest())){
//            HttpServletRequest request = sra.getRequest();
//            System.out.println("url: " + request.getRequestURI());
//            System.out.println("method: "+request.getMethod());      //post or get? or ?
//            System.out.println("class.method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//            System.out.println("args: "+joinPoint.getArgs());
//        }
//
//        System.out.println("切面结束...");
        //kx_parse_mapper id,url,mapperName,mapperMethod,controllerName,cMethod,cMethodNotes
        //kx_parse_mapperxml  id,xml_name,xml_method(对应标签id  要排除resultMap标签),p_xml_method(父标签id,此id指的是标签id属性),xml_content
    }
    @After("log()")
    public void doAfter(JoinPoint joinPoint){
//        System.out.println("进入切面方法执行后...");
//
//        ServletRequestAttributes sra =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (!Objects.isNull(sra) && !Objects.isNull(sra.getRequest())){
//            HttpServletRequest request = sra.getRequest();
//            System.out.println("url: " + request.getRequestURI());
//            System.out.println("method: "+request.getMethod());      //post or get? or ?
//            System.out.println("class.method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//            System.out.println("args: "+joinPoint.getArgs());
//        }
//
//        System.out.println("切面结束...");
        //kx_parse_mapper id,url,mapperName,mapperMethod,controllerName,cMethod,cMethodNotes
        //kx_parse_mapperxml  id,xml_name,xml_method(对应标签id  要排除resultMap标签),p_xml_method(父标签id,此id指的是标签id属性),xml_content
    }

    //环绕通知
    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("++++++++++环绕通知开始+++++++++++");

        //1.从redis中获取主数据库，若获取不到直接退出，否则判断当前数据源是会否为主，若不为主，则切换到主数据源
        //2.调用目标方法
        ServletRequestAttributes sra =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!Objects.isNull(sra) && !Objects.isNull(sra.getRequest())){
            HttpServletRequest request = sra.getRequest();
            System.out.println("url: " + request.getRequestURI());
            System.out.println("method: "+request.getMethod());      //post or get? or ?
            System.out.println("class.method: " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
            System.out.println("args: "+pjp.getArgs());
        }
        Object proceed = pjp.proceed();
        //3.获取SQL
        String sql = SqlUtils.getMybatisSql(pjp, sqlSessionFactory);
        System.out.println("##################################################"+sql+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("++++++++++环绕通知结束+++++++++++");
        return proceed;
    }
}

