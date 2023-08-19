package com.rumpus.rumpus.common.Dao;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.rumpus.rumpus.data.RumpusUserDao;

public class DaoApiDBTest extends CommonDaoTest {

    @MockBean private RumpusUserDao userDao;

    
}
