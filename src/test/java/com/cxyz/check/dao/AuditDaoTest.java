package com.cxyz.check.dao;

import com.cxyz.check.entity.Audit;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.PowerType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class AuditDaoTest {

    @Autowired
    private AuditDao dao;

    @Test
    public void addAudits() {
    }

    @Test
    public void auditVac() {
        Audit audit = new Audit();
        audit.setId(14);
        audit.setInfo(null);
        audit.setState(1);
        User checker = new User();
        checker.setPower(PowerType.TEA_SECRETARY);
        audit.setChecker(checker);
        System.out.println(dao.auditVac(audit));
    }

    @Test
    public void clearOther()
    {
        dao.clearOther(8,50);
    }

    @Test
    public void getAudits() {
        System.out.println(dao.getAudits(14,"16478040"));
    }
}