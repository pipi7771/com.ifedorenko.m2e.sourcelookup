/*******************************************************************************
 * Copyright (c) 2011-2012 Igor Fedorenko
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Igor Fedorenko - initial API and implementation
 *******************************************************************************/
package com.ifedorenko.m2e.sourcelookup.ui.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.codehaus.plexus.util.IOUtil;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.m2e.core.ui.internal.actions.OpenPomAction;
import org.eclipse.m2e.core.ui.internal.actions.OpenPomAction.MavenStorageEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;

import com.ifedorenko.m2e.sourcelookup.internal.JDIHelpers;
import com.ifedorenko.m2e.sourcelookup.internal.MetaInfMavenScanner;

public class OpenPomCommandHandler
    extends AbstractHandler
{

    @Override
    public Object execute( ExecutionEvent event )
        throws ExecutionException
    {
        ISelection selection = HandlerUtil.getCurrentSelectionChecked( event );

        if ( !( selection instanceof IStructuredSelection ) || selection.isEmpty() )
        {
            return null;
        }

        try
        {
            final String location = JDIHelpers.getLocation( ( (IStructuredSelection) selection ).getFirstElement() );

            if ( location == null )
            {
                return null;
            }

            final String name = new File( location ).getName();

            List<IEditorInput> inputs = new MetaInfMavenScanner<IEditorInput>()
            {
                @Override
                protected IEditorInput visitFile( File file )
                    throws IOException
                {
                    return toEditorInput( name, new FileInputStream( file ) );
                }

                @Override
                protected IEditorInput visitJarEntry( JarFile jar, JarEntry entry )
                    throws IOException
                {
                    return toEditorInput( name, jar.getInputStream( entry ) );
                }

            }.scan( location, "pom.xml" );

            if ( inputs.isEmpty() )
            {
                return null;
            }

            OpenPomAction.openEditor( inputs.get( 0 ), "pom.xml" );
        }
        catch ( CoreException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    static MavenStorageEditorInput toEditorInput( String name, InputStream is )
        throws IOException
    {
        try
        {
            return new MavenStorageEditorInput( name, name, null, IOUtil.toByteArray( is ) );
        }
        finally
        {
            IOUtil.close( is );
        }
    }
}