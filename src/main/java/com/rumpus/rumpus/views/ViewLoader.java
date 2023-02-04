package com.rumpus.rumpus.views;

import java.util.ArrayList;
import java.util.List;

import com.rumpus.common.RumpusObject;
import com.rumpus.common.util.Pair;

public class ViewLoader extends RumpusObject implements IViewLoader {

    private static final String NAME = "ViewLoader";
    private Footer footer;

	public ViewLoader() {
        super(NAME);
        init();
	}

    private int init() {
        initFooter();
        return SUCCESS;
    }

    private int initFooter() {

        this.footer = new Footer();
        List<Pair<String, List<String>>> columns = new ArrayList<>( // Add footers here, Pair<title,items>
            List.of(
                new Pair<>("Useful", new ArrayList<>(List.of("Shop", "Rules", "News"))),
                new Pair<>("Support", new ArrayList<>(List.of("Shop", "Rules", "News"))),
                new Pair<>("Extras", new ArrayList<>(List.of("Shop", "Rules", "News")))
            )
        );

        for(Pair<String, List<String>> column : columns) {
            this.footer.add(column.getFirst(), column.getSecond());
        }

        return SUCCESS;
    }

    @Override
    public Footer getFooter() {
        return this.footer;
    }

    @Override
    public int setFooter(Footer footer) {
        this.footer = footer;
        return SUCCESS;
    }
}
