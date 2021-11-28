package com.guigu.gulimallthirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallThirdPartyApplicationTests {


    @Resource
    private OSSClient ossClient;


    @Test
    public void testUpload() throws IOException {
        InputStream inputStream = new FileInputStream("/Users/liuhangfei/Desktop/Kafka整体介绍.png");
        ossClient.putObject("dearcandy-bucket01","test001.png",inputStream);

        ossClient.shutdown();
        System.out.println("上传完成");

    }
}
