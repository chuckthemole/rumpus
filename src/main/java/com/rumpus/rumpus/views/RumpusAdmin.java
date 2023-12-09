package com.rumpus.rumpus.views;

import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaAside;
import com.rumpus.common.views.Component.AbstractAside;
import com.rumpus.common.views.Component.AbstractBreadcrumb;
import com.rumpus.common.views.Component.AbstractWelcome;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Template.AbstractAdmin;

public class RumpusAdmin extends AbstractAdmin {

    private static final String NAME = "RumpusAdmin";

    private RumpusAdmin() {
        super(NAME);
    }

    public static RumpusAdmin create() {
        return new RumpusAdmin();
    }

    @Override
    public AbstractAside initAside() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("group1, group1-item1, group1-item2,");
        stringBuilder.append(AbstractAside.START_ASIDE_CHILD_LIST);
        stringBuilder.append(",groupChildList, child-item1, child-item2,");
        stringBuilder.append(AbstractAside.END_ASIDE_CHILD_LIST);
        stringBuilder.append(",");
        stringBuilder.append(AbstractAside.GROUP_DELIMITER);
        stringBuilder.append("group2, group2-item1, group2-item2");
        return BulmaAside.create(stringBuilder.toString());
    }

    @Override
    public AbstractBreadcrumb initBreadcrumb() {
        return null;
    }

    @Override
    public AbstractWelcome initWelcome() {
        return null;
    }

    @Override
    public AbstractHtmlObject setHead() {
        return this.components.get("aside");
    }
}
