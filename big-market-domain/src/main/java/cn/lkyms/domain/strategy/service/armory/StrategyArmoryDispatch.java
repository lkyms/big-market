package cn.lkyms.domain.strategy.service.armory;

import cn.lkyms.domain.strategy.model.entity.StrategyAwardEntity;
import cn.lkyms.domain.strategy.model.entity.StrategyEntity;
import cn.lkyms.domain.strategy.model.entity.StrategyRuleEntity;
import cn.lkyms.domain.strategy.repository.IStrategyRepository;
import cn.lkyms.types.enums.ResponseCode;
import cn.lkyms.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

@Service
@Slf4j
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    @Resource
    private IStrategyRepository repository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1. 查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities =  repository.queryStrategyAwardList(strategyId);
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        // 2. 权重策略配置 -- 适用于rule_weight
        StrategyEntity strategyEntity = repository.getStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();
        if(ruleWeight == null) {
            return true;
        }
        StrategyRuleEntity strategyRuleEntity = repository.getStrategyRule(strategyId, ruleWeight);
        if(strategyRuleEntity == null) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();
        for(String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }

        return true;
    }

    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
        // 2. 获取最小概率
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 3. 获取概率总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. 用 1 / 0.0001 获取概率范围，百分位，千分位，万分位
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 5.
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for(StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();

            // 计算每个概率，需要存放到查找表的数量，循环填充
            for(int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++ ) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }

        // 6. 乱序
        Collections.shuffle(strategyAwardSearchRateTables);

        // 7. 生成集合
        HashMap<Integer, Integer> shuffledStrategyAwardSearchRateTables = new HashMap<>();
        for(int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffledStrategyAwardSearchRateTables.put(i, strategyAwardSearchRateTables.get(i));
        }

        // 8. 存储到redis
//        repository.storeStrategyAwardSearchRateTables(key, rateRange, shuffledStrategyAwardSearchRateTables);
        repository.storeStrategyAwardSearchRateTables(key, BigDecimal.valueOf(shuffledStrategyAwardSearchRateTables.size()), shuffledStrategyAwardSearchRateTables);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = repository.getRateRange(strategyId);
        return repository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        int rateRange = repository.getRateRange(key);
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }
}
