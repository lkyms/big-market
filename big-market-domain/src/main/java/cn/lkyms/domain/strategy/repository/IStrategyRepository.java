package cn.lkyms.domain.strategy.repository;

import cn.lkyms.domain.strategy.model.entity.StrategyAwardEntity;
import cn.lkyms.domain.strategy.model.entity.StrategyEntity;
import cn.lkyms.domain.strategy.model.entity.StrategyRuleEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTables(String key, BigDecimal rateRange, HashMap<Integer, Integer> shuffledStrategyAwardSearchRateTables);

    int getRateRange(Long strategyId);

    int getRateRange(String key);

    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    StrategyEntity getStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity getStrategyRule(Long strategyId, String ruleModel);
}
