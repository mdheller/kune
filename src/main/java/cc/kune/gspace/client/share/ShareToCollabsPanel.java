/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share;

import cc.kune.common.shared.i18n.I18n;

/**
 * The Class ShareToAdminsPanel is a lists of user/groups with collabs
 * permissions
 */
public class ShareToCollabsPanel extends AbstractShareToListPanel implements ShareToCollabsView {

  /**
   * Instantiates a new share to admins panel.
   */
  ShareToCollabsPanel() {
    super(I18n.t("who more can edit:"));
  }

}
