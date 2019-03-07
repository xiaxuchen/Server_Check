package com.cxyz.check.dao;


import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.School;
import com.cxyz.check.util.collections.List;

import org.apache.ibatis.annotations.Param;

public interface SchoolDao {

    /**
     * 获取当前学期id
     * @param schoolId 学校id
     * @return 当前学期
     */
    Integer getCurrentTermId(@Param("schoolId")int schoolId);

    /**
     * 创建学校
     * @param school 需要有name属性，将生成自增id
     * @return
     */
    int addSchool(@Param("school") School school);

    /**
     * 更新学校信息,若name、term、manager为null则表示相应字段不修改
     * name非null,若manager为空，则直接school.setManager(new User),
     * 若term置空school.setTerm(new Term());
     * @param school
     * @return
     */
    int updateSchool(@Param("school") School school);

    /**
     * 删除学校
     * @param id 学校id
     * @return
     */
    int deleteSchool(@Param("id") int id);

    /**
     * 获取全部学校
     * @param field 排序字段
     * @param isAsc 是否正序
     * @return
     */
    List<School> getSchools(@Param("field")String field,@Param("isAsc")boolean isAsc);

    /**
     * 管理员登录
     * @param id 管理员id
     * @return
     */
    School getManager(@Param("id") String id);
}
