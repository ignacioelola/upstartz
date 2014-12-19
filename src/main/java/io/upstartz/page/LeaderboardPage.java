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

    public LeaderboardPage(final PageParameters pp) {
        super(pp);

        add(new BookmarkablePageLink<VoteForStartupPage>("voteLink", VoteForStartupPage.class));

        final List<StartupCompany> scList = list(pp.get("dir"), 10);
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

    private List<StartupCompany> list(final StringValue dir, final int limit) {
        if (dir.isEmpty() || !"down".equals(dir.toString())) {
            return dao.listTopUpvotes(limit);
        }
        return dao.listTopDownvotes(limit);
    }
}
