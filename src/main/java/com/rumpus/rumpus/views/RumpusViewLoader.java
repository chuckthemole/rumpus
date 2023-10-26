package com.rumpus.rumpus.views;

import java.util.ArrayList;
import java.util.List;

import com.rumpus.common.util.Pair;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.NavbarItem;
import com.rumpus.common.views.NavbarItem.ItemType;
import com.rumpus.common.views.AbstractViewLoader;

/**
 * @author Chuck Thomas
 * 
 * Views for Rumpus. Add your view init function to init() just as initFooter().
 */
public class RumpusViewLoader extends AbstractViewLoader implements IRumpusViewLoader {
    
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
        navbarBrand = NavbarItem.create(NAVBAR_BRAND_NAME, NAVBAR_BRAND_HREF, true, ItemType.BRAND);
        List<NavbarItem> navbarItemsStartDropdown = new ArrayList<>(
            List.of(
                NavbarItem.create("About", "/", false, ItemType.LINK),
                NavbarItem.create("Jobs", "/", false, ItemType.LINK),
                NavbarItem.create("Contact", "/", false, ItemType.LINK),
                NavbarItem.create("Report an issue", "/", false, ItemType.LINK)
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
                NavbarItem.createAsReactComponent("Signup", "SignupModal", true)
            )
        );
    }

    public RumpusViewLoader() {
        super(NAME);
        init();
	}

    private int init() {
        initFooter();
        initHeader();
        initUserTable();
        return SUCCESS;
    }

    private int initFooter() {
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

    private int initHeader() {
        super.header = Header.create(navbarBrand, navbarItemsStart, navbarItemsEnd);

        return SUCCESS;
    }

    private int initUserTable() {
        super.userTable = new ComponentUserTable(CSS_FRAMEWORK);
        return SUCCESS;
    }
}
