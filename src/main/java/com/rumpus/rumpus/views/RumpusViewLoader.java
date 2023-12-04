package com.rumpus.rumpus.views;

import java.util.ArrayList;
import java.util.List;

import com.rumpus.common.util.Pair;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.NavbarItem;
import com.rumpus.common.views.ResourceManager;
import com.rumpus.common.views.SectionManager;
import com.rumpus.common.views.CSSFramework.Bulma.Element.Block;
import com.rumpus.common.views.CSSFramework.Bulma.Element.Box;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.NavbarItem.ItemType;
import com.rumpus.common.views.AbstractViews;

/**
 * @author Chuck Thomas
 * 
 * Views for Rumpus. Add your view init function to init() just as initFooter().
 */
public class RumpusViewLoader extends AbstractViews {
    
    private static final String NAME = "RumpusViewLoader";

    // Footer Columns
    // Add footers columns here, Pair<title,items>
    private static final Pair<String, List<String>> FOOTER_COLUMN_1;
    private static final Pair<String, List<String>> FOOTER_COLUMN_2;
    private static final Pair<String, List<String>> FOOTER_COLUMN_3;

    // Header Items
    private static final String NAVBAR_BRAND_NAME = "RumpusBrand";
    private static final String NAVBAR_BRAND_HREF = "https://bulma.io/images/bulma-logo.png";
    private static final NavbarItem navbarBrand;
    private static final List<NavbarItem> navbarItemsStart;
    private static final List<NavbarItem> navbarItemsEnd;

    // Tables
    private static final String CSS_FRAMEWORK = "bulma";

    // Add more views here...

    // TODO - can we get rid of ArrayList here??
    static {

        // Footer
        FOOTER_COLUMN_1 = new Pair<>("Useful", new ArrayList<>(List.of("Shop", "Rules", "News")));
        FOOTER_COLUMN_2 = new Pair<>("Support", new ArrayList<>(List.of("Shop", "Rules", "News")));
        FOOTER_COLUMN_3 = new Pair<>("Extras", new ArrayList<>(List.of("Shop", "Rules", "News")));

        // Header
        navbarBrand = NavbarItem.createWithImage(NAVBAR_BRAND_NAME, "/", true, NAVBAR_BRAND_HREF);
        List<NavbarItem> navbarItemsStartDropdown = new ArrayList<>(
            List.of(
                NavbarItem.create("About", "/", true, ItemType.LINK),
                NavbarItem.create("Jobs", "/", true, ItemType.LINK),
                NavbarItem.create("Contact", "/", true, ItemType.LINK),
                NavbarItem.createDropdownDivider("DropdownDivider1", true),
                NavbarItem.create("Report an issue", "/", true, ItemType.LINK)
            )
        );
        navbarItemsStart = new ArrayList<>(
            List.of(
                NavbarItem.create("Home", "/", true, ItemType.LINK),
                NavbarItem.create("Documentation", "/", false, ItemType.LINK),
                NavbarItem.createAsDropdown("More", "/", true, navbarItemsStartDropdown)
            )
        );
        navbarItemsEnd = new ArrayList<>(
            List.of(
                NavbarItem.createAsReactComponent("Login", "LoginModal", true),
                NavbarItem.createAsReactComponent("Signup", "SignupModal", true),
                NavbarItem.createAsReactComponent("UserIcon", "UserIcon", true),
                NavbarItem.createAsReactComponent("Admin", "Admin", true),
                NavbarItem.createAsReactComponent("Logout", "Logout", true)
            )
        );
    }

    private RumpusViewLoader() {
        super(NAME);
        super.init();
	}

    public static RumpusViewLoader create() {
        return new RumpusViewLoader();
    }

    @Override
    protected int initFooter() {
        super.footer = new Footer();
        List<Pair<String, List<String>>> columns = new ArrayList<>(
            List.of(
                FOOTER_COLUMN_1,
                FOOTER_COLUMN_2,
                FOOTER_COLUMN_3
            )
        );

        for(Pair<String, List<String>> column : columns) {
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
    protected int initSections() {
        this.sectionManager = SectionManager.createEmptyManager();
        AbstractHtmlObject parent = Block.createWithNoBody().addChild(Box.createWithBody("Test Section"));
        parent.addChild(Box.createWithBody("Test Section 2"));
        this.sectionManager.addSection("TestSection", parent);
        return SUCCESS;
    }
}
