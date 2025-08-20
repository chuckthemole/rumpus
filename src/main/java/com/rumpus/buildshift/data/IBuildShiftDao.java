package com.rumpus.buildshift.data;

import com.rumpus.common.Dao.IDao;
import com.rumpus.buildshift.models.BuildShiftModel;

public interface IBuildShiftDao<MODEL extends BuildShiftModel<MODEL>> extends IDao<MODEL> {
}