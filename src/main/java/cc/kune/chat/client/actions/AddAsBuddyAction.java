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
 */
package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.ChatInstances;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;

public class AddAsBuddyAction extends AbstractExtendedAction {
  private final ChatClient chatEngine;

  @Inject
  public AddAsBuddyAction(final ChatClient chatEngine, final ChatInstances chatInstances,
      final StateManager stateManager, final I18nTranslationService i18n, final CoreResources img) {
    super();
    this.chatEngine = chatEngine;
    putValue(Action.NAME, i18n.t(CoreMessages.ADD_AS_A_BUDDY));
    putValue(Action.SMALL_ICON, img.addGreen());
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    String username = null;
    if (event.getTarget() instanceof GroupDTO) {
      username = ((GroupDTO) event.getTarget()).getShortName();
    } else if (event.getTarget() instanceof UserSimpleDTO) {
      username = ((UserSimpleDTO) event.getTarget()).getShortName();
    }
    if (username != null) {
      chatEngine.addNewBuddy(username);
      // NotifyUser.info("Added as buddy. Waiting buddy response");
      setEnabled(false);
    }
  }
}
