package cn.lkyms.test.infrastructure;

import cn.lkyms.infrastructure.persistent.dao.IStrategyDao;
import cn.lkyms.infrastructure.persistent.dao.IStrategyRuleDao;
import cn.lkyms.infrastructure.persistent.po.Strategy;
import cn.lkyms.infrastructure.persistent.po.StrategyRule;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRuleDaoTest {

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Test
    public void test_queryAwardList() {
        List<StrategyRule> strategyRules = strategyRuleDao.queryStrategyRuleList();
        log.info("测试结果：{}", JSON.toJSONString(strategyRules));
    }

}
