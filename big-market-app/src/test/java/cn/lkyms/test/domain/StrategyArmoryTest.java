package cn.lkyms.test.domain;

import cn.lkyms.domain.strategy.service.armory.IStrategyArmory;
import cn.lkyms.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {

    @Resource
    private IStrategyDispatch strategyDispatch;

    @Resource
    private IStrategyArmory strategyArmory;

    @Before
    public void test_assembleLotteryStrategy() {
        boolean success = strategyArmory.assembleLotteryStrategy(10001L);
        log.info("assembleLotteryStrategy success: {}", success);
    }

    @Test
    public void test_getRandomAwardId() {
        log.info("奖品id值: {}", strategyDispatch.getRandomAwardId(10001L));
        log.info("奖品id值: {}", strategyDispatch.getRandomAwardId(10001L));
        log.info("奖品id值: {}", strategyDispatch.getRandomAwardId(10001L));
    }

    @Test
    public void test_getRandomAwardId_ruleWeight() {
        log.info("测试结果: {} --4000配置", strategyDispatch.getRandomAwardId(10001L, "4000:102,103,104,105"));
        log.info("测试结果: {} --5000配置", strategyDispatch.getRandomAwardId(10001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果: {} --6000配置", strategyDispatch.getRandomAwardId(10001L, "6000:102,103,104,105,106,107,108,109"));
    }
}
