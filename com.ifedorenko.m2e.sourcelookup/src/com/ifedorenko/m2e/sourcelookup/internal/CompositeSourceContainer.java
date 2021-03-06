/*******************************************************************************
 * Copyright (c) 2011 Igor Fedorenko
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Igor Fedorenko - initial API and implementation
 *******************************************************************************/
package com.ifedorenko.m2e.sourcelookup.internal;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;

class CompositeSourceContainer
    extends org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer
{

    private final ISourceContainer[] members;

    public CompositeSourceContainer( Collection<ISourceContainer> members )
    {
        this.members = members.toArray( new ISourceContainer[members.size()] );
    }

    @Override
    public ISourceContainer[] getSourceContainers()
        throws CoreException
    {
        return members;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public ISourceContainerType getType()
    {
        return null;
    }

    @Override
    protected ISourceContainer[] createSourceContainers()
        throws CoreException
    {
        return null;
    }

    @Override
    public void dispose()
    {
        super.dispose();
        for ( ISourceContainer member : members )
        {
            member.dispose();
        }
        Arrays.fill( members, null );
    }
}
