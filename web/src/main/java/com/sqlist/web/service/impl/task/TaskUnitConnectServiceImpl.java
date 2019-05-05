package com.sqlist.web.service.impl.task;

import com.sqlist.web.domain.TaskUnitConnect;
import com.sqlist.web.mapper.TaskUnitConnectMapper;
import com.sqlist.web.service.task.TaskService;
import com.sqlist.web.service.task.TaskUnitConnectService;
import com.sqlist.web.vo.TaskUnitConnectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author SqList
 * @date 2019/4/23 21:40
 * @description
 **/
@Slf4j
@Service
public class TaskUnitConnectServiceImpl implements TaskUnitConnectService {

    @Autowired
    private TaskUnitConnectMapper taskUnitConnectMapper;

    @Autowired
    private TaskService taskService;

    @Override
    public List<TaskUnitConnect> list(Integer tid) {
        TaskUnitConnect taskUnitConnect = new TaskUnitConnect();
        taskUnitConnect.setTid(tid);
        return taskUnitConnectMapper.select(taskUnitConnect);
    }

    @Override
    public void add(TaskUnitConnectVO taskUnitConnectVO) {
        TaskUnitConnect taskUnitConnect = new TaskUnitConnect();
        taskUnitConnect.setSourceTuid(taskUnitConnectVO.getSourceTuid());
        taskUnitConnect.setTargetTuid(taskUnitConnectVO.getTargetTuid());
        taskUnitConnect.setTid(taskUnitConnectVO.getTid());

        if (taskUnitConnectMapper.selectCount(taskUnitConnect) == 0) {
            taskUnitConnect.setCreateTime(new Date());
            try {
                taskUnitConnectMapper.insert(taskUnitConnect);
            } catch (DataIntegrityViolationException e) {
                log.error("new connect already exist");
            }
        }

        // 更新task的updateTime
        taskService.updateUpdateTime(taskUnitConnectVO.getTid());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void delete(TaskUnitConnectVO taskUnitConnectVO) {
        TaskUnitConnect taskUnitConnect = new TaskUnitConnect();
        taskUnitConnect.setSourceTuid(taskUnitConnectVO.getSourceTuid());
        taskUnitConnect.setTargetTuid(taskUnitConnectVO.getTargetTuid());
        taskUnitConnect.setTid(taskUnitConnectVO.getTid());

        taskUnitConnectMapper.delete(taskUnitConnect);

        // 更新task的updateTime
        taskService.updateUpdateTime(taskUnitConnectVO.getTid());
    }

    @Override
    public void taskUnitDelete(TaskUnitConnect taskUnitConnect) {
        taskUnitConnectMapper.taskUnitDelete(taskUnitConnect);
    }

    @Override
    public void deleteMultiple(List<Integer> deleteTidList) {
        taskUnitConnectMapper.deleteMultiple(deleteTidList);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void update(TaskUnitConnectVO taskUnitConnectVO) {
        TaskUnitConnect newConnect = new TaskUnitConnect();
        newConnect.setSourceTuid(taskUnitConnectVO.getSourceTuid());
        newConnect.setTargetTuid(taskUnitConnectVO.getTargetTuid());
        newConnect.setTid(taskUnitConnectVO.getTid());

        TaskUnitConnect oldConnect = new TaskUnitConnect();
        oldConnect.setSourceTuid(taskUnitConnectVO.getOldSourceTuid());
        oldConnect.setTargetTuid(taskUnitConnectVO.getOldTargetTuid());
        oldConnect.setTid(taskUnitConnectVO.getTid());

        if (taskUnitConnectMapper.selectCount(newConnect) != 0) {
            // 重新连成的连接已经存在在数据库中（connect 触发导致插入的）
            // 删除旧连接
            log.info("new connect already exist, deleteMultiple old connect");
            log.debug("new connect: {} already exist, deleteMultiple old connect: {}", newConnect, oldConnect);
            taskUnitConnectMapper.delete(oldConnect);
        } else {
            // 重新连成的连接还未存在在数据库中
            // 更新旧连接

            try {
                log.info("new connect is not exist, updateDis old connect");
                log.debug("new connect: {} is not exist, updateDis old connect: {}", newConnect, oldConnect);
                TaskUnitConnect updateOldConnect = taskUnitConnectMapper.selectOne(oldConnect);
                updateOldConnect.setSourceTuid(taskUnitConnectVO.getSourceTuid());
                updateOldConnect.setTargetTuid(taskUnitConnectVO.getTargetTuid());
                taskUnitConnectMapper.updateByPrimaryKeySelective(updateOldConnect);
            } catch (DataIntegrityViolationException e) {
                log.error("new connect insert during select old connect, deleteMultiple old connect");
                taskUnitConnectMapper.delete(oldConnect);
            }
        }

        // 更新task的updateTime
        taskService.updateUpdateTime(taskUnitConnectVO.getTid());
    }

    @Override
    public Integer count(Integer tid) {
        TaskUnitConnect taskUnitConnect = new TaskUnitConnect();
        taskUnitConnect.setTid(tid);
        return taskUnitConnectMapper.selectCount(taskUnitConnect);
    }
}
