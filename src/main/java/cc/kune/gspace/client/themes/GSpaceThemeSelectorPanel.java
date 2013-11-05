/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.themes;

import cc.kune.common.client.actions.ui.ActionExtensibleView;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GSpaceThemeSelectorPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GSpaceThemeSelectorPanel extends ActionFlowPanel implements ActionExtensibleView {

  /**
   * Instantiates a new g space theme selector panel.
   *
   * @param guiProvider the gui provider
   * @param i18n the i18n
   */
  @Inject
  public GSpaceThemeSelectorPanel(final GuiProvider guiProvider, final I18nTranslationService i18n) {
    super(guiProvider, i18n);
  }

}
