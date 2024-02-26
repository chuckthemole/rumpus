package com.rumpus.rumpus.common.Dao;

import com.rumpus.AbstractRumpusTest;

abstract public class AbstractRumpusDaoTest extends AbstractRumpusTest {

    public AbstractRumpusDaoTest(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void setUpClass() {
        this.LOG("AbstractRumpusDaoTest::setUpClass() NOT IMPLEMENTED");
    }

    @Override
    protected void tearDownClass() {
        this.LOG("AbstractRumpusDaoTest::tearDownClass() NOT IMPLEMENTED");
    }
}
