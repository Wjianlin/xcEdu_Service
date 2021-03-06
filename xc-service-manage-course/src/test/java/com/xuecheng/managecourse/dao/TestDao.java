package com.xuecheng.managecourse.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.managecourse.client.CmsPageClient;
import com.xuecheng.managecourse.service.CourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseService courseService;
    @Test
    public void testCourseBaseRepository(){
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper(){
        CourseBase courseBase = courseMapper.findCourseBaseById("402885816240d276016240f7e5000002");
        System.out.println(courseBase);

    }

    @Test
    public void test(){
        TeachplanNode teachplanList = courseService.findTeachplanList("4028e581617f945f01617f9dabc40000");
    }

    @Autowired
    private RibbonLoadBalancerClient client;
    //负载均衡调用
    @Test
    public void testRibbon(){
        //服务id
        String serviceId = "XC-SERVICE-MANAGE-CMS";
        for (int i = 0; i < 10; i++) {
            ServiceInstance instance = this.client.choose(serviceId);
            System.out.println(instance.getHost() + ":" + instance.getPort());
        }
    }

    @Autowired
    private CmsPageClient cmsPageClient;

    @Test
    public void testFeign(){
        CmsPageResult cmsPageResult = cmsPageClient.findById("5a795ac7dd573c04508f3a56");
        System.out.println(cmsPageResult);
    }
}
