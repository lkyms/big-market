package cn.lkyms.domain.strategy.model.entity;

import cn.lkyms.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRuleEntity {
    private Long strategyId;
    private Integer awardId;
    private Integer ruleType;
    private String ruleModel;
    private String ruleValue;
    private String ruleDesc;

    public Map<String, List<Integer>> getRuleWeightValues() {
        if(!"rule_weight".equals(ruleModel)) {
            return null;
        }
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<String, List<Integer>> resultMap = new HashMap<>();
        for(String ruleValueGroup : ruleValueGroups) {
            if (ruleValueGroup == null || ruleValueGroup.isEmpty()) {
                return resultMap;
            }
            String[] parts = ruleValueGroup.split(Constants.COLON);
            if(parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_value invalid input format " + ruleValueGroup);
            }
            String[] valueStrings = parts[1].split(Constants.SPLIT);
            List<Integer> values = new ArrayList<>();
            for(String valueString : valueStrings) {
                values.add(Integer.parseInt(valueString));
            }
            resultMap.put(ruleValueGroup, values);
        }
        return resultMap;
    }
}
