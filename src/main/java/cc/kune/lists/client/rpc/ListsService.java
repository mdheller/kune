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
package cc.kune.lists.client.rpc;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface ListsService.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RemoteServiceRelativePath("ListsService")
public interface ListsService extends RemoteService {

  /**
   * Creates the list.
   *
   * @param hash the hash
   * @param parentToken the parent token
   * @param name the name
   * @param description the description
   * @param isPublic the is public
   * @return the state container dto
   */
  StateContainerDTO createList(String hash, StateToken parentToken, String name, String description,
      boolean isPublic);

  /**
   * New post.
   *
   * @param userHash the user hash
   * @param parentToken the parent token
   * @param title the title
   * @return the state content dto
   */
  public StateContentDTO newPost(final String userHash, final StateToken parentToken, final String title);

  /**
   * Sets the public.
   *
   * @param hash the hash
   * @param token the token
   * @param isPublic the is public
   * @return the state container dto
   */
  StateContainerDTO setPublic(String hash, StateToken token, Boolean isPublic);

  /**
   * Subscribe to list.
   *
   * @param hash the hash
   * @param token the token
   * @param subscribe the subscribe
   * @return the state container dto
   */
  StateContainerDTO subscribeToList(String hash, StateToken token, Boolean subscribe);

}
