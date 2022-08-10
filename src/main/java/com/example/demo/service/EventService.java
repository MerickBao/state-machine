package com.example.demo.service;

import com.example.demo.dao.EventDAO;
import com.example.demo.domain.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 11:00
 * @Description:
 */

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;
    public EventEntity getEventById(Integer id) {
        return eventDAO.getEventById(id);
    }
}
