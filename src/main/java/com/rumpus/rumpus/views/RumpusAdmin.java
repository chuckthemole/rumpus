package com.rumpus.rumpus.views;

import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaAside;
import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaBreadcrumb;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Bulma");
        sb.append(AbstractBreadcrumb.LINK_DELIMITER);
        sb.append("www.google.com,");
        sb.append("Another Bulma Page");
        sb.append(AbstractBreadcrumb.LINK_DELIMITER);
        sb.append("www.google.com,");
        sb.append("Current Page");
        return BulmaBreadcrumb.create(sb.toString());
    }

    @Override
    public AbstractWelcome initWelcome() {
        return null;
    }

    @Override
    public AbstractHtmlObject setHead() {
        AbstractHtmlObject head = AbstractHtmlObject.createEmptyAbstractHtmlObject();
        head.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
        head.addChild(this.components.get("breadcrumb"));
        head.addChild(this.components.get("aside"));
        return head;
    }
}
