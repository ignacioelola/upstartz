package io.upstartz.page;

import io.upstartz.dao.StartupCompanyDAO;
import io.upstartz.model.StartupCompany;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.util.List;

public class LeaderboardPage extends WebPage {
    @SpringBean
    private StartupCompanyDAO dao;

    public enum Direction {
        UP,
        DOWN
    }

    public LeaderboardPage(final PageParameters pp) {
        super(pp);
        final Direction direction = parseDirection(pp.get("dir"));

        add(new Label("title", getTitle(direction)));

        add(new BookmarkablePageLink<LeaderboardPage>("leaderboardUpvoteLink", LeaderboardPage.class));
        final PageParameters leaderboardDownvoteLinkParameters = new PageParameters();
        leaderboardDownvoteLinkParameters.add("dir", Direction.DOWN);
        add(new BookmarkablePageLink<LeaderboardPage>("leaderboardDownvoteLink", LeaderboardPage.class, leaderboardDownvoteLinkParameters));

        add(new BookmarkablePageLink<VoteForStartupPage>("voteLink", VoteForStartupPage.class));

        final List<StartupCompany> scList = list(direction, 10);
        add(new ListView<StartupCompany>("list", scList) {
            @Override
            protected void populateItem(final ListItem<StartupCompany> item) {
                final StartupCompany sc = item.getModelObject();
                item.add(new Label("upvotes", sc.getUpvotes()));
                item.add(new Label("downvotes", sc.getDownvotes()));
                item.add(new Label("name", sc.getName()));
            }
        });
    }

    private String getTitle(final Direction direction) {
        switch (direction) {
            case DOWN:
                return "Ice Cold Upstartz";
            default:
                return "Upstartz on Fire!";
        }
    }

    private Direction parseDirection(final StringValue arg) {
        try {
            return Direction.valueOf(arg.toString());
        } catch (IllegalArgumentException e) {
            return Direction.UP;
        }
    }

    private List<StartupCompany> list(final Direction dir, final int limit) {
        switch (dir) {
            case DOWN:
                return dao.listTopDownvotes(limit);
            default:
                return dao.listTopUpvotes(limit);
        }
    }
}
