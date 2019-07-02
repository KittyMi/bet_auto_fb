package com.auto.ball.sce;

import com.auto.ball.entity.MulFactorsNewBetOnly;
import com.auto.ball.service.MulFactorsNewBetOnlyService;
import com.auto.ball.util.MyChromeDriver;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;

import static com.auto.ball.sce.Constant.UAS;

/**
 * 自动投注(业务就不涉及了)
 * @author Ady_L
 */
@Component
@Slf4j
public class AutoBetFootBallJob {

    @Value("${bet.price}")
    private String betPrice;
    @Value("${bet.url}")
    private String URL;
    @Resource
    MulFactorsNewBetOnlyService mulFactorsNewBetOnlyService;
    @Scheduled(cron = "0 */5 * * * *")
    public void refreshGoogle() throws Exception{
        ChromeDriver driver = new MyChromeDriver(UAS.getSessionID(), UAS.getLocalserver());
        driver.get(URL);
        Thread.sleep(1000 * 3);
        driver.navigate().refresh();
    }
    public boolean doesWebElementExist(ChromeDriver driver, By selector) {
        try
        {
            driver.findElement(selector);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }
    @Scheduled(cron = "*/30 * * * * *")
    public void scheduled(){
        log.error("自动投注  {}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())); SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long s=System.currentTimeMillis();
        long e=s-30*1000;
        QueryWrapper<MulFactorsNewBetOnly> q=new QueryWrapper<MulFactorsNewBetOnly>();
        q.between("gmt_create",sdf.format(e),sdf.format(s));
        List<MulFactorsNewBetOnly> list=mulFactorsNewBetOnlyService.list(q);

        if(CollectionUtils.isNotEmpty(list)){
            ChromeDriver driver=null;
            for (MulFactorsNewBetOnly mulFactorsNewBetOnly : list) {
                    try {
                    //构造器获取sessionId和URL
                    driver = new MyChromeDriver(UAS.getSessionID(), UAS.getLocalserver());
                    driver.get(URL);
                    Thread.sleep(1000 * 6);
                    //全部删除
                    driver.switchTo().frame(driver.findElement(By.className("bw-BetslipWebModule_Frame")));
                    if(doesWebElementExist(driver,By.id("bsDiv"))) {
                        WebElement el1=driver.findElement(By.id("bsDiv"));
                        try{
                            el1.findElement(By.className("bs-Header_RemoveAllLink")).click();
                        }catch (Exception e1){
                            e1.printStackTrace();

                        }
                    }
                    driver.switchTo().defaultContent();
                    Thread.sleep(1000 * 3);

                    if (Integer.parseInt(mulFactorsNewBetOnly.getFenzhong()) <= 45) {
                        //半场
                        indexHalfFootball(driver, (mulFactorsNewBetOnly.getHostGuest().equals("host"))?0:1,mulFactorsNewBetOnly.getHostName(),mulFactorsNewBetOnly.getGuestName(),"0.0",mulFactorsNewBetOnly.getScoreHost()+"-"+mulFactorsNewBetOnly.getScoreGuest());
                    }else{
                        //全场
                        indexWholeFootball(driver, (mulFactorsNewBetOnly.getHostGuest().equals("host"))?0:1,mulFactorsNewBetOnly.getHostName(),mulFactorsNewBetOnly.getGuestName(),"0.0",mulFactorsNewBetOnly.getScoreHost()+"-"+mulFactorsNewBetOnly.getScoreGuest());
                    }
                    }catch (Exception el){
                        el.printStackTrace();
                        log.error("投注失败");
                        //全部删除
                        driver.switchTo().frame(driver.findElement(By.className("bw-BetslipWebModule_Frame")));
                        if(doesWebElementExist(driver,By.id("bsDiv"))) {
                            WebElement el1=driver.findElement(By.id("bsDiv"));
                            try{
                                el1.findElement(By.className("bs-Header_RemoveAllLink")).click();
                            }catch (Exception e1){
                                e1.printStackTrace();

                            }
                        }
                        driver.switchTo().defaultContent();
                    }
                }

        }else{
            log.error("暂时没有合适的球赛可以下注！");
        }

    }

    /**
     * 投注半场
      * @param driver
     * @param index
     * @param host
     * @param guest
     * @param b
     * @param sbf
     * @throws Exception
     */
    public void indexHalfFootball(ChromeDriver driver, Integer index, String host, String guest, String b, String sbf) throws Exception{
        driver.findElements(By.className("ip-ControlBar_BBarItem")).get(0).click();//点击赛事查看
        driver.findElements(By.className("ipo-ClassificationBarButtonBase")).get(1).click();//选择足球

        List<WebElement> list = driver.findElements(By.className("ipo-Fixture_TableRow"));
        for (WebElement we : list) {
            String hostName = we.findElements(By.className("ipo-TeamStack_Team")).get(0).getText();//主队名称
            String guestName = we.findElements(By.className("ipo-TeamStack_Team")).get(1).getText();//客队名称
            if (hostName.equals(host)
                    && guestName.equals(guest)
            ) {
                we.findElement(By.className("ipo-FixtureEventCountButton_EventCountWrapper")).click();//点击到具体的球赛页面
                break;
            }
        }
        Thread.sleep(1000*3);
        //半场
        WebElement element = driver.findElements(By.className("gl-MarketGrid_PairL")).get(1);
        if(element.findElement(By.className("gl-MarketGroupButton_Text")).getText().contains("上半场")) {
            list = element.findElements(By.className("gl-Market_General"));
            String hostName = list.get(index).findElement(By.className("gl-MarketColumnHeader")).getText();
            List<WebElement> elementList = list.get(index).findElements(By.className("gl-ParticipantCentered_BlankName"));
            log.error("下注球队名称：" + hostName);
            for (WebElement we : elementList) {
                //比分
                String bf = we.findElement(By.className("gl-ParticipantCentered_Handicap")).getText();
                //赔率
                String pl = we.findElement(By.className("gl-ParticipantCentered_Odds")).getText();
                if (bf.equals(b)) {
                    we.click();
                }
            }

            Thread.sleep(1000*1);
            log.error("投注");
            try {
                execBet(driver, sbf, index);
            }catch (Exception e){
                e.printStackTrace();
                execBet(driver, sbf, index);
            }
        }
    }

    /**
     * 投注全场
      * @param driver
     * @param index
     * @param host
     * @param guest
     * @param b
     * @param sdf
     * @throws Exception
     */
    public void indexWholeFootball(ChromeDriver driver,Integer index,String host,String guest,String b,String sdf) throws Exception{
        driver.findElements(By.className("ip-ControlBar_BBarItem")).get(0).click();//点击赛事查看
        driver.findElements(By.className("ipo-ClassificationBarButtonBase")).get(1).click();//选择足球

        List<WebElement> list = driver.findElements(By.className("ipo-Fixture_TableRow"));
        for (WebElement we : list) {
            String hostName = we.findElements(By.className("ipo-TeamStack_Team")).get(0).getText();//主队名称
            String guestName = we.findElements(By.className("ipo-TeamStack_Team")).get(1).getText();//客队名称
            if (hostName.equals(host)
                    && guestName.equals(guest)
            ) {
                we.findElement(By.className("ipo-FixtureEventCountButton_EventCountWrapper")).click();//点击到具体的球赛页面
                break;
            }
        }
        Thread.sleep(1000*3);
        //全场
        WebElement element = driver.findElements(By.className("gl-MarketGrid_PairL")).get(0);
        list = element.findElements(By.className("gl-Market_General"));
        String hostName = list.get(index).findElement(By.className("gl-MarketColumnHeader")).getText();
        List<WebElement> elementList = list.get(index).findElements(By.className("gl-ParticipantCentered_BlankName"));
        log.error("下注球队名称：" + hostName);
        for (WebElement we : elementList) {
            //比分
            String bf = we.findElement(By.className("gl-ParticipantCentered_Handicap")).getText();
            //赔率
            String pl = we.findElement(By.className("gl-ParticipantCentered_Odds")).getText();
            if (bf.equals(b)) {
                we.click();
            }
        }
        Thread.sleep(1000*3);
        log.error("投注");
        try {
            execBet(driver, sdf, index);
        }catch (Exception e){
            e.printStackTrace();
            execBet(driver, sdf, index);
        }
    }

    //执行下注
    public void execBet(ChromeDriver driver,String sdf,Integer index){
        //切换到iframe
        driver.switchTo().frame(driver.findElement(By.className("bw-BetslipWebModule_Frame")));
        WebElement el=driver.findElement(By.id("bsDiv"));
        String currentOdds=el.findElement(By.className("bs-Odds")).getText();
        String currentSelection = el.findElement(By.className("bs-Selection_Desc")).getText();
        currentSelection = currentSelection.substring(1, currentSelection.lastIndexOf(")"));
        log.error("比分："+currentSelection);
        log.error("当前赔率："+currentOdds);
        if(Float.parseFloat(currentOdds)>1.625) {
            //确认金额改变
            el.findElement(By.className("bs-BtnAccept")).click();
            currentOdds=el.findElement(By.className("bs-Odds")).getText();
            currentSelection = el.findElement(By.className("bs-Selection_Desc")).getText();
            currentSelection = currentSelection.substring(1, currentSelection.lastIndexOf(")"));
            log.error("***************************");
            log.error("数据库比分："+sdf);
            log.error("比分："+currentSelection);
            log.error("当前赔率："+currentOdds);
            String[] strs=currentSelection.split("\\-");
            String[] indexs=sdf.split("\\-");
            //赔率改变： 赔率要大于 1.625 并且比分没有改变
            if(Float.parseFloat(currentOdds)>1.625 && strs[index].equals(indexs[index])) {
                el.findElement(By.className("bs-BtnAccept")).click();
                //设置投注金额
                //el.findElement(By.className("single-section")).findElement(By.className("bs-Stake_TextBox")).clear();//先清空
                el.findElement(By.className("single-section")).findElement(By.className("bs-Stake_TextBox")).sendKeys(betPrice);
                el.findElement(By.className("bs-BtnAccept")).click();
                //下注
                el.findElement(By.className("bs-BtnHover")).click();
                log.error(
                        "下注时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) +
                                "下注金额：" + betPrice +
                                "下注成功.");
                el.findElement(By.className("bs-Header_RemoveAllLink")).click();
            }else{
                log.error(
                        "赔率低于 1.625 或者下注的球队已经进球 ");
                //全部删除
                el.findElement(By.className("bs-Header_RemoveAllLink")).click();
            }

        }
        //退出iframe
        driver.switchTo().defaultContent();
    }
}
