package com.rumpus.rumpus.service;

import com.rumpus.common.Service.IService;
import com.rumpus.rumpus.models.RumpusModel;

public interface IRumpusService<MODEL extends RumpusModel<MODEL>> extends IService<MODEL> {
}
