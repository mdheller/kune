/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

package cc.kune.common.client.shortcuts;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class GlobalShortcuts.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class GlobalShortcuts {

  /**
   * Instantiates a new global shortcuts.
   *
   * @param register the register
   * @param eventBus the event bus
   */
  @Inject
  public GlobalShortcuts(final GlobalShortcutRegister register, final EventBus eventBus) {
    register.put(KeyStroke.getKeyStroke(KeyCodes.KEY_ESCAPE, 0), new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        OnEscapePressedEvent.fire(eventBus);
      }
    });
    // More global shortcuts here (...)
  }

}
