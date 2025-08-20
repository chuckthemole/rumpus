package com.rumpus.buildshift.views;

import com.rumpus.common.views.CSSFramework.Bulma.CSS.Layout.BulmaTile;
import com.rumpus.common.views.Component.AbstractTile;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Template.AbstractUserTemplate;

import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

public class AdminUserView extends AbstractUserTemplate<User, UserMetaData> {

    private AdminUserView(User user) {
        super(user);
    }

    ///////////////////////
    /// Factory Methods ///
    ///////////////////////
    public static AdminUserView createWithEmptyUser() {
        return new AdminUserView(User.createEmptyUser());
    }
    public static AdminUserView createWithUser(User user) {
        return new AdminUserView(user);
    }

    @Override
    public AbstractTile initUsername() {
        AbstractTile parentTile = BulmaTile.createParentTile("UserNameParentTile");
        AbstractTile childTile = BulmaTile.createChildTile("UsernameChildTile", "User", this.getUser().getUsername());
        parentTile.addChild(childTile);
        return parentTile;
    }

    @Override
    public AbstractTile initEmail() {
        AbstractTile parentTile = BulmaTile.createParentTile("UserEmailParentTile");
        AbstractTile childTile = BulmaTile.createChildTile("UserEmailChildTile", "Email", this.getUser().getEmail());
        parentTile.addChild(childTile);
        return parentTile;
    }

    @Override
    public AbstractTile initAuthorities() {
        AbstractTile parentTile = BulmaTile.createParentTile("UserAuthoritiesParentTile");
        AbstractTile childTile = BulmaTile.createChildTile("UserAuthoritiesChildTile", "Authorities", "TODO");
        parentTile.addChild(childTile);
        return parentTile;
    }

    @Override
    public AbstractHtmlObject setHead() {
        AbstractTile head = BulmaTile.createAncestorTile("UserViewHead");
        head.addToAttribute("class", "has-centered-text");
        head.addChild(this.get(AbstractUserTemplate.USERNAME_TILE_KEY));
        head.addChild(this.get(AbstractUserTemplate.EMAIL_TILE_KEY));
        head.addChild(this.get(AbstractUserTemplate.AUTHORITIES_TILE_KEY));
        return head;
    }
    
}
