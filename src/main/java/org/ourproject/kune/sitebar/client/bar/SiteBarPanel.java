/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.ui.ChatFactory;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoom;
import org.ourproject.kune.chat.client.ui.rooms.RoomUser;
import org.ourproject.kune.platf.client.group.NewGroupForm;
import org.ourproject.kune.platf.client.ui.BorderDecorator;
import org.ourproject.kune.platf.client.ui.dialogs.TwoButtonsDialog;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.login.LoginForm;
import org.ourproject.kune.sitebar.client.login.LoginFormPanel;
import org.ourproject.kune.sitebar.client.services.Images;
import org.ourproject.kune.sitebar.client.services.Translate;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SiteBarPanel extends Composite implements SiteBarView {

    private static final String IMAGE_SPIN = "images/spin-kune-thund-green.gif";
    private final SiteBarPresenter presenter;
    private final Translate t;
    private final Image logoImage;

    private final HorizontalPanel siteBarHP;

    private final Image spinProcessing;
    private final Label textProcessingLabel;
    private final Hyperlink loginHyperlink;
    private final Hyperlink newGroupHyperlink;
    private final HTML pipeSeparatorHtml2;
    private PushButton searchButton;
    private TextBox searchTextBox;

    private final Hyperlink logoutHyperlink;
    private LoginFormPanel loginPanel;
    private TwoButtonsDialog newGroupDialog;
    private final Images img;

    public SiteBarPanel(final SiteBarPresenter initPresenter) {
	t = SiteBarTrans.getInstance().t;
	img = Images.App.getInstance();

	// Initialize
	siteBarHP = new HorizontalPanel();
	initWidget(siteBarHP);
	this.presenter = initPresenter;
	spinProcessing = new Image();
	img.spinKuneThundGreen().applyTo(spinProcessing);
	spinProcessing.setUrl(IMAGE_SPIN);
	textProcessingLabel = new Label();
	final Label expandLabel = new Label("");
	newGroupHyperlink = new Hyperlink();
	final HTML pipeSeparatorHtml = new HTML();
	pipeSeparatorHtml2 = new HTML();
	loginHyperlink = new Hyperlink();
	logoutHyperlink = new Hyperlink();
	HTML spaceSeparator1 = new HTML("<b></b>");
	MenuBar options = new MenuBar();
	MenuBar optionsSubmenu = new MenuBar(true);
	BorderDecorator optionsButton = new BorderDecorator(options, BorderDecorator.ALL, BorderDecorator.SIMPLE);
	HTML spaceSeparator2 = new HTML("<b></b>");
	logoImage = new Image();

	// Layout
	siteBarHP.add(spinProcessing);
	siteBarHP.add(textProcessingLabel);
	siteBarHP.add(expandLabel);
	siteBarHP.add(loginHyperlink);
	siteBarHP.add(pipeSeparatorHtml);
	siteBarHP.add(logoutHyperlink);
	siteBarHP.add(pipeSeparatorHtml2);
	siteBarHP.add(newGroupHyperlink);
	siteBarHP.add(spaceSeparator1);
	siteBarHP.add(optionsButton);
	siteBarHP.add(spaceSeparator2);
	createSearchBox();
	siteBarHP.add(logoImage);

	// Set properties
	siteBarHP.addStyleName("kune-SiteBarPanel");
	siteBarHP.setCellWidth(expandLabel, "100%");
	spinProcessing.addStyleName("kune-Progress");
	textProcessingLabel.setText(t.Processing());
	textProcessingLabel.addStyleName("kune-Progress");
	newGroupHyperlink.setText(t.NewGroup());
	newGroupHyperlink.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.doNewGroup();
	    }
	});
	pipeSeparatorHtml.setHTML("|");
	pipeSeparatorHtml.setStyleName("kune-SiteBarPanel-Separator");
	pipeSeparatorHtml2.setHTML("|");
	pipeSeparatorHtml2.setStyleName("kune-SiteBarPanel-Separator");
	loginHyperlink.setText(t.Login());

	options.addItem(t.Options(), true, optionsSubmenu);

	optionsSubmenu.addItem(t.MyGroups(), true, new Command() {
	    public void execute() {
		// FIXME
		Window.alert("In development!");
	    }
	});
	optionsSubmenu.addItem(t.HelpWithTranslation(), true, new Command() {
	    public void execute() {
		// FIXME
		Window.alert("In development!");
	    }
	});
	optionsSubmenu.addItem(t.Help(), true, new Command() {
	    public void execute() {
		// FIXME
		Window.alert("In develoopment!");
	    }
	});
	options.setStyleName("kune-MenuBar");
	optionsButton.setColor("AAA");
	optionsButton.setHeight("16px");
	spaceSeparator1.setStyleName("kune-SiteBarPanel-SpaceSeparator");
	spaceSeparator2.setStyleName("kune-SiteBarPanel-SpaceSeparator");

	createListeners();

	// TODO: externalize this
	img.kuneLogo16px().applyTo(logoImage);

	this.hideProgress();
    }

    private void createSearchBox() {
	searchButton = new PushButton(img.kuneSearchIco().createImage(), img.kuneSearchIcoPush().createImage());
	searchTextBox = new TextBox();

	siteBarHP.add(searchButton);
	siteBarHP.add(searchTextBox);

	searchTextBox.setWidth("180");
	setDefaultTextSearch();
	searchTextBox.addFocusListener(new FocusListener() {

	    public void onFocus(final Widget arg0) {
		presenter.onSearchFocus();
	    }

	    public void onLostFocus(final Widget arg0) {
		presenter.onSearchLostFocus(searchTextBox.getText());
	    }
	});
    }

    public void setDefaultTextSearch() {
	searchTextBox.setText(t.Search());
    }

    public void clearSearchText() {
	searchTextBox.setText("");
    }

    public void clearUserName() {
	loginHyperlink.setText(t.Login());
    }

    public void setLogo(final Image logo) {
	// TODO
    }

    public void setSearchText(final String text) {
	searchTextBox.setText(text);
    }

    public void showLoggedUserName(final String user) {
	loginHyperlink.setText(user);
    }

    public void showLoginDialog() {
	final LoginForm login = SiteBarFactory.createLogin(presenter);
	loginPanel = (LoginFormPanel) login.getView();
	loginPanel.show();
    }

    public void hideLoginDialog() {
	loginPanel.hide();
    }

    public void showNewGroupDialog() {
	final NewGroupForm newGroupForm = SiteBarFactory.createNewGroup(presenter);
	newGroupDialog = new TwoButtonsDialog(t.RegisterANewGroup(), t.Register(), t.Cancel(), true, false, 500, 365,
		new FormListener() {
		    public void onAccept() {
			newGroupForm.doCreateNewGroup();
			newGroupDialog.hide();
		    }

		    public void onCancel() {
			newGroupForm.doCancel();
			newGroupDialog.hide();
		    }
		});
	newGroupDialog.add((Widget) newGroupForm.getView());
	newGroupDialog.hide();
	newGroupDialog.center();
	Site.hideProgress();
    }

    public void hideNewGroupDialog() {
	newGroupDialog.hide();
    }

    public void setLogoutLinkVisible(final boolean visible) {
	logoutHyperlink.setVisible(visible);
	pipeSeparatorHtml2.setVisible(visible);
    }

    public void restoreLoginLink() {
	loginHyperlink.setText(t.Login());
    }

    public void showProgress(final String text) {
	textProcessingLabel.setText(text);
	spinProcessing.setVisible(true);
	textProcessingLabel.setVisible(true);
    }

    public void hideProgress() {
	spinProcessing.setVisible(false);
	textProcessingLabel.setVisible(false);
    }

    private void createListeners() {
	loginHyperlink.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.doLogin();
	    }
	});
	logoutHyperlink.setText(t.Logout());
	searchButton.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		MultiRoom rooms = ChatFactory.createChatMultiRoom();
		rooms.show();

		Room room1 = rooms.createRoom("chat1@talks.localhost", "luther.b", RoomUser.PARTICIPANT);
		room1.setSubject("Welcome to chat1, today topic: Cultural issues in Brazil");
		room1.addUser("otro usuario", RoomUser.MODERADOR);
		room1.addMessage("luther.b", "Mensaje de test en room1");

		Room room2 = rooms.createRoom("chat2@talks.localhost", "luther", RoomUser.PARTICIPANT);
		room2.setSubject("Welcome to this room: we are talking today about 2009 meeting");
		room2.addUser("luther", RoomUser.MODERADOR);
		room2.addMessage("luther", "Mensaje de test en room2");
		room2.addInfoMessage("Mensaje de evento en room2");
		room2.addDelimiter("17:35");

		// presenter.doSearch(searchTextBox.getText());
	    }
	});
	logoutHyperlink.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.doLogout();
	    }
	});
	searchTextBox.addKeyboardListener(new KeyboardListener() {
	    public void onKeyDown(final Widget arg0, final char arg1, final int arg2) {
	    }

	    public void onKeyPress(final Widget arg0, final char arg1, final int arg2) {
	    }

	    public void onKeyUp(final Widget widget, final char key, final int mod) {
		if (key == KEY_ENTER) {
		    if (searchTextBox.getText().length() > 0) {
			presenter.doSearch(searchTextBox.getText());
		    }
		}
	    }
	});
    }
}
