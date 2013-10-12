/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.wiki.shared;

import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.shared.ToolConstants;

public final class WikiToolConstants {

  public static final KuneIcon ICON_TYPE_FOLDER = new KuneIcon('n');
  public static final KuneIcon ICON_TYPE_ROOT = new KuneIcon('e');
  public static final KuneIcon ICON_TYPE_WIKIPAGE = new KuneIcon('e');
  public static final String ROOT_NAME = "wiki";
  public static final String TOOL_NAME = "wiki";
  public static final String TYPE_FOLDER = TOOL_NAME + "." + "folder";
  public static final String TYPE_ROOT = TOOL_NAME + "." + "root";
  public static final String TYPE_UPLOADEDFILE = TOOL_NAME + "." + ToolConstants.UPLOADEDFILE_SUFFIX;
  public static final String TYPE_WIKIPAGE = TOOL_NAME + "." + "wikipage";

  private WikiToolConstants() {
  }
}
