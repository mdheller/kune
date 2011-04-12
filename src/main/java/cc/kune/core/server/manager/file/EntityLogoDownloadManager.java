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
 \*/
package cc.kune.core.server.manager.file;

import java.io.IOException;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cc.kune.core.client.services.FileConstants;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Group;

import com.google.inject.Inject;

public class EntityLogoDownloadManager extends HttpServlet {

    private static final long serialVersionUID = -1958945058088446881L;
    @Inject
    GroupManager groupManager;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
            IOException {

        // final String userHash = req.getParameter(FileParams.HASH);
        final StateToken stateToken = new StateToken(req.getParameter(FileConstants.TOKEN));

        Group group = Group.NO_GROUP;
        try {
            group = groupManager.findByShortName(stateToken.getGroup());
            if (group == Group.NO_GROUP) {
                throw new NoResultException("Group not found trying to get the logo");
            }

            if (!group.hasLogo()) {
                throw new NoResultException("This Group has no logo");
            }
        } catch (NoResultException e) {
            FileDownloadManagerUtils.returnNotFound(resp);
            return;
        }

        byte[] logo = group.getLogo();

        resp.setContentLength(logo.length);
        resp.setContentType(group.getLogoMime().toString());
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + group.getShortName() + "-logo\"");
        resp.getOutputStream().write(logo);
    }
}