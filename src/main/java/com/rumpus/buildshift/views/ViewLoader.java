package com.rumpus.buildshift.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rumpus.common.util.Pair;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.NavbarItem;
import com.rumpus.common.views.ResourceManager;
import com.rumpus.common.views.NavbarItem.ItemType;
import com.rumpus.common.views.AbstractViews;

/**
 * @author Chuck Thomas
 */
public class ViewLoader extends AbstractViews {

    public static final String BS_ADMIN_TEMPLATE = "BuildShiftAdminTemplate";
    public static final String BS_USER_VIEW_TEMPLATE = AbstractViews.CURRENT_USER_TEMPLATE_KEY;

    // Footer Columns
    // Add footers columns here, Pair<title,items>
    private static final Pair<String, List<String>> FOOTER_COLUMN_1;
    private static final Pair<String, List<String>> FOOTER_COLUMN_2;
    private static final Pair<String, List<String>> FOOTER_COLUMN_3;

    // Header Items
    private static final String NAVBAR_BRAND_NAME = "BSBrand";
    private static final String NAVBAR_BRAND_HREF = "https://bulma.io/images/bulma-logo.png";
    private static final NavbarItem navbarBrand;
    private static final List<NavbarItem> navbarItemsStart;
    private static final List<NavbarItem> navbarItemsEnd;

    private static final String BUILDSHIFT_BRAND = "/images/buildshift_brand.png";

    // Tables
    private static final String CSS_FRAMEWORK = "bulma";

    // Add more views here...

    // TODO - can we get rid of ArrayList here??
    static {

        // Footer
        FOOTER_COLUMN_1 = new Pair<>("Useful", new ArrayList<>(List.of("BS", "BS", "BS")));
        FOOTER_COLUMN_2 = new Pair<>("Support", new ArrayList<>(List.of("Shop", "Rules", "News")));
        FOOTER_COLUMN_3 = new Pair<>("Extras", new ArrayList<>(List.of("Shop", "Rules", "News")));

        // Header
        navbarBrand = NavbarItem.createNavbarBrandWithLocalImage(NAVBAR_BRAND_NAME, "/", true,
                ViewLoader.BUILDSHIFT_BRAND);
        // navbarBrand =
        // NavbarItem.createNavbarBrandWithAwsS3CloudImage(NAVBAR_BRAND_NAME, "/", true,
        // "static/default_brand.PNG");
        List<NavbarItem> navbarItemsStartDropdown = new ArrayList<>(
                List.of(
                        NavbarItem.create("About", "/", true, ItemType.LINK),
                        NavbarItem.create("Jobs", "/", true, ItemType.LINK),
                        NavbarItem.create("Contact", "/", true, ItemType.LINK),
                        NavbarItem.createDropdownDivider("DropdownDivider1", true),
                        NavbarItem.create("Report an issue", "/", true, ItemType.LINK)));
        navbarItemsStart = new ArrayList<>(
                List.of(
                        NavbarItem.create("Home", "/", true, ItemType.LINK),
                        NavbarItem.create("Documentation", "/", false, ItemType.LINK),
                        NavbarItem.createAsDropdown("More", "/", true, navbarItemsStartDropdown)));
        List<NavbarItem> navbarItemsEndDropdown = new ArrayList<>(
                List.of(
                        NavbarItem.create("Console", "/notion_console/", true, ItemType.LINK),
                        NavbarItem.create("Leaderboard", "/notion_leader", true, ItemType.LINK)));
        navbarItemsEnd = new ArrayList<>(
                List.of(
                        NavbarItem.createAsDropdown("Notion", "/", true, navbarItemsEndDropdown),
                        NavbarItem.createAsReactComponent("Login", "LoginModal", true, null),
                        NavbarItem.createAsReactComponent("Signup", "SignupModal", true,
                                Map.of("redirectTo", "/api/user")),
                        NavbarItem.createAsReactComponent("UserIcon", "UserIcon", true, null),
                        NavbarItem.createAsReactComponent("Admin", "Admin", true, null),
                        NavbarItem.createAsReactComponent("Logout", "Logout", true,
                                Map.of("redirectTo", "/"))));
    }

    private ViewLoader() {
    }

    public static ViewLoader create() {
        return new ViewLoader();
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
    protected int initHeader() {
        super.header = Header.create(navbarBrand, navbarItemsStart, navbarItemsEnd);

        return SUCCESS;
    }

    @Override
    protected int initBody() {
        // TODO
        return SUCCESS;
    }

    @Override
    protected int initUserTable() {
        super.userTable = new ComponentUserTable(CSS_FRAMEWORK);
        return SUCCESS;
    }

    @Override
    protected int initResourceManager() {
        this.resourceManager = ResourceManager.createEmptyManager();
        this.resourceManager.addResource(
                "NavbarBrand",
                com.rumpus.common.views.Resource.ResourceType.IMAGE,
                com.rumpus.common.views.Resource.StorageType.LOCAL,
                "https://bulma.io/images/bulma-logo.png");
        return SUCCESS;
    }

    @Override
    protected int initTemplates() {
        // this.put(BS_ADMIN_TEMPLATE, RumpusAdmin.create());
        this.put(BS_USER_VIEW_TEMPLATE, AdminUserView.createWithEmptyUser());
        return SUCCESS;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
