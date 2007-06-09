/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */

package org.apache.directory.ldapstudio.browser.ui.actions;


import org.apache.directory.ldapstudio.browser.common.actions.BrowserAction;
import org.apache.directory.ldapstudio.browser.ui.BrowserUIConstants;
import org.apache.directory.ldapstudio.browser.ui.BrowserUIPlugin;
import org.apache.directory.ldapstudio.browser.ui.wizards.ExportCsvWizard;
import org.apache.directory.ldapstudio.browser.ui.wizards.ExportDsmlWizard;
import org.apache.directory.ldapstudio.browser.ui.wizards.ExportExcelWizard;
import org.apache.directory.ldapstudio.browser.ui.wizards.ExportLdifWizard;
import org.apache.directory.ldapstudio.browser.ui.wizards.ImportDsmlWizard;
import org.apache.directory.ldapstudio.browser.ui.wizards.ImportLdifWizard;
import org.apache.directory.studio.ldapbrowser.core.model.IConnection;
import org.apache.directory.studio.ldapbrowser.core.model.IEntry;
import org.apache.directory.studio.ldapbrowser.core.model.ISearch;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;


/**
 * This class implements Import/Export Actions for LDIF, CSV, EXCEL and DSML.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class ImportExportAction extends BrowserAction
{
    /**
     * LDIF Import Type
     */
    public static final int TYPE_IMPORT_LDIF = 0;

    /**
     * LDIF Export Type
     */
    public static final int TYPE_EXPORT_LDIF = 1;

    /**
     * CSV Export Type
     */
    public static final int TYPE_EXPORT_CSV = 2;

    /**
     * EXCEL Export Type
     */
    public static final int TYPE_EXPORT_EXCEL = 3;

    /**
     * DSML Import Type
     */
    public static final int TYPE_IMPORT_DSML = 4;

    /**
     * DSML Export Type
     */
    public static final int TYPE_EXPORT_DSML = 5;

    private int type;


    /**
     * Creates a new instance of ImportExportAction.
     *
     * @param type
     *      the type of Import/Export
     */
    public ImportExportAction( int type )
    {
        super();
        this.type = type;
    }


    /**
     * {@inheritDoc}
     */
    public String getText()
    {
        if ( this.type == TYPE_IMPORT_LDIF )
        {
            return "LDIF Import...";
        }
        else if ( this.type == TYPE_EXPORT_LDIF )
        {
            return "LDIF Export...";
        }
        else if ( this.type == TYPE_EXPORT_CSV )
        {
            return "CSV Export...";
        }
        else if ( this.type == TYPE_EXPORT_EXCEL )
        {
            return "Excel Export...";
        }
        else if ( this.type == TYPE_IMPORT_DSML )
        {
            return "DSML Import...";
        }
        else if ( this.type == TYPE_EXPORT_DSML )
        {
            return "DSML Export...";
        }
        else
        {
            return "Export...";
        }
    }


    /**
     * {@inheritDoc}
     */
    public ImageDescriptor getImageDescriptor()
    {
        if ( this.type == TYPE_IMPORT_LDIF )
        {
            return BrowserUIPlugin.getDefault().getImageDescriptor( BrowserUIConstants.IMG_IMPORT_LDIF );
        }
        else if ( this.type == TYPE_EXPORT_LDIF )
        {
            return BrowserUIPlugin.getDefault().getImageDescriptor( BrowserUIConstants.IMG_EXPORT_LDIF );
        }
        else if ( this.type == TYPE_EXPORT_CSV )
        {
            return BrowserUIPlugin.getDefault().getImageDescriptor( BrowserUIConstants.IMG_EXPORT_CSV );
        }
        else if ( this.type == TYPE_EXPORT_EXCEL )
        {
            return BrowserUIPlugin.getDefault().getImageDescriptor( BrowserUIConstants.IMG_EXPORT_XLS );
        }
        else if ( this.type == TYPE_IMPORT_DSML )
        {
            return BrowserUIPlugin.getDefault().getImageDescriptor( BrowserUIConstants.IMG_IMPORT_DSML );
        }
        else if ( this.type == TYPE_EXPORT_DSML )
        {
            return BrowserUIPlugin.getDefault().getImageDescriptor( BrowserUIConstants.IMG_EXPORT_DSML );
        }
        else
        {
            return null;
        }
    }


    /**
     * {@inheritDoc}
     */
    public String getCommandId()
    {
        return null;
    }


    /**
     * {@inheritDoc}
     */
    public boolean isEnabled()
    {
        return getEntry() != null || getConnection() != null || getSearch() != null || getConnectionInput() != null;

    }


    /**
     * {@inheritDoc}
     */
    public void run()
    {
        IWizard wizard = null;

        if ( this.type == TYPE_IMPORT_LDIF )
        {
            if ( getEntry() != null )
            {
                wizard = new ImportLdifWizard( getEntry().getConnection() );
            }
            else if ( getSearch() != null )
            {
                wizard = new ImportLdifWizard( getSearch().getConnection() );
            }
            else if ( getConnectionInput() != null )
            {
                wizard = new ImportLdifWizard( getConnectionInput() );
            }
            else if ( getConnection() != null )
            {
                wizard = new ImportLdifWizard( getConnection() );
            }
        }
        else if ( this.type == TYPE_IMPORT_DSML )
        {
            if ( getEntry() != null )
            {
                wizard = new ImportDsmlWizard( getEntry().getConnection() );
            }
            else if ( getSearch() != null )
            {
                wizard = new ImportDsmlWizard( getSearch().getConnection() );
            }
            else if ( getConnectionInput() != null )
            {
                wizard = new ImportDsmlWizard( getConnectionInput() );
            }
            else if ( getConnection() != null )
            {
                wizard = new ImportDsmlWizard( getConnection() );
            }
        }
        else if ( this.type == TYPE_EXPORT_LDIF )
        {
            wizard = new ExportLdifWizard();
        }
        else if ( this.type == TYPE_EXPORT_CSV )
        {
            wizard = new ExportCsvWizard();
        }
        else if ( this.type == TYPE_EXPORT_EXCEL )
        {
            wizard = new ExportExcelWizard();
        }
        else if ( this.type == TYPE_EXPORT_DSML )
        {
            wizard = new ExportDsmlWizard();
        }

        if ( wizard != null )
        {
            WizardDialog dialog = new WizardDialog( getShell(), wizard );
            dialog.setBlockOnOpen( true );
            dialog.create();
            dialog.open();
        }

    }


    /**
     * Gets the selected Entry.
     *
     * @return
     *      the selected Entry
     */
    protected IEntry getEntry()
    {
        IEntry entry = null;
        if ( getSelectedEntries().length > 0 )
        {
            entry = getSelectedEntries()[0];
        }
        else if ( getSelectedSearchResults().length > 0 )
        {
            entry = getSelectedSearchResults()[0].getEntry();
        }
        else if ( getSelectedBookmarks().length > 0 )
        {
            entry = getSelectedBookmarks()[0].getEntry();
        }

        return entry != null && entry.getConnection().isOpened() ? entry : null;
    }


    /**
     * Gets the Connection.
     *
     * @return
     *      the Connection
     */
    protected IConnection getConnection()
    {
        return getSelectedConnections().length > 0 && getSelectedConnections()[0].isOpened() ? getSelectedConnections()[0]
            : null;
    }


    /**
     * Gets the Search.
     *
     * @return
     *      the Search
     */
    protected ISearch getSearch()
    {
        return getSelectedSearches().length > 0 && getSelectedSearches()[0].getConnection().isOpened() ? getSelectedSearches()[0]
            : null;
    }


    /**
     * Gets the Connection Input.
     *
     * @return
     *      the Connection Input
     */
    protected IConnection getConnectionInput()
    {

        if ( getInput() != null && ( getInput() instanceof IConnection ) && ( ( IConnection ) getInput() ).isOpened() )
        {
            return ( IConnection ) getInput();
        }
        else
        {
            return null;
        }
    }
}
