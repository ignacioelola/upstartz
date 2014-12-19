package io.upstartz;

import io.upstartz.dao.StartupCompanyDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
public class SpringContextTest {
    @Resource
    private StartupCompanyDAO dao;

    @Test
    public void verifyResource(){
        assertNotNull(dao);
    }
}
