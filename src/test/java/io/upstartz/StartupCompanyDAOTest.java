package io.upstartz;

import io.upstartz.dao.StartupCompanyDAO;
import io.upstartz.model.StartupCompany;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
public class StartupCompanyDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    StartupCompanyDAO dao;

    @Test
    public void createAndLoad() throws Exception {
        final StartupCompany sc = new StartupCompany("betalist", "http://betalist.com/x", "Company 1", "logoUrl", "desc");
        dao.save(sc);
        Assert.assertNotNull(sc.getId());
        Assert.assertEquals(0, sc.getUpvotes());

        dao.upvote(sc.getId());

        final StartupCompany scLoad = dao.load(sc.getId());
        Assert.assertEquals(sc.getId(), scLoad.getId());
        Assert.assertEquals(1, scLoad.getUpvotes());

        // only one company in the database
        Assert.assertEquals(sc.getId(), dao.loadRandom().getId());
    }
}
