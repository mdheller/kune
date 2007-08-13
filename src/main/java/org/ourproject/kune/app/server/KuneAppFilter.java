package org.ourproject.kune.app.server;

import org.ourproject.kune.app.server.servlet.Application;
import org.ourproject.kune.app.server.servlet.GWTFilter;
import org.ourproject.kune.app.server.servlet.LifeCycleListener;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.docs.server.KuneDocumentModule;
import org.ourproject.kune.platf.server.KunePlatformModule;
import org.ourproject.kune.platf.server.KuneServiceDefault;
import org.ourproject.kune.platf.server.LoggerMethodInterceptor;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class KuneAppFilter extends GWTFilter {

    @Override
    protected void configure(final Application app) {
	app.use(new KunePlatformModule());
	app.use(new KuneDocumentModule());

	app.use(new AbstractModule() {
	    public void configure() {
		bindInterceptor(Matchers.any(), Matchers.any(), new LoggerMethodInterceptor());
		bindScope(SessionScoped.class, Scopes.SINGLETON);
		bindConstant().annotatedWith(JpaUnit.class).to("development");
		bindConstant().annotatedWith(PropertiesFileName.class).to("kune.properties");
	    }
	});

	app.create("kune", "Kune.html", "gwt/org.ourproject.kune.app.Kune").with(new KuneApplicationListener());
	app.useService("KuneService", KuneServiceDefault.class);
	app.add(new LifeCycleListener() {
	    @Inject
	    ToolRegistry toolRegistry;
	    @Inject
	    DocumentServerTool documentTool;

	    public void start() {
		toolRegistry.register(documentTool);
	    }

	    public void stop() {
	    }
	});
	app.add(new KuneLifeCycleListener());
    }
}
