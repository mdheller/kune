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

package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorComponent;
import org.ourproject.kune.workspace.client.licensefoot.LicenseComponent;

public class WorkspaceUIComponents {
    private LicenseComponent license;
    private ContentTitleComponent contentTitle;
    private GroupMembersSummary groupMembers;
    private ParticipationSummary participatesInGroups;
    private GroupSummary groupSummary;
    private ContentSubTitleComponent contentSubTitle;
    private ContentBottomToolBarComponent contentBottomToolBar;
    private ThemeMenuComponent themeMenu;
    private ContentToolBarComponent contentToolBar;
    private GroupLiveSearchComponent groupLiveSearch;
    private I18nTranslatorComponent i18nTranslatorSearch;
    private UserLiveSearchComponent userLiveSearch;

    public WorkspaceUIComponents(final WorkspacePresenter presenter) {
    }

    public ContentBottomToolBarComponent getContentBottomToolBarComponent() {
	if (contentBottomToolBar == null) {
	    contentBottomToolBar = WorkspaceFactory.createContentBottomToolBarComponent();
	}
	return contentBottomToolBar;
    }

    public ContentSubTitleComponent getContentSubTitleComponent() {
	if (contentSubTitle == null) {
	    contentSubTitle = WorkspaceFactory.createContentSubTitleComponent();
	}
	return contentSubTitle;
    }

    public ContentTitleComponent getContentTitleComponent() {
	if (contentTitle == null) {
	    contentTitle = WorkspaceFactory.createContentTitleComponent();
	}
	return contentTitle;
    }

    public ContentToolBarComponent getContentToolBarComponent() {
	if (contentToolBar == null) {
	    contentToolBar = WorkspaceFactory.createContentToolBarComponent();
	}
	return contentToolBar;
    }

    public GroupLiveSearchComponent getGroupLiveSearchComponent() {
	if (groupLiveSearch == null) {
	    groupLiveSearch = WorkspaceFactory.createGroupLiveSearchComponent();
	}
	return groupLiveSearch;
    }

    public I18nTranslatorComponent getI18nTranslatorComponent() {
	if (i18nTranslatorSearch == null) {
	    i18nTranslatorSearch = WorkspaceFactory.createI18nTranslatorComponent();
	}
	return i18nTranslatorSearch;
    }

    public LicenseComponent getLicenseComponent() {
	if (license == null) {
	    license = WorkspaceFactory.createLicenseComponent();
	}
	return license;
    }

    public ThemeMenuComponent getThemeMenuComponent() {
	if (themeMenu == null) {
	    themeMenu = WorkspaceFactory.createThemeMenuComponent();
	}
	return themeMenu;
    }

    public UserLiveSearchComponent getUserLiveSearchComponent() {
	if (userLiveSearch == null) {
	    userLiveSearch = WorkspaceFactory.createUserLiveSearchComponent();
	}
	return userLiveSearch;
    }

}
