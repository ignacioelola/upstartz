package io.upstartz;

import io.upstartz.page.AcceptStartupCompany;
import io.upstartz.page.LeaderboardPage;
import io.upstartz.page.VoteForStartupPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication {
    @Override
    public Class<? extends WebPage> getHomePage() {
        return VoteForStartupPage.class;
    }

    @Override
    public void init() {
        super.init();
        onInitialize();
    }

    protected void onInitialize() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        mountPage("/leaderboard/${dir}", LeaderboardPage.class);
        mountResource("/add", new ResourceReference("add") {
            @Override
            public IResource getResource() {
                return new AcceptStartupCompany();
            }
        });
    }
}
