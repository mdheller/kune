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
package cc.kune.gallery.shared;

import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.shared.ToolConstants;

public final class GalleryToolConstants {
  public static final KuneIcon ICON_TYPE_ALBUM = new KuneIcon('j');
  public static final KuneIcon ICON_TYPE_ROOT = new KuneIcon('j');
  public static final KuneIcon ICON_TYPE_UPLOADEDFILE = new KuneIcon('j');
  public static final String ROOT_NAME = "gallery";
  public static final String TOOL_NAME = "gallery";
  public static final String TYPE_ALBUM = TOOL_NAME + "." + "album";
  public static final String TYPE_ROOT = TOOL_NAME + "." + "root";
  public static final String TYPE_UPLOADEDFILE = TOOL_NAME + "." + ToolConstants.UPLOADEDFILE_SUFFIX;

  private GalleryToolConstants() {
  }
}
