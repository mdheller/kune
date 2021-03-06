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
package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarViewer;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarViewSelectAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CalendarViewSelectAction extends RolAction {

  /** The cal viewer. */
  private final CalendarViewer calViewer;

  /** The days. */
  private int days;

  /** The view. */
  private CalendarViews view;

  /**
   * Instantiates a new calendar view select action.
   * 
   * @param calViewer
   *          the cal viewer
   */
  @Inject
  public CalendarViewSelectAction(final CalendarViewer calViewer) {
    super(AccessRolDTO.Viewer, false);
    this.calViewer = calViewer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    if (days != 0) {
      calViewer.setView(view, days);
    } else {
      calViewer.setView(view);
    }
  }

  /**
   * Sets the days.
   * 
   * @param days
   *          the new days
   */
  public void setDays(final int days) {
    this.days = days;
  }

  /**
   * Sets the view.
   * 
   * @param view
   *          the new view
   */
  public void setView(final CalendarViews view) {
    this.view = view;
  }
}
