/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.ctxnav;

import org.ourproject.kune.platf.client.View;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;

public interface ContextNavigatorView extends View {

    void addItem(ContextNavigatorItem contextNavigatorItem);

    void clear();

    void detach();

    void editItem(String id);

    boolean isAttached();

    boolean isSelected(String id);

    void selectItem(String id);

    void setEditable(boolean editable);

    void setItemStatus(String id, ContentStatus status);

    void setItemText(String id, String text);

    void setRootItem(String id, String text, StateToken stateToken);

}
