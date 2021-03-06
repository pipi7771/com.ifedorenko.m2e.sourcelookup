/*******************************************************************************
 * Copyright (c) 2014 Igor Fedorenko
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Igor Fedorenko - initial API and implementation
 *******************************************************************************/
package com.ifedorenko.m2e.sourcelookup.ui.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ifedorenko.m2e.sourcelookup.internal.SourceLookupActivator;

public class SourceLookupPreferencePage
    extends PreferencePage
    implements IWorkbenchPreferencePage
{
    private Text vmArguments;

    private Text launchFileAttribute;

    private Text equinoxClasspath;

    public SourceLookupPreferencePage()
    {
        setMessage( "Manual configuration of dynamic source lookup" );
        noDefaultAndApplyButton();
    }

    public void init( IWorkbench workbench )
    {
    }

    @Override
    protected Control createContents( Composite parent )
    {
        Composite composite = new Composite( parent, SWT.NONE )
        {
            @Override
            public Point computeSize( int wHint, int hHint, boolean changed )
            {
                return new Point( 0, 0 );
            }
        };

        GridLayout gl_composite = new GridLayout( 1, false );
        gl_composite.marginWidth = 0;
        gl_composite.marginHeight = 0;
        composite.setLayout( gl_composite );

        try
        {
            Label lblVMArguments = new Label( composite, SWT.NONE );
            lblVMArguments.setText( "VM arguments:" );

            vmArguments = new Text( composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP );
            vmArguments.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false, 1, 1 ) );
            vmArguments.setText( SourceLookupActivator.getDefault().getJavaagentString() );

            Label lblEqiunoxFrameworkClasspath = new Label( composite, SWT.NONE );
            lblEqiunoxFrameworkClasspath.setText( "Eqiunox framework classpath entry:" );

            equinoxClasspath = new Text( composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP );
            equinoxClasspath.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
            equinoxClasspath.setText( SourceLookupActivator.getDefault().getJavaagentLocation() );

            Label lblLaunchFileAttribute = new Label( composite, SWT.NONE );
            lblLaunchFileAttribute.setText( ".launch file attribute:" );

            launchFileAttribute = new Text( composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP );
            launchFileAttribute.setText( "<stringAttribute key=\"org.eclipse.debug.core.source_locator_id\" value=\"com.ifedorenko.m2e.sourcelookupDirector\"/>\n" );
            launchFileAttribute.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 1, 1 ) );
        }
        catch ( CoreException e )
        {
            vmArguments.setText( e.getStatus().getMessage() );
        }

        return composite;
    }
}