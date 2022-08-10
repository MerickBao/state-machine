package com.example.demo.dao;

import com.example.demo.domain.EventEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 11:00
 * @Description:
 */

@Mapper
public interface EventDAO {
	EventEntity getEventById(Integer id);
}
