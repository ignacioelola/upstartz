package io.upstartz.component;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class StaticImage extends WebComponent {
    public StaticImage(final String id, final IModel<String> model) {
        super(id, model);
    }

    public StaticImage(final String id, final String url) {
        super(id, Model.of(url));
    }

    protected void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        tag.put("src", getDefaultModelObjectAsString());
    }
}
