package com.sqlist.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sqlist.web.domain.Device;
import com.sqlist.web.domain.User;
import com.sqlist.web.mapper.DeviceMapper;
import com.sqlist.web.service.DeviceService;
import com.sqlist.web.vo.DeviceVO;
import com.sqlist.web.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SqList
 * @date 2019/4/26 1:34
 * @description
 **/
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public Map<String, Object> list(DeviceVO deviceVO, PageVO pageVO) {
        Device device = new Device();
        device.setUid(deviceVO.getUid());
        device.setPid(deviceVO.getPid());

        HashMap<String, Object> map = new HashMap<>();

        PageHelper.startPage(pageVO.getPage(), pageVO.getLimit());
        List<Device> deviceList = deviceMapper.select(device);

        map.put("total", ((Page)deviceList).getTotal());
        map.put("list", deviceList);

        return map;
    }

    @Override
    public void add(User user, DeviceVO deviceVO) {
        Device device = new Device();
        device.setUid(user.getUid());
        device.setName(deviceVO.getName());
        device.setCreateTime(new Date());

        deviceMapper.insert(device);
    }

    @Override
    public void deleteMultiple(User user, List<Integer> didList) {
        deviceMapper.deleteMultiple(user, didList);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updatePidMultipleByDid(Device device, List<Integer> didList) {
        deviceMapper.updatePidMultipleByDid(device, didList);
    }

    @Override
    public void updatePidMultipleByPid(Device device, List<Integer> pidList) {
        deviceMapper.updatePidMultipleByPid(device, pidList);
    }

    @Override
    public Device detail(Device device) {
        device = deviceMapper.selectByPrimaryKey(device);
        return device;
    }
}
