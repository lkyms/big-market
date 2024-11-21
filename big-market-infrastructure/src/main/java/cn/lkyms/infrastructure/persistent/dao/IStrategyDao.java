package cn.lkyms.infrastructure.persistent.dao;

import cn.lkyms.infrastructure.persistent.po.Award;
import cn.lkyms.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyDao {
    List<Strategy> queryStrategyList();
}
