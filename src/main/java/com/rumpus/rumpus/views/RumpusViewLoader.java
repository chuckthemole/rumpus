package com.rumpus.rumpus.views;

import java.util.ArrayList;
import java.util.List;

import com.rumpus.common.util.Pair;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.ViewLoader;

/**
 * @author Chuck Thomas
 * 
 * Views for Rumpus. Add your view init function to init() just as initFooter().
 */
public class RumpusViewLoader extends ViewLoader {
    
    private static final String NAME = "RumpusViewLoader";

    // Footer Columns
    // Add footers columns here, Pair<title,items>
    private static final Pair<String, List<String>> FOOTER_COLUMN_1;
    private static final Pair<String, List<String>> FOOTER_COLUMN_2;
    private static final Pair<String, List<String>> FOOTER_COLUMN_3;

    // Add more views here...

    static {
        FOOTER_COLUMN_1 = new Pair<>("Useful", new ArrayList<>(List.of("Shop", "Rules", "News")));
        FOOTER_COLUMN_2 = new Pair<>("Support", new ArrayList<>(List.of("Shop", "Rules", "News")));
        FOOTER_COLUMN_3 = new Pair<>("Extras", new ArrayList<>(List.of("Shop", "Rules", "News")));
    }

    public RumpusViewLoader() {
        super(NAME);
        init();
	}

    private int init() {
        initFooter();
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
}
