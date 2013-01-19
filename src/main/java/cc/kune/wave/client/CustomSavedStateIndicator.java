// @formatter:off
/*
 * Copyright 2011, 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.kune.wave.client;

import org.waveprotocol.wave.client.scheduler.Scheduler;
import org.waveprotocol.wave.client.scheduler.SchedulerInstance;
import org.waveprotocol.wave.client.scheduler.TimerService;
import org.waveprotocol.wave.concurrencycontrol.common.UnsavedDataListener;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.wave.client.kspecific.WaveUnsaveNotificator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.inject.Singleton;

@Singleton
public class CustomSavedStateIndicator implements UnsavedDataListener, ValueChangeHandler<String> {

  private enum SavedState {
    SAVED, UNSAVED;
  }

  private static final int UPDATE_DELAY_MS = 300;
  private static final int UPDATE_UNSAVED_DELAY_MS = 10;

  private SavedState currentSavedState = null;

  private final WaveUnsaveNotificator notifier;

  private final TimerService scheduler;
  private final Scheduler.Task updateTask = new Scheduler.Task() {
    @Override
    public void execute() {
      updateDisplay();
    }
  };
  private SavedState visibleSavedState = SavedState.SAVED;

  /**
   * Simple saved state indicator.
   *
   * @author danilatos@google.com (Daniel Danilatos)
   * @author yurize@apache.org (Yuri Zelikov)
   */
  public CustomSavedStateIndicator(){
    this.scheduler = SchedulerInstance.getLowPriorityTimer();
    notifier = new WaveUnsaveNotificator();
    Window.addWindowClosingHandler(new ClosingHandler() {
      @Override
      public void onWindowClosing(final ClosingEvent event) {
        if (currentSavedState != null && currentSavedState.equals(SavedState.UNSAVED)) {
          event.setMessage(I18n.t("This document is not saved. " +
              "Are you sure that you want to navigate away from this page?"));
        }
        }
      });
    History.addValueChangeHandler(this);
  }

  private void maybeUpdateDisplay() {
    if (needsUpdating()) {
      switch (currentSavedState) {
      case SAVED:
        scheduler.scheduleDelayed(updateTask, UPDATE_DELAY_MS);
        break;
      case UNSAVED:
        scheduler.scheduleDelayed(updateTask, UPDATE_UNSAVED_DELAY_MS);
        updateDisplay();
        break;
      default:
        throw new AssertionError("unknown " + currentSavedState);
      }
    } else {
      scheduler.cancel(updateTask);
    }
  }

  private boolean needsUpdating() {
    return visibleSavedState != currentSavedState;
  }

  @Override
  public void onClose(final boolean everythingCommitted) {
    if (everythingCommitted) {
      saved();
    } else {
      unsaved();
    }
  }

  public void onNewHistory(final String nextHistory, final SimpleResponseCallback callback) {
    if (currentSavedState != null && currentSavedState.equals(SavedState.UNSAVED)) {
      NotifyUser.askConfirmation(I18n.t("Please confirm"),I18n.t("This document is not saved. " +
          "Are you sure that you want to navigate away from it?"), callback);
    }
  }

  @Override
  public void onUpdate(final UnsavedDataInfo unsavedDataInfo) {
    if (unsavedDataInfo.estimateUnacknowledgedSize() != 0) {
      currentSavedState = SavedState.UNSAVED;
      unsaved();
    } else {
      currentSavedState = SavedState.SAVED;
      saved();
    }
  }

  @Override
  public void onValueChange(final ValueChangeEvent<String> event) {
    final String nextHistory = event.getValue();
    final SimpleResponseCallback callback = new SimpleResponseCallback() {
      @Override
      public void onCancel() {
        // Do nothing
      }

      @Override
      public void onSuccess() {
        History.newItem(nextHistory);
      }
    };
    onNewHistory(nextHistory, callback);
    }

  public void saved() {
    maybeUpdateDisplay();
  }

  public void unsaved() {
    maybeUpdateDisplay();
  }

  private void updateDisplay() {
    visibleSavedState = currentSavedState;
    switch (visibleSavedState) {
    case SAVED:
      notifier.hide();
      break;
    case UNSAVED:
      notifier.show(I18n.t("Saving"));
      break;
    default:
      throw new AssertionError("unknown " + currentSavedState);
    }
  }
}
