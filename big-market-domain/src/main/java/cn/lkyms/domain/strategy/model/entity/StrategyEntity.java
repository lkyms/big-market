package cn.lkyms.domain.strategy.model.entity;

import cn.lkyms.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {
    private Long strategyId;
    private String strategyDesc;
    private String ruleModels;

    public String[] ruleModels() {
        if(StringUtils.isBlank(ruleModels)) {
            return null;
        }
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight() {
        String[] ruleModels = ruleModels();
        for(String ruleModel : ruleModels) {
            if("rule_weight".equals(ruleModel)) {
                return ruleModel;
            }
        }
        return null;
    }
}
