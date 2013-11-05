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
package cc.kune.domain.finders;

import java.util.ArrayList;
import java.util.List;

import cc.kune.domain.License;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

// TODO: Auto-generated Javadoc
/**
 * The Interface LicenseFinder.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface LicenseFinder {

  /**
   * Find by short name.
   *
   * @param shortName the short name
   * @return the license
   */
  @Finder(query = "from License l where l.shortName = :shortName")
  public License findByShortName(@Named("shortName") final String shortName);

  /**
   * Gets the all.
   *
   * @return the all
   */
  @Finder(query = "from License", returnAs = ArrayList.class)
  public List<License> getAll();

  /**
   * Gets the cc.
   *
   * @return the cc
   */
  @Finder(query = "from License where isCC = true", returnAs = ArrayList.class)
  public List<License> getCC();

  /**
   * Gets the not cc.
   *
   * @return the not cc
   */
  @Finder(query = "from License where isCC = false", returnAs = ArrayList.class)
  public List<License> getNotCC();
}
