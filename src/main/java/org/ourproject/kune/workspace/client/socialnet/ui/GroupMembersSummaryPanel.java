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
package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.UIConstants;
import org.ourproject.kune.platf.client.ui.stacks.StackSubItemAction;
import org.ourproject.kune.platf.client.ui.stacks.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryView;
import org.ourproject.kune.workspace.client.socialnet.MemberAction;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.gwtext.client.widgets.MessageBox;

public class GroupMembersSummaryPanel extends StackedDropDownPanel implements GroupMembersSummaryView {
    private static final boolean COUNTS_VISIBLE = true;
    private final Images img = Images.App.getInstance();
    private final GroupMembersSummaryPresenter presenter;
    private final I18nTranslationService i18n;

    public GroupMembersSummaryPanel(final GroupMembersSummaryPresenter initPresenter,
	    final I18nTranslationService i18n, final WorkspaceSkeleton ws) {
	super(initPresenter, "#00D4AA", i18n.t("Group members"), i18n
		.t("People and groups collaborating in this group"), COUNTS_VISIBLE);
	presenter = initPresenter;
	this.i18n = i18n;
	ws.getEntitySummary().addInSummary(this);
    }

    public void addAddMemberLink() {
	super.addBottomLink(img.addGreen(), i18n.t("Add member"), i18n
		.t("Add a group or a person as member of this group"), WorkspaceEvents.ADD_MEMBER_GROUPLIVESEARCH,
		presenter);
    }

    public void addCategory(final String name, final String title) {
	super.addStackItem(name, title, COUNTS_VISIBLE);
    }

    public void addCategory(final String name, final String title, final String iconType) {
	super.addStackItem(name, title, getIcon(iconType), UIConstants.ICON_HORIZ_ALIGN_RIGHT, COUNTS_VISIBLE);
    }

    public void addCategoryMember(final String categoryName, final String name, final String title,
	    final MemberAction[] memberActions) {
	final StackSubItemAction[] subItems = new StackSubItemAction[memberActions.length];
	for (int i = 0; i < memberActions.length; i++) {
	    subItems[i] = new StackSubItemAction(getIconFronEvent(memberActions[i].getAction()), memberActions[i]
		    .getText(), memberActions[i].getAction());
	}

	super.addStackSubItem(categoryName, img.groupDefIcon(), name, title, subItems);
    }

    public void addComment(final String comment) {
	super.addComment(comment);
    }

    public void addJoinLink() {
	super.addBottomLink(img.addGreen(), i18n.t("Request to join"), i18n.t("Request to participate in this group"),
		WorkspaceEvents.REQ_JOIN_GROUP);
    }

    public void addUnjoinLink() {
	super.addBottomLink(img.del(), i18n.t("Unjoin this group"), i18n
		.t("Don't participate more as a member in this group"), WorkspaceEvents.UNJOIN_GROUP);
    }

    public void clear() {
	super.clear();
    }

    public void confirmAddCollab(final String groupShortName, final String groupLongName) {
	final String groupName = groupLongName + " (" + groupShortName + ")";
	MessageBox.confirm(i18n.t("Confirm addition of member"), i18n.t("Add [%s] as member?", groupName),
		new MessageBox.ConfirmCallback() {
		    public void execute(final String btnID) {
			if (btnID.equals("yes")) {
			    presenter.addCollab(groupShortName);
			}
		    }
		});
    }

    public void hide() {
	this.setVisible(false);
    }

    public void show() {
	this.setVisible(true);
    }

    public void showCategory(final String name) {
	super.showStackItem(name);
    }

    private AbstractImagePrototype getIcon(final String event) {
	if (event == GroupMembersSummaryView.ICON_ALERT) {
	    return img.alert();
	}
	throw new IndexOutOfBoundsException("Icon unknown in GroupMemebersPanel");
    }

    private AbstractImagePrototype getIconFronEvent(final String event) {
	if (event == WorkspaceEvents.ACCEPT_JOIN_GROUP) {
	    return img.accept();
	}
	if (event == WorkspaceEvents.DENY_JOIN_GROUP) {
	    return img.cancel();
	}
	if (event == WorkspaceEvents.DEL_MEMBER) {
	    return img.del();
	}
	if (event == PlatformEvents.GOTO) {
	    return img.groupHome();
	}
	if (event == WorkspaceEvents.SET_ADMIN_AS_COLLAB) {
	    return img.arrowDownGreen();
	}
	if (event == WorkspaceEvents.SET_COLLAB_AS_ADMIN) {
	    return img.arrowUpGreen();
	}
	throw new IndexOutOfBoundsException("Event unknown in GroupMembersPanel");
    }

}
