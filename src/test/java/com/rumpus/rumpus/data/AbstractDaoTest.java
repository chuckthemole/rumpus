package com.rumpus.rumpus.data;

import com.rumpus.AbstractRumpusTest;
import com.rumpus.common.Model.AbstractModel;

abstract public class AbstractDaoTest<MODEL extends AbstractModel<MODEL, ?>> extends AbstractRumpusTest {

    public AbstractDaoTest(Class<?> clazz) {
        super(clazz);
    }
}
