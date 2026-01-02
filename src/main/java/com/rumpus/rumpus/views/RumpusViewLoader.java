package com.rumpus.rumpus.views;

// **
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rumpus.common.util.Pair;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.NavbarItem;
import com.rumpus.common.views.NavbarItem.ItemType;
import com.rumpus.common.views.ResourceManager;
import com.rumpus.common.views.AbstractViews;

/**
 * ViewLoader is responsible for initializing the primary components of views,
 * including Navbar, Footer, Header items, and ResourceManager.
 * <p>
 * All dependencies are injected via Spring, and initialization of
 * instance-dependent
 * components is deferred to a {@link PostConstruct} method to ensure proper
 * injection.
 * </p>
 * 
 * This design eliminates reliance on static blocks for fields that depend on
 * injected beans.
 */
@Component
public class RumpusViewLoader extends AbstractViews {

    /**
     * Injected brand value for the navbar; guaranteed non-null via constructor
     * injection
     */
    private final String RUMPUS_BRAND;

    /** NavbarItem representing the brand logo */
    private NavbarItem navbarBrand;

    /** Footer column definitions */
    private List<Pair<String, List<String>>> footerColumns;

    /** Navbar start and end items */
    private List<NavbarItem> navbarItemsStart;
    private List<NavbarItem> navbarItemsEnd;

    /** Table configuration constants */
    private static final String CSS_FRAMEWORK = "bulma";

    /** View template constants */
    public static final String RUMPUS_ADMIN_TEMPLATE = "RumpusAdminTemplate";
    public static final String RUMPUS_USER_VIEW_TEMPLATE = AbstractViews.CURRENT_USER_TEMPLATE_KEY;

    /** Navbar brand constants */
    private static final String NAVBAR_BRAND_NAME = "RUMPUSBrand";

    // ------------------------------------------------------------------------
    // Constructor with dependency injection
    // ------------------------------------------------------------------------
    /**
     * Constructor using Spring's constructor injection for the navbar brand.
     *
     * @param RUMPUS_BRAND The brand value from the Spring context, qualified by
     *                     "navbarBrand".
     */
    @Autowired
    public RumpusViewLoader(@Qualifier("navbarBrand") String RUMPUS_BRAND) {
        this.RUMPUS_BRAND = RUMPUS_BRAND;
    }

    // ------------------------------------------------------------------------
    // Post-construction initialization
    // ------------------------------------------------------------------------
    /**
     * Initializes all view components that depend on injected beans.
     * <p>
     * This method is called automatically by Spring after dependencies are
     * injected.
     * </p>
     */
    @PostConstruct
    private void initializeViewLoader() {
        // Initialize Navbar brand using injected brand string
        this.navbarBrand = NavbarItem.createNavbarBrandWithRemoteImage(
                NAVBAR_BRAND_NAME,
                "/",
                true,
                this.RUMPUS_BRAND);

        // Initialize Navbar items
        List<NavbarItem> navbarItemsStartDropdown = List.of(
                NavbarItem.create("About", "/", true, ItemType.LINK),
                NavbarItem.create("Jobs", "/", true, ItemType.LINK),
                NavbarItem.create("Contact", "/", true, ItemType.LINK),
                NavbarItem.createDropdownDivider("DropdownDivider1", true),
                NavbarItem.create("Report an issue", "/bug_report", true, ItemType.LINK));

        this.navbarItemsStart = List.of(
                NavbarItem.create("Home", "/", true, ItemType.LINK),
                NavbarItem.create("Documentation", "/", false, ItemType.LINK),
                NavbarItem.createAsDropdown("More", "/", true, navbarItemsStartDropdown));

        List<NavbarItem> navbarItemsEndDropdown = List.of(
                NavbarItem.createDropdownSectionTitle("TEST", true),
                NavbarItem.create("Console", "/notion_console/", true, ItemType.LINK),
                NavbarItem.create("Leaderboard", "/notion_leader", true, ItemType.LINK));

        this.navbarItemsEnd = List.of(
                // NavbarItem.createAsDropdown("Notion", "/", true, navbarItemsEndDropdown),
                NavbarItem.createAsReactComponent(
                        "LoginTrigger",
                        "LoginTrigger",
                        true,
                        Map.of(
                                "mode", "modal",
                                "triggerLabel", "Login",
                                "triggerType", "button",
                                "triggerClassName", "navbar-item button is-primary")),
                NavbarItem.createAsReactComponent(
                        "SignupTrigger",
                        "SignupTrigger",
                        true,
                        Map.of(
                                "mode", "modal",
                                "triggerLabel", "Signup",
                                "triggerType", "button",
                                "triggerClassName", "navbar-item button is-info")),
                // NavbarItem.createAsReactComponent("Login", "LoginModal", true, null),
                // NavbarItem.createAsReactComponent("Signup", "SignupModal", true, Map.of("redirectTo", "/api/user")),
                NavbarItem.createAsReactComponent("UserIcon", "UserIcon", true, null),
                NavbarItem.createAsReactComponent("Admin", "Admin", true, null),
                NavbarItem.createAsReactComponent("Logout", "Logout", true, Map.of("redirectTo", "/")));

        // Initialize footer columns
        this.footerColumns = List.of(
                new Pair<>("Useful", List.of("RUMPUS", "RUMPUS", "RUMPUS")),
                new Pair<>("Support", List.of("Shop", "Rules", "News")),
                new Pair<>("Extras", List.of("Shop", "Rules", "News")));

        super.init();
    }

    // ------------------------------------------------------------------------
    // AbstractViews overrides
    // ------------------------------------------------------------------------
    @Override
    protected int initFooter() {
        super.footer = new Footer();
        for (Pair<String, List<String>> column : this.footerColumns) {
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
        return SUCCESS; // placeholder
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
        this.put(RUMPUS_USER_VIEW_TEMPLATE, RumpusAdminUserView.createWithEmptyUser());
        return SUCCESS;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
