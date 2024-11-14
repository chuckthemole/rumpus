package com.rumpus.rumpus.views;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaAside;
import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaBreadcrumb;
import com.rumpus.common.views.CSSFramework.Bulma.CommonComponents.BulmaWelcome;
import com.rumpus.common.views.Component.AbstractAside;
import com.rumpus.common.views.Component.AbstractBreadcrumb;
import com.rumpus.common.views.Component.AbstractComponent;
import com.rumpus.common.views.Component.AbstractWelcome;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Template.AbstractPlaceHolderTemplate;
import com.rumpus.common.views.Template.AbstractTemplate;

public class RumpusPlaceHolderView extends AbstractPlaceHolderTemplate {

    private static final String ASIDE_COMPONENT_NAME = "RumpusUserViewAside";
    private static final String BREADCRUMB_COMPONENT_NAME = "RumpusUserViewBreadcrumb";
    private static final String WELCOME_COMPONENT_NAME = "RumpusUserViewWelcome";

    public RumpusPlaceHolderView() {}

    public static RumpusPlaceHolderView create() {
        return new RumpusPlaceHolderView();
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
        return BulmaAside.create(ASIDE_COMPONENT_NAME, stringBuilder.toString());
    }

    @Override
    public AbstractBreadcrumb initBreadcrumb() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bulma");
        sb.append(AbstractComponent.DEFAULT_LINK_DELIMITER);
        sb.append("www.google.com,");
        sb.append("Another Bulma Page");
        sb.append(AbstractComponent.DEFAULT_LINK_DELIMITER);
        sb.append("www.google.com,");
        sb.append("Current Page");
        sb.append(AbstractComponent.DEFAULT_LINK_DELIMITER);
        sb.append("www.google.com,");
        return BulmaBreadcrumb.create(BREADCRUMB_COMPONENT_NAME, sb.toString());
    }

    @Override
    public AbstractWelcome initWelcome() {
        StringBuilder sb = new StringBuilder();
        sb.append(AbstractHtmlObject.HtmlTagType.H1);
        sb.append(AbstractWelcome.WELCOME_COMPONENT_DELIMITER);
        sb.append("Hi, there butthead!");
        sb.append(AbstractWelcome.WELCOME_DEFAULT_DELIMITER);

        sb.append(AbstractHtmlObject.HtmlTagType.H2);
        sb.append(AbstractWelcome.WELCOME_COMPONENT_DELIMITER);
        sb.append("What the hell are you doing?!,");
        return BulmaWelcome.create(WELCOME_COMPONENT_NAME, sb.toString());
    }

    @Override
    public AbstractHtmlObject setHead() {
        AbstractHtmlObject head = AbstractHtmlObject.createEmptyAbstractHtmlObject();
        head.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);


        // BulmaTile tile = BulmaTile.create("RumpusAdminUserViewTile", "HelloWhat?, what the hell are you doing?!");
        // BulmaTile childTile = BulmaTile.createChildTile("Child", "HelloWhat?", "what the hell are you doing?!");
        // BulmaTile parentTile = BulmaTile.createParentTile("Parent");
        // parentTile.addChild(childTile);
        // BulmaTile tile = BulmaTile.createAncestorTile("Ancestor");
        // tile.addChild(parentTile);
        head.addChild(this.get(AbstractTemplate.TEMPLATE_WELCOME));
        head.addChild(this.get(AbstractTemplate.TEMPLATE_BREADCRUMB));
        head.addChild(this.get(AbstractTemplate.TEMPLATE_ASIDE));
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(
            "DEBUG ASIDE: ",
            this.get(AbstractTemplate.TEMPLATE_ASIDE).toString()).toString();
        LOG(log);
        return head;
    }
}
