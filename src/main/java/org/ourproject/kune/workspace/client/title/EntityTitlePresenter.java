/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.title;

import java.util.Date;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityTitlePresenter implements EntityTitle {

    private EntityTitleView view;
    private final I18nTranslationService i18n;
    private final KuneErrorHandler errorHandler;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final Session session;
    private final Provider<ContextNavigator> contextNavigatorProvider;

    public EntityTitlePresenter(final I18nTranslationService i18n, final KuneErrorHandler errorHandler,
	    final StateManager stateManager, final Session session,
	    final Provider<ContentServiceAsync> contentServiceProvider,
	    final Provider<ContextNavigator> contextNavigatorProvider) {
	this.i18n = i18n;
	this.errorHandler = errorHandler;
	this.session = session;
	this.contentServiceProvider = contentServiceProvider;
	this.contextNavigatorProvider = contextNavigatorProvider;
	stateManager.onStateChanged(new Slot<StateDTO>() {
	    public void onEvent(final StateDTO state) {
		setState(state);
	    }
	});
    }

    public View getView() {
	return view;
    }

    public void init(final EntityTitleView view) {
	this.view = view;
    }

    public void setContentDate(final Date publishedOn) {
	final DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy, Z");
	view.setContentDate(i18n.t("Published on: [%s]", fmt.format(publishedOn)));
    }

    /**
     * Used renaming from context
     */
    public void setContentTitle(final String title) {
	view.setContentTitle(title);
    }

    public void setContentTitle(final String title, final boolean editable) {
	setContentTitle(title);
	view.setContentTitleEditable(editable);
    }

    protected void onTitleRename(final String newName) {
	Site.showProgressSaving();
	final StateDTO currentState = session.getCurrentState();
	final StateToken stateToken = currentState.getStateToken();
	contentServiceProvider.get().rename(session.getUserHash(), currentState.getGroup().getShortName(),
		stateToken.getEncoded(), newName, new AsyncCallback<String>() {
		    public void onFailure(final Throwable caught) {
			view.restoreOldTitle();
			errorHandler.process(caught);
		    }

		    public void onSuccess(final String result) {
			Site.hideProgress();
			view.setContentTitle(result);
			contextNavigatorProvider.get().setItemText(stateToken, newName);
		    }
		});
	Site.hideProgress();
    }

    private void setContentDateVisible(final boolean visible) {
	view.setDateVisible(visible);
    }

    private void setState(final StateDTO state) {
	if (state.hasDocument()) {
	    setContentTitle(state.getTitle(), state.getContentRights().isEditable());
	    setContentDateVisible(true);
	    setContentDate(state.getPublishedOn());
	} else {
	    if (state.getContainer().getParentFolderId() == null) {
		// We translate root folder names (documents, chat room,
		// etcetera)
		setContentTitle(i18n.t(state.getTitle()), false);
	    } else {
		setContentTitle(state.getTitle(), state.getContentRights().isEditable());
	    }
	    setContentDateVisible(false);
	}
    }

}
