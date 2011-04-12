/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.users;

import java.util.List;

import cc.kune.domain.Group;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

public class UserInfo {
    private String chatName;
    private String chatPassword;
    private List<String> enabledTools;
    private List<Group> groupsIsAdmin;
    private List<Group> groupsIsCollab;
    private String homePage;
    private boolean showDeletedContent;
    private User user;
    private Group userGroup;
    private String userHash;

    public String getChatName() {
        return chatName;
    }

    public String getChatPassword() {
        return chatPassword;
    }

    public I18nCountry getCountry() {
        return user.getCountry();
    }

    public List<String> getEnabledTools() {
        return enabledTools;
    }

    public List<Group> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public List<Group> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public String getHomePage() {
        return homePage;
    }

    public I18nLanguage getLanguage() {
        return user.getLanguage();
    }

    public String getName() {
        return user.getName();
    }

    public String getShortName() {
        return user.getShortName();
    }

    public boolean getShowDeletedContent() {
        return showDeletedContent;
    }

    public User getUser() {
        return user;
    }

    public Group getUserGroup() {
        return userGroup;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setChatName(final String chatName) {
        this.chatName = chatName;
    }

    @Deprecated
    public void setChatPassword(final String chatPassword) {
        this.chatPassword = chatPassword;
    }

    public void setEnabledTools(final List<String> enabledTools) {
        this.enabledTools = enabledTools;
    }

    public void setGroupsIsAdmin(final List<Group> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public void setGroupsIsCollab(final List<Group> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

    public void setHomePage(final String homePage) {
        this.homePage = homePage;
    }

    public void setShowDeletedContent(final boolean showDeletedContent) {
        this.showDeletedContent = showDeletedContent;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setUserGroup(final Group userGroup) {
        this.userGroup = userGroup;
    }

    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }

}