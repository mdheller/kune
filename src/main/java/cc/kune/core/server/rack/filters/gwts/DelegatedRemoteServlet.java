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
package cc.kune.core.server.rack.filters.gwts;

import javax.servlet.ServletContext;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DelegatedRemoteServlet extends RemoteServiceServlet {
    private static final long serialVersionUID = -7646054921925214953L;
    private transient RemoteService service;
    private ServletContext servletContext;

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String processCall(final String payload) throws SerializationException {
        try {
            final RPCRequest rpcRequest = RPC.decodeRequest(payload, service.getClass());
            return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters());
        } catch (IncompatibleRemoteServiceException ex) {
            return RPC.encodeResponseForFailure(null, ex);
        }
    }

    public void setService(final RemoteService service) {
        this.service = service;
    }

    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    protected void doUnexpectedFailure(final Throwable except) {
        except.printStackTrace();
        super.doUnexpectedFailure(except);
    }
}