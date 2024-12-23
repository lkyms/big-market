package cn.lkyms.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardEntity {
    private Long strategyId;
    private Integer awardId;
    private Integer awardCount;
    private Integer awardCountSurplus;
    private BigDecimal awardRate;
}
