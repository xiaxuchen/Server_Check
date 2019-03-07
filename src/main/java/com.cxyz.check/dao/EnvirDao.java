package com.cxyz.check.dao;

import com.cxyz.check.entity.College;
import com.cxyz.check.entity.Grade;
import com.cxyz.check.entity.Term;
import com.cxyz.check.entity.Times;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.PowerType;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface EnvirDao {

    /**
     * 通过班级id获取添加user时需要的信息
     * @param id 班级id
     * @return 包含学院名称，学院id和班级名称的grade对象
     */
    Grade getUserNeedById(@Param("id") int id);

    /**
     * 检查是否已创建学期
     * @param school 学校id
     * @param date 日期
     * @param num 学期
     */
    int checkTerm(@Param("school") int school,@Param("date") String date,@Param("num")int num);

    /**
     * 添加学期信息
     * @param term 学期
     */
    void addTerm(@Param("term") Term term);

    /**
     * 添加学期时间表
     * @param times 时间
     * @param termId 学期id
     */
    void addTimes(@Param("times") List<Times> times, @Param("termId")int termId);

    /**
     * 获取学校当前学期
     * @param schoolId 学校id
     * @return
     */
    Term getCurrentTerm(@Param("schoolId")Integer schoolId);

    int addManager(@Param("user") User user,@Param("type") ManagerType type);

    enum ManagerType{
        schoolManager(PowerType.TEA_SCHOOL),collegeManager(PowerType.TEA_COLLEGE);

        private int power;

        ManagerType(int power) {
            this.power = power;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }
    }
}
