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

package org.ourproject.kune.platf.server.rpc;

import java.util.UUID;

import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.SessionService;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class UserRPC implements RPC, UserService {

    private final UserManager userManager;
    private final Provider<UserSession> userSessionProvider;
    private final GroupManager groupManager;
    private final Mapper mapper;
    private final UserInfoService userInfoService;
    private final Provider<SessionService> sessionServiceProvider;

    @Inject
    public UserRPC(final Provider<SessionService> sessionServiceProvider,
	    final Provider<UserSession> userSessionProvider, final UserManager userManager,
	    final GroupManager groupManager, final UserInfoService userInfoService, final Mapper mapper) {

	this.sessionServiceProvider = sessionServiceProvider;
	this.userSessionProvider = userSessionProvider;
	this.userManager = userManager;
	this.groupManager = groupManager;
	this.userInfoService = userInfoService;
	this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = DefaultException.class)
    public UserInfoDTO createUser(final UserDTO userDTO) throws DefaultException {
	final User user = userManager.createUser(userDTO.getShortName(), userDTO.getName(), userDTO.getEmail(), userDTO
		.getPassword(), userDTO.getLanguage().getCode(), userDTO.getCountry().getCode(), userDTO.getTimezone()
		.getId());
	groupManager.createUserGroup(user);
	return loginUser(user);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO login(final String nickOrEmail, final String passwd) throws DefaultException {
	final SessionService sessionService = sessionServiceProvider.get();
	sessionService.getNewSession();
	final User user = userManager.login(nickOrEmail, passwd);
	return loginUser(user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public void logout(final String userHash) throws DefaultException {
	getUserSession().logout();
	final SessionService sessionService = sessionServiceProvider.get();
	sessionService.getNewSession();
    }

    @Authenticated(mandatory = false)
    @Transactional(type = TransactionType.READ_ONLY)
    public void onlyCheckSession(final String userHash) throws DefaultException {
	// Do nothing @Authenticated checks user session
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO reloadUserInfo(final String userHash) throws DefaultException {
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	return loadUserInfo(user);
    };

    private UserSession getUserSession() {
	return userSessionProvider.get();
    }

    private UserInfoDTO loadUserInfo(final User user) throws DefaultException {
	final UserInfo userInfo = userInfoService.buildInfo(user, getUserSession().getHash());
	final UserInfoDTO map = mapper.map(userInfo, UserInfoDTO.class);
	try {
	    map.setCustomProperties(mapper.mapProperties(userInfo.getCustomProperties()));
	} catch (final ClassNotFoundException e) {
	    e.printStackTrace();
	    throw new DefaultException("Class not found during mapping userInfo");
	}
	return map;
    }

    private UserInfoDTO loginUser(final User user) throws DefaultException {
	if (user != null) {
	    // Maybe use terracotta.org for http session clustering
	    getUserSession().login(user, UUID.randomUUID().toString());
	    return loadUserInfo(user);
	} else {
	    throw new UserAuthException();
	}
    }

}
