/*
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

package org.ourproject.kune.workspace.client.i18n;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A helper class for implementers of the SourcesI18nChangeEvents interface.
 * This subclass of {@link ArrayList} assumes that all objects added to it will
 * be of type {@link com.google.gwt.user.client.ui.I18nChangeListener}.
 */
public class I18nChangeListenerCollection extends ArrayList {

    private static final long serialVersionUID = 1L;

    /**
     * Fires a i18n change event to all listeners.
     * 
     */
    public void fireI18nLanguageChange() {
        for (Iterator it = iterator(); it.hasNext();) {
            I18nChangeListener listener = (I18nChangeListener) it.next();
            listener.onI18nLanguageChange();
        }
    }
}
