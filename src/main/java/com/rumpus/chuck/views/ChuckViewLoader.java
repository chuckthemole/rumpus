package com.rumpus.chuck.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rumpus.common.util.Pair;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.NavbarItem;
import com.rumpus.common.views.NavbarItem.ItemType;
import com.rumpus.common.views.ResourceManager;
import com.rumpus.common.views.CSSFramework.Bulma.CSS.Element.Block;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.AbstractViews;

/**
 * @author Chuck Thomas
 * 
 *         Views for Rumpus. Add your view init function to init() just as
 *         initFooter().
 */
public class ChuckViewLoader extends AbstractViews {

    private static final String NAVBAR_BRAND_HREF = "https://bulma.io/images/bulma-logo.png";

    // Footer Columns
    // Add footers columns here, Pair<title,items>
    private static final Pair<String, List<String>> FOOTER_COLUMN_1;
    private static final Pair<String, List<String>> FOOTER_COLUMN_2;
    private static final Pair<String, List<String>> FOOTER_COLUMN_3;

    // Tables
    private static final String CSS_FRAMEWORK = "bulma";

    // Add more views here...

    static {
        FOOTER_COLUMN_1 = new Pair<>("Useful", new ArrayList<>(List.of("Chuck", "Chuck", "Chuck")));
        FOOTER_COLUMN_2 = new Pair<>("Support", new ArrayList<>(List.of("Shop", "Rules", "News")));
        FOOTER_COLUMN_3 = new Pair<>("Extras", new ArrayList<>(List.of("Shop", "Rules", "News")));
    }

    private ChuckViewLoader() {
        init();
    }

    public static ChuckViewLoader create() {
        return new ChuckViewLoader();
    }

    @Override
    protected int initFooter() {
        super.footer = new Footer();
        List<Pair<String, List<String>>> columns = new ArrayList<>(
                List.of(
                        FOOTER_COLUMN_1,
                        FOOTER_COLUMN_2,
                        FOOTER_COLUMN_3));

        for (Pair<String, List<String>> column : columns) {
            this.footer.add(column.getFirst(), column.getSecond());
        }

        return SUCCESS;
    }

    @Override
    protected int initUserTable() {
        super.userTable = new ComponentUserTable(CSS_FRAMEWORK);
        return SUCCESS;
    }

    @Override
    protected int initHeader() {
        // Header
        NavbarItem navbarBrand = NavbarItem.createNavbarBrandWithLocalImage("ChuckBrand", "/", true, NAVBAR_BRAND_HREF);
        List<NavbarItem> navbarItemsStartDropdown = new ArrayList<>(
                List.of(
                        NavbarItem.create("About", "/", true, ItemType.LINK),
                        NavbarItem.create("Jobs", "/", true, ItemType.LINK),
                        NavbarItem.create("Contact", "/", true, ItemType.LINK),
                        NavbarItem.createDropdownDivider("DropdownDivider1", true),
                        NavbarItem.create("Report an issue", "/", true, ItemType.LINK)));
        List<NavbarItem> navbarItemsStart = new ArrayList<>(
                List.of(
                        NavbarItem.create("Home", "/", true, ItemType.LINK),
                        NavbarItem.create("Documentation", "/", false, ItemType.LINK),
                        NavbarItem.createAsDropdown("More", "/", true, navbarItemsStartDropdown)));
        List<NavbarItem> navbarItemsEnd = new ArrayList<>(
                List.of(
                        NavbarItem.createAsReactComponent("Login", "LoginModal", true, null),
                        NavbarItem.createAsReactComponent("Signup", "SignupModal", true,
                                Map.of("redirectTo", "/api/user")),
                        NavbarItem.createAsReactComponent("UserIcon", "UserIcon", true, null),
                        NavbarItem.createAsReactComponent("Admin", "Admin", true, null),
                        NavbarItem.createAsReactComponent("Logout", "Logout", true,
                                Map.of("redirectTo", "/api/user"))));
        super.header = Header.create(navbarBrand, navbarItemsStart, navbarItemsEnd);
        return SUCCESS;
    }

    @Override
    protected int initBody() {
        super.landingPageBody = AbstractHtmlObject.createEmptyAbstractHtmlObject()
                .addChild(Block.createWithBody("Test this out!"));
        return SUCCESS;
    }

    @Override
    protected int initResourceManager() {
        this.resourceManager = ResourceManager.createEmptyManager();
        this.resourceManager.addResource(
                "NavbarBrandChuck",
                com.rumpus.common.views.Resource.ResourceType.IMAGE,
                com.rumpus.common.views.Resource.StorageType.LOCAL,
                "https://bulma.io/images/bulma-logo.png");
        return SUCCESS;
    }

    @Override
    protected int initTemplates() {
        // TODO
        return SUCCESS;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
