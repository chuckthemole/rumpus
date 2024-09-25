package com.rumpus.rumpus.views;

import com.rumpus.common.views.CSSFramework.Bulma.CSS.Layout.BulmaTile;
import com.rumpus.common.views.Component.AbstractTile;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Template.AbstractUserTemplate;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

public class RumpusAdminUserView extends AbstractUserTemplate<RumpusUser, RumpusUserMetaData> {

    private static final String NAME = "RumpusAdminUserView";

    private RumpusAdminUserView(RumpusUser user) {
        super(NAME, user);
    }

    ///////////////////////
    /// Factory Methods ///
    ///////////////////////
    public static RumpusAdminUserView createWithEmptyUser() {
        return new RumpusAdminUserView(RumpusUser.createEmptyUser());
    }
    public static RumpusAdminUserView createWithUser(RumpusUser user) {
        return new RumpusAdminUserView(user);
    }

    @Override
    public AbstractTile initUsername() {
        AbstractTile parentTile = BulmaTile.createParentTile("RumpusUserNameParentTile");
        AbstractTile childTile = BulmaTile.createChildTile("RumpusUsernameChildTile", "User", this.getUser().getUsername());
        parentTile.addChild(childTile);
        return parentTile;
    }

    @Override
    public AbstractTile initEmail() {
        AbstractTile parentTile = BulmaTile.createParentTile("RumpusUserEmailParentTile");
        AbstractTile childTile = BulmaTile.createChildTile("RumpusUserEmailChildTile", "Email", this.getUser().getEmail());
        parentTile.addChild(childTile);
        return parentTile;
    }

    @Override
    public AbstractTile initAuthorities() {
        AbstractTile parentTile = BulmaTile.createParentTile("RumpusUserAuthoritiesParentTile");
        AbstractTile childTile = BulmaTile.createChildTile("RumpusUserAuthoritiesChildTile", "Authorities", "TODO");
        parentTile.addChild(childTile);
        return parentTile;
    }

    @Override
    public AbstractHtmlObject setHead() {
        AbstractTile head = BulmaTile.createAncestorTile("RumpusUserViewHead");
        head.addToAttribute("class", "has-centered-text");
        head.addChild(this.get(AbstractUserTemplate.USERNAME_TILE_KEY));
        head.addChild(this.get(AbstractUserTemplate.EMAIL_TILE_KEY));
        head.addChild(this.get(AbstractUserTemplate.AUTHORITIES_TILE_KEY));
        return head;
    }
    
}
