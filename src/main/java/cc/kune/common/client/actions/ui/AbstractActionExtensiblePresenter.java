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
package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractActionExtensiblePresenter.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractActionExtensiblePresenter implements IsActionExtensible {

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.IsActionExtensible#add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public abstract void add(final GuiActionDescrip descriptor);

  /**
   * Adds the action collection.
   *
   * @param descriptors the descriptors
   */
  public void addActionCollection(final GuiActionDescCollection descriptors) {
    for (final GuiActionDescrip descriptor : descriptors) {
      add(descriptor);
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.IsActionExtensible#add(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip[])
   */
  @Override
  public void add(final GuiActionDescrip... descriptors) {
    for (final GuiActionDescrip descriptor : descriptors) {
      add(descriptor);
    }
  }

}
