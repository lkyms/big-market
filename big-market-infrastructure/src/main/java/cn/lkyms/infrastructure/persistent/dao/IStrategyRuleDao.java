package cn.lkyms.infrastructure.persistent.dao;

import cn.lkyms.infrastructure.persistent.po.Award;
import cn.lkyms.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyRuleDao {
    List<StrategyRule> queryStrategyRuleList();

    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);
}
