package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

public class Services {
    public final Application app;
    public final Session session;
    public final StateManager stateManager;
    public final Dispatcher dispatcher;
    public final String user;

    public Services(final String userHash, final Application application, final Session session,
	    final StateManager stateManager, final Dispatcher dispatcher) {
	this.user = userHash;
	this.app = application;
	this.session = session;
	this.stateManager = stateManager;
	this.dispatcher = dispatcher;
    }

}