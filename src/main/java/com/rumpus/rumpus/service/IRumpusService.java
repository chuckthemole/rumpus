package com.rumpus.rumpus.service;

import java.util.List;

import com.rumpus.common.IService;
import com.rumpus.rumpus.models.RumpusModel;

public interface IRumpusService<T extends RumpusModel<T>> extends IService<T> {
}
