package com.sqlist.web.service.impl;

import com.sqlist.web.domain.Task;
import com.sqlist.web.domain.User;
import com.sqlist.web.mapper.TaskMapper;
import com.sqlist.web.mapper.TaskSendSumMapper;
import com.sqlist.web.service.TaskSendSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SqList
 * @date 2019/5/13 23:56
 * @description
 **/
@Service
public class TaskSendSumImpl implements TaskSendSumService {

    @Autowired
    private TaskSendSumMapper taskSendSumMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Map getInfoBetweenTime(Task task, Date startTime, Date endTime) {
        List<Task> taskList = taskMapper.select(task);

        int cap = (int) (taskList.size() / 0.75 + 1);
        HashMap<Integer, Map> tidToResult = new HashMap<>(cap);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        taskList.forEach(tmpTask -> {
            HashMap<String, Object> tmpMap = new HashMap<>(3);
            tmpMap.put("name", tmpTask.getName());

            List<Map<String, Object>> countPerHourMap = taskSendSumMapper.selectCountBetweenTime(tmpTask, startTime, endTime);

            HashMap<String, Integer> timeToCountMap = new HashMap<>((int) (countPerHourMap.size() / 0.75 + 1));
            countPerHourMap.forEach(timeCountMap ->
                    timeToCountMap.put(sdf.format(timeCountMap.get("time")), (Integer) timeCountMap.get("count")));
            tmpMap.put("timeToCountMap", timeToCountMap);

            tidToResult.put(tmpTask.getTid(), tmpMap);
        });

        return tidToResult;
    }

    @Override
    public Map get7DayInfo(Task task) {
        List<Task> taskList = taskMapper.select(task);

        int cap = (int) (taskList.size() / 0.75 + 1);
        HashMap<Integer, Map> tidToResult = new HashMap<>(cap);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        taskList.forEach(tmpTask -> {
            HashMap<String, Object> tmpMap = new HashMap<>(3);
            tmpMap.put("name", tmpTask.getName());

            List<Map<String, Object>> countPerDayMap = taskSendSumMapper.selectCountPerDayIn7Day(tmpTask);

            // 将 数据库的结果 转换成 time -> count
            HashMap<String, BigDecimal> timeToCountMap = new HashMap<>((int) (countPerDayMap.size() / 0.75 + 1));
            countPerDayMap.forEach(timeCountMap ->
                    timeToCountMap.put(sdf.format(timeCountMap.get("time")), (BigDecimal) timeCountMap.get("count")));
            tmpMap.put("timeToCountMap", timeToCountMap);

            tidToResult.put(tmpTask.getTid(), tmpMap);
        });

        return tidToResult;
    }

    @Override
    public Map getTotalInfo(Task task) {
        List<Task> taskList = taskMapper.select(task);

        int cap = (int) (taskList.size() / 0.75 + 1);
        HashMap<Integer, Map> tidToResult = new HashMap<>(cap);

        taskList.forEach(tmpTask -> {
            HashMap<String, Object> tmpMap = new HashMap<>(3);
            tmpMap.put("name", tmpTask.getName());

            Map<String, Object> countAllMap = taskSendSumMapper.selectCountAll(tmpTask);
            if (countAllMap != null) {
                tmpMap.put("count", countAllMap.get("count").toString());
            } else {
                tmpMap.put("count", "0");
            }

            tidToResult.put(tmpTask.getTid(), tmpMap);
        });

        return tidToResult;
    }
}
