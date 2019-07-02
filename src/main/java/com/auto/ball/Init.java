package com.auto.ball;

import com.auto.ball.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.auto.ball.sce.Constant.UAS;

/**
 * 项目初始化 自动打开浏览器
 * @author Ady_L
 */
@Component
@Slf4j
public class Init  implements CommandLineRunner {
    @Value("${bet.url}")
    private String URL;
    @Override
    public void run(String... args) throws Exception {
        try {
            //开启个浏览器并且输入链接
            ChromeDriver driver = PageUtils.getChromeDriver(URL);

            //获取已打开浏览器的sessionId
            String sessionId = driver.getSessionId().toString();
            log.error("sessionId:" + sessionId);
            //获取已打开浏览器的URL
            String url = ((HttpCommandExecutor) (driver.getCommandExecutor())).getAddressOfRemoteServer().toString();
            log.error(url);
            //保存数据
            UAS.setSessionID(sessionId);
            UAS.setLocalserver(url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
