package com.rumpus.rumpus.views;

import com.rumpus.common.IRumpusObject;

public interface IViewLoader extends IRumpusObject {
    public Footer getFooter();
    public int setFooter(Footer f);
}
