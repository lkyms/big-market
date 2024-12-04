package cn.lkyms.infrastructure.persistent.repository;

import cn.lkyms.domain.strategy.model.entity.StrategyAwardEntity;
import cn.lkyms.domain.strategy.model.entity.StrategyEntity;
import cn.lkyms.domain.strategy.model.entity.StrategyRuleEntity;
import cn.lkyms.domain.strategy.repository.IStrategyRepository;
import cn.lkyms.infrastructure.persistent.dao.IStrategyAwardDao;
import cn.lkyms.infrastructure.persistent.dao.IStrategyDao;
import cn.lkyms.infrastructure.persistent.dao.IStrategyRuleDao;
import cn.lkyms.infrastructure.persistent.po.Strategy;
import cn.lkyms.infrastructure.persistent.po.StrategyAward;
import cn.lkyms.infrastructure.persistent.po.StrategyRule;
import cn.lkyms.infrastructure.persistent.redis.IRedisService;
import cn.lkyms.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (strategyAwardEntities != null && !strategyAwardEntities.isEmpty()) {
            return strategyAwardEntities;
        }
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTables(String key, BigDecimal rateRange, HashMap<Integer, Integer> shuffledStrategyAwardSearchRateTables) {
        // 1.存储抽奖策略范围值，如1000，生成1000以内随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange.intValue());
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(shuffledStrategyAwardSearchRateTables);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public StrategyEntity getStrategyEntityByStrategyId(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if(strategyEntity != null) {
            return strategyEntity;
        }
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        strategyEntity = StrategyEntity.builder()
                  .strategyId(strategy.getStrategyId())
                  .strategyDesc(strategy.getStrategyDesc())
                  .ruleModels(strategy.getRuleModels())
                  .build();
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity getStrategyRule(Long strategyId, String ruleModel) {
        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleRes.getStrategyId())
                .awardId(strategyRuleRes.getAwardId())
                .ruleType(strategyRuleRes.getRuleType())
                .ruleModel(strategyRuleRes.getRuleModel())
                .ruleValue(strategyRuleRes.getRuleValue())
                .ruleDesc(strategyRuleRes.getRuleDesc())
                .build();
    }
}
