package io.upstartz.page;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Arrays;

public class HeaderPanel extends Panel {
    public HeaderPanel(final String id, final Direction selectedDirection) {
        super(id);

        add(new ListView<Direction>("nav", Arrays.asList(Direction.values())) {
            @Override
            protected void populateItem(final ListItem<Direction> item) {
                final PageParameters parameters = new PageParameters();
                final Direction direction = item.getModelObject();
                parameters.add("dir", direction.name().toLowerCase());
                final BookmarkablePageLink<LeaderboardPage> link =
                        new BookmarkablePageLink<LeaderboardPage>(
                                "link",
                                LeaderboardPage.class,
                                parameters);
                if (direction == selectedDirection) {
                    link.add(new AttributeAppender("class", Model.of("selected"), " "));
                }
                link.add(new AttributeAppender("class", Model.of(direction.name()), " "));
                item.add(link);

                link.add(new Label("label", getString(direction.name())));
            }
        });
    }
}
