package com.sqlist.web.mapper;

import com.sqlist.web.domain.Device;
import com.sqlist.web.domain.User;
import com.sqlist.web.util.MyMapper;
import com.sqlist.web.vo.DeviceResponseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SqList
 * @date 2019/4/25 10:31
 * @description
 **/
public interface DeviceMapper extends MyMapper<Device> {

    /**
     * 获取 设备及所属产品
     * @param device
     * @return
     */
    List<DeviceResponseVO> selectWithProduct(Device device);

//    /**
//     * 根据did更新设备的pid
//     * @param device
//     * @param didList
//     */
//    void updatePidMultipleByDid(@Param("device") Device device, @Param("didList") List<Integer> didList);
//
//    /**
//     * 根据pid更新设备pid（主要是用于删除product时）
//     * @param device
//     * @param pidList
//     */
//    void updatePidMultipleByPid(@Param("device") Device device, @Param("pidList") List<Integer> pidList);

    /**
     * 删除某产品下的所有设备
     * @param device
     * @param pidList
     */
    void deleteMultipleByPid(@Param("device") Device device, @Param("pidList") List<Integer> pidList);

    /**
     * 根据did删除多个任务
     * @param user
     * @param didList
     */
    void deleteMultiple(@Param("user") User user, @Param("didList") List<Integer> didList);
}