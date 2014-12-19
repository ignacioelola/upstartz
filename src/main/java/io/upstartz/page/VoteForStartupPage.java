package io.upstartz.page;

import io.upstartz.component.StaticImage;
import io.upstartz.dao.StartupCompanyDAO;
import io.upstartz.model.StartupCompany;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Presents a random company with voting options.
 */
public class VoteForStartupPage extends WebPage {
	private static final long serialVersionUID = 1L;

    @SpringBean
    private StartupCompanyDAO dao;

	public VoteForStartupPage(final PageParameters pp) {
		super(pp);

        final StartupCompany company = dao.loadRandom();
        if (company == null) {
            throw new IllegalStateException("No startups exist in the database.");
        }

        add(new HeaderPanel("header", null));
        add(new Label("name", company.getName()));
        add(new StaticImage("logoUrl", company.getLogoUrl()));
        add(new Label("description", company.getDescription()));

        add(new AjaxLink("upvoteLink") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                dao.upvote(company.getId());
                throw new RestartResponseException(getApplication().getHomePage());
            }
        });

        add(new AjaxLink("downvoteLink") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                dao.downvote(company.getId());
                throw new RestartResponseException(getApplication().getHomePage());
            }
        });
    }
}
