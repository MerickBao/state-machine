package com.example.demo.dao;

import com.example.demo.domain.DemoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-09 08:52
 * @Description: 用于测试的DAO : 定义与数据库的交互方法，具体的查询实现在mapper对应的xml里边
 */

@Mapper
public interface DemoDAO {

	DemoEntity getDemoEntityById(int id);
}
