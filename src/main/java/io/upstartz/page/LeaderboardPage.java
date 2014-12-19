package io.upstartz.page;

import io.upstartz.component.StaticImage;
import io.upstartz.dao.StartupCompanyDAO;
import io.upstartz.model.StartupCompany;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.util.List;

public class LeaderboardPage extends WebPage {
    @SpringBean
    private StartupCompanyDAO dao;

    public LeaderboardPage(final PageParameters pp) {
        super(pp);
        final Direction selectedDirection = parseDirection(pp.get("dir"));

        add(new HeaderPanel("header", selectedDirection));

        final List<StartupCompany> scList = list(selectedDirection, 10);
        add(new ListView<StartupCompany>("list", scList) {
            @Override
            protected void populateItem(final ListItem<StartupCompany> item) {
                final StartupCompany sc = item.getModelObject();
                final ExternalLink link = new ExternalLink("link", sc.getCompanyUrl());
                item.add(link);
                link.add(new Label("name", sc.getName()));
                item.add(new Label("upvotes", sc.getUpvotes()));
                item.add(new Label("downvotes", sc.getDownvotes()));
                item.add(new StaticImage("logo", sc.getLogoUrl()));
                item.add(new Label("description", sc.getDescription()));
            }
        });
    }

    private Direction parseDirection(final StringValue arg) {
        try {
            return Direction.valueOf(arg.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Direction.ON_FIRE;
        }
    }

    private List<StartupCompany> list(final Direction dir, final int limit) {
        switch (dir) {
            case ICE_COLD:
                return dao.listTopDownvotes(limit);
            default:
                return dao.listTopUpvotes(limit);
        }
    }
}
