package com.foundation;

import com.foundation.common.date.DateUtils;
import com.foundation.dao.entry.app.AppVersion;
import com.foundation.service.app.IAppVersionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:/spring/spring-context.xml",
        "classpath:/spring/spring-db.xml","classpath:/spring/spring-cache.xml"
})
public class AppVersionServiceTests {
    private MockMvc mockMvc;//

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    IAppVersionService appVersionService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }




    //@Test
    public void getVersion() throws Exception {
        AppVersion v=appVersionService.getAppVersion("3", "3");
        System.out.println(v);

        AppVersion v2=appVersionService.getAppVersion("1", "1");
        System.out.println(v2);

        AppVersion v3=appVersionService.getAppVersion("2", "2");
        System.out.println(v3);

        AppVersion v4=appVersionService.getAppVersion("1", "1");
        System.out.println("v4:"+v4);

        AppVersion v5=appVersionService.getAppVersion("2", "2");
        System.out.println("v5:"+v5);
    }


    @Test
    public void save() throws Exception {
        AppVersion v=new AppVersion();
        v.setType(3);
        v.setVersion("3");
        v.setUrl("www.baidu.com");
        v.setForceUpdate(1);
        long nowDate=DateUtils.nowDateToTimestamp();
        v.setCreateDate(nowDate);
        v.setUpdateDate(nowDate);
        System.out.println(appVersionService.getAppVersion("3", "3"));
        v=appVersionService.save(v);
        System.out.println(v);
        System.out.println(appVersionService.getAppVersion("3", "3"));
        System.out.println(appVersionService.getAppVersion("3", "3"));
    }

}
