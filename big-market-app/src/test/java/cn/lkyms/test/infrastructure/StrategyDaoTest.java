package cn.lkyms.test.infrastructure;

import cn.lkyms.infrastructure.persistent.dao.IAwardDao;
import cn.lkyms.infrastructure.persistent.dao.IStrategyDao;
import cn.lkyms.infrastructure.persistent.po.Award;
import cn.lkyms.infrastructure.persistent.po.Strategy;
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
public class StrategyDaoTest {

    @Resource
    private IStrategyDao strategyDao;

    @Test
    public void test_queryAwardList() {
        List<Strategy> strategies = strategyDao.queryStrategyList();
        log.info("测试结果：{}", JSON.toJSONString(strategies));
    }

}
