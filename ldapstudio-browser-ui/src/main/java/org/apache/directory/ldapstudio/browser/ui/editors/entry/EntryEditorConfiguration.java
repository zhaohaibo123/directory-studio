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

package org.apache.directory.ldapstudio.browser.ui.editors.entry;


import org.apache.directory.ldapstudio.browser.ui.widgets.entryeditor.EntryEditorWidgetConfiguration;
import org.apache.directory.ldapstudio.browser.ui.widgets.entryeditor.EntryEditorWidgetFilter;


/**
 * The BrowserConfiguration contains the content provider, 
 * label provider, sorter, filter the context menu manager and the
 * preferences for the entry editor. 
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
public class EntryEditorConfiguration extends EntryEditorWidgetConfiguration
{

    /**
     * Creates a new instance of EntryEditorConfiguration.
     */
    public EntryEditorConfiguration()
    {
    }


    /**
     * {@inheritDoc}
     */
    public EntryEditorWidgetFilter getFilter()
    {
        if ( filter == null )
        {
            filter = new EntryEditorFilter( getPreferences() );
        }

        return filter;
    }

}
