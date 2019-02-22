package com.cxyz.check.dao;

import org.apache.ibatis.annotations.Param;

public interface RoomDao {

    /**
     * 获取房间名
     * @param id 房间id
     * @return
     */
    String getRoomName(@Param("id")Integer id);


}
