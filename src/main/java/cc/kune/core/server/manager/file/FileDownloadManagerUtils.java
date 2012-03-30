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
 \*/
package cc.kune.core.server.manager.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileDownloadManagerUtils {

  public static final Log LOG = LogFactory.getLog(FileDownloadManagerUtils.class);

  public static void returnFile(final InputStream in, final OutputStream out)
      throws FileNotFoundException, IOException {
    try {
      final byte[] buf = new byte[4 * 1024]; // 4K buffer
      int bytesRead;
      while ((bytesRead = in.read(buf)) != -1) {
        out.write(buf, 0, bytesRead);
      }
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  public static void returnFile(final String filename, final OutputStream out)
      throws FileNotFoundException, IOException {
    InputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream(filename));
      final byte[] buf = new byte[4 * 1024]; // 4K buffer
      int bytesRead;
      while ((bytesRead = in.read(buf)) != -1) {
        out.write(buf, 0, bytesRead);
      }
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  public static void returnNotFound404(final HttpServletResponse resp) throws IOException {
    resp.getWriter().println("Content not found");
    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  public static InputStream searchFileInResourcBases(final List<String> resourceBases,
      final String filename) {
    InputStream in = null;
    File icon = null;
    for (final String path : resourceBases) {
      final String pathAndfilename = path + (path.endsWith(File.separator) ? "" : File.separator) + filename;
      final File file = new File(pathAndfilename);
      if (file.exists()) {
        icon = file;
        break;
      }
    }
    try {
      if (icon != null) {
        in = new BufferedInputStream(new FileInputStream(icon));
      }
    } catch (final FileNotFoundException e) {
      LOG.error(String.format("Cannot read filename: %s in %s", filename, resourceBases.toString()));
    }
    return in;
  }
}
