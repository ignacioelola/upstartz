package io.upstartz.page;

import io.upstartz.dao.StartupCompanyDAO;
import io.upstartz.model.StartupCompany;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;

public class AcceptStartupCompany extends AbstractResource {
    @SpringBean
    private StartupCompanyDAO dao;

    public AcceptStartupCompany() {
        Injector.get().inject(this);
    }

    @Override
    protected ResourceResponse newResourceResponse(final Attributes attributes) {
        final ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("text/plain");
        resourceResponse.setTextEncoding("utf-8");

        final HttpServletRequest request = (HttpServletRequest) attributes.getRequest().getContainerRequest();
        parseRequest(request);

        resourceResponse.setWriteCallback(new WriteCallback() {
            @Override
            public void writeData(Attributes attributes) throws IOException {
                final OutputStream outputStream = attributes.getResponse().getOutputStream();
                final Writer writer = new OutputStreamWriter(outputStream);
                writer.write("OK");
                writer.close();
            }
        });

        return resourceResponse;
    }

    private void parseRequest(final HttpServletRequest request) {
        dao.save(
                new StartupCompany(
                        getheader(request, "source"),
                        getheader(request, "sourceUrl"),
                        getheader(request, "name"),
                        getheader(request, "companyUrl"),
                        getheader(request, "location"),
                        getheader(request, "logoUrl"),
                        getheader(request, "description")));
    }

    private String getheader(final HttpServletRequest request, final String name) {
        final String parameter = request.getParameter(name);
        if (parameter == null) {
            throw new IllegalArgumentException(
                    "Missing parameter [" + name + "] in request: " + requestParametersToString(request));
        }
        return parameter;
    }

    private String requestParametersToString(final HttpServletRequest req) {
        final StringBuilder sb = new StringBuilder();
        final Enumeration parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            final String name = (String)parameterNames.nextElement();
            sb.append(name).append('=').append(req.getParameter(name));
        }
        return sb.toString();
    }
}