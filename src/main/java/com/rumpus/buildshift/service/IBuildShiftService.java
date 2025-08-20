package com.rumpus.buildshift.service;

import com.rumpus.common.Service.IService;
import com.rumpus.buildshift.models.BuildShiftModel;

public interface IBuildShiftService<MODEL extends BuildShiftModel<MODEL>> extends IService<MODEL> {
}
