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

package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class CustomButton implements SourcesClickEvents {

    private ClickListenerCollection clickListeners;
    private final Button button;

    public CustomButton(final String text) {
	this(text, null);
    }

    public CustomButton(final String text, final ClickListener listener) {
	button = new Button(new ButtonConfig() {
	    {
		setText(text);
		setButtonListener(new ButtonListenerAdapter() {
		    public void onClick(final Button button, final EventObject e) {
			fireClickListeners();
		    }
		});
	    }
	});
	if (listener != null) {
	    addClickListener(listener);
	}
    }

    public void addClickListener(final ClickListener listener) {
	if (clickListeners == null) {
	    clickListeners = new ClickListenerCollection();
	}
	clickListeners.add(listener);
    }

    public void removeClickListener(final ClickListener listener) {
	if (clickListeners != null) {
	    clickListeners.remove(listener);
	}

    }

    public Button getButton() {
	return button;
    }

    public boolean isEnabled() {
	return !button.isDisabled();
    }

    private void fireClickListeners() {
	if (clickListeners != null) {
	    clickListeners.fireClick(button);
	}
    }

    public void setEnabled(final boolean enabled) {
	if (enabled) {
	    button.enable();
	} else {
	    button.disable();
	}
    }

    public void setText(final String text) {
	button.setText(text);
    }

    public void setVisible(final boolean b) {
	button.setVisible(b);
    }

    public void setHeight(final String height) {
	button.setHeight(height);
    }

    public void setWidth(final String width) {
	button.setWidth(width);
    }

    public void addStyleName(final String style) {
	button.addStyleName(style);
    }
}
