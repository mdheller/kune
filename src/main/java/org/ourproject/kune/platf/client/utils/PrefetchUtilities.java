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

package org.ourproject.kune.platf.client.utils;

import com.google.gwt.user.client.ui.Image;

public class PrefetchUtilities {

    public static void preFetchImpImages() {
	Image.prefetch("images/spin-kune-thund-green.gif");
	Image.prefetch("css/img/button-bg-hard.gif");
	Image.prefetch("css/img/button-bg-soft.gif");

	Image.prefetch("images/lic/gnu-fdl.gif");
	Image.prefetch("images/lic/bysa80x15.png");
	Image.prefetch("images/lic/bynd80x15.png");
	Image.prefetch("images/lic/byncsa80x15.png");
	Image.prefetch("images/lic/byncnd80x15.png");
	Image.prefetch("images/lic/bync80x15.png");
	Image.prefetch("images/lic/by80x15.png");

	// gwt-ext dialog images
	Image.prefetch("js/ext/resources/images/aero/basic-dialog/bg-center.gif");
	Image.prefetch("js/ext/resources/images/aero/basic-dialog/bg-left.gif");
	Image.prefetch("js/ext/resources/images/aero/basic-dialog/bg-right.gif");
	Image.prefetch("js/ext/resources/images/aero/basic-dialog/collapse.gif");
	Image.prefetch("js/ext/resources/images/aero/basic-dialog/hd-sprite.gif");
	Image.prefetch("js/ext/resources/images/aero/basic-dialog/se-handle.gif");
	Image.prefetch("js/ext/resources/images/aero/grid/grid-hrow.gif");
	Image.prefetch("js/ext/resources/images/aero/s.gif");
	Image.prefetch("js/ext/resources/images/aero/tabs/tab-sprite.gif");
	Image.prefetch("js/ext/resources/images/aero/tabs/tab-strip-bg.gif");
	Image.prefetch("js/ext/resources/images/default/basic-dialog/btn-sprite.gif");
	Image.prefetch("js/ext/resources/images/default/form/exclamation.gif");
	Image.prefetch("js/ext/resources/images/default/form/text-bg.gif");
	Image.prefetch("js/ext/resources/images/default/layout/panel-close.gif");
	Image.prefetch("js/ext/resources/images/default/qtip/close.gif");
	Image.prefetch("js/ext/resources/images/default/qtip/tip-sprite.gif");
	Image.prefetch("js/ext/resources/images/default/s.gif");
	Image.prefetch("js/ext/resources/images/default/shadow-c.png");
	Image.prefetch("js/ext/resources/images/default/shadow-lr.png");
	Image.prefetch("js/ext/resources/images/default/shadow.png");

    }
}