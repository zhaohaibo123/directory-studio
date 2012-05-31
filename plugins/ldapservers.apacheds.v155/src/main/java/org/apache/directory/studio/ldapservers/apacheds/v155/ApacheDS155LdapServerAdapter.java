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
package org.apache.directory.studio.ldapservers.apacheds.v155;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.directory.studio.apacheds.configuration.editor.ServerConfigurationEditor;
import org.apache.directory.studio.apacheds.configuration.model.ServerXmlIOException;
import org.apache.directory.studio.apacheds.configuration.model.v155.ServerConfigurationV155;
import org.apache.directory.studio.apacheds.configuration.model.v155.ServerXmlIOV155;
import org.apache.directory.studio.common.core.jobs.StudioProgressMonitor;
import org.apache.directory.studio.common.ui.filesystem.PathEditorInput;
import org.apache.directory.studio.ldapservers.LdapServersManager;
import org.apache.directory.studio.ldapservers.LdapServersUtils;
import org.apache.directory.studio.ldapservers.model.LdapServer;
import org.apache.directory.studio.ldapservers.model.LdapServerAdapter;
import org.apache.mina.util.AvailablePortFinder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;


/**
 * This class implements an LDAP Server Adapter for ApacheDS version 1.5.5.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class ApacheDS155LdapServerAdapter implements LdapServerAdapter
{
    // Various strings constants used in paths
    private static final String SERVER_XML = "server.xml"; //$NON-NLS-1$
    private static final String LOG4J_PROPERTIES = "log4j.properties"; //$NON-NLS-1$
    private static final String RESOURCES = "resources"; //$NON-NLS-1$
    private static final String LIBS = "libs"; //$NON-NLS-1$
    private static final String CONF = "conf"; //$NON-NLS-1$

    /** The array of libraries names */
    private static final String[] libraries = new String[]
        { "antlr-2.7.7.jar", "apacheds-bootstrap-extract-1.5.5.jar", "apacheds-bootstrap-partition-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "apacheds-core-1.5.5.jar", "apacheds-core-avl-1.5.5.jar", "apacheds-core-constants-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "apacheds-core-entry-1.5.5.jar", "apacheds-core-jndi-1.5.5.jar", "apacheds-core-shared-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "apacheds-interceptor-kerberos-1.5.5.jar", "apacheds-jdbm-1.5.5.jar", "apacheds-jdbm-store-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "apacheds-kerberos-shared-1.5.5.jar", "apacheds-launcher-1.5.0.jar", //$NON-NLS-1$ //$NON-NLS-2$
            "apacheds-protocol-changepw-1.5.5.jar", "apacheds-protocol-dns-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$
            "apacheds-protocol-kerberos-1.5.5.jar", "apacheds-protocol-ldap-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$
            "apacheds-protocol-ntp-1.5.5.jar", "apacheds-protocol-shared-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$
            "apacheds-schema-bootstrap-1.5.5.jar", "apacheds-schema-extras-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$
            "apacheds-schema-registries-1.5.5.jar", "apacheds-server-jndi-1.5.5.jar", "apacheds-server-xml-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "apacheds-utils-1.5.5.jar", "apacheds-xbean-spring-1.5.5.jar", "apacheds-xdbm-base-1.5.5.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "apacheds-xdbm-search-1.5.5.jar", "apacheds-xdbm-tools-1.5.5.jar", "bcprov-jdk15-140.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "commons-cli-1.2.jar", "commons-collections-3.2.1.jar", "commons-daemon-1.0.1.jar", "commons-io-1.4.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "commons-lang-2.4.jar", "daemon-bootstrappers-1.1.6.jar", "jcl-over-slf4j-1.5.6.jar", "log4j-1.2.14.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "mina-core-2.0.0-M6.jar", "shared-asn1-0.9.15.jar", "shared-asn1-codec-0.9.15.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "shared-cursor-0.9.15.jar", "shared-ldap-0.9.15.jar", "shared-ldap-constants-0.9.15.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "slf4j-api-1.5.6.jar", "slf4j-log4j12-1.5.6.jar", "spring-beans-2.5.6.SEC01.jar", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "spring-context-2.5.6.SEC01.jar", "spring-core-2.5.6.SEC01.jar", "xbean-spring-3.5.jar" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$


    /**
     * {@inheritDoc}
     */
    public void add( LdapServer server, StudioProgressMonitor monitor ) throws Exception
    {
        // Getting the bundle associated with the plugin
        Bundle bundle = ApacheDS155Plugin.getDefault().getBundle();

        // Verifying and copying ApacheDS 1.5.5 libraries
        LdapServersUtils.verifyAndCopyLibraries( bundle, new Path( RESOURCES ).append( LIBS ),
            getServerLibrariesFolder(), libraries, monitor, "verifying and copying ApacheDS 1.5.5 libraries" );

        // Creating server folder structure
        monitor.subTask( "creating server folder structure" );
        File serverFolder = LdapServersManager.getServerFolder( server ).toFile();
        File confFolder = new File( serverFolder, CONF );
        confFolder.mkdir();
        File ldifFolder = new File( serverFolder, "ldif" ); //$NON-NLS-1$
        ldifFolder.mkdir();
        File logFolder = new File( serverFolder, "log" ); //$NON-NLS-1$
        logFolder.mkdir();
        File partitionFolder = new File( serverFolder, "partitions" ); //$NON-NLS-1$
        partitionFolder.mkdir();

        // Copying configuration files
        monitor.subTask( "copying configuration files" );
        IPath resourceConfFolderPath = new Path( RESOURCES ).append( CONF );
        LdapServersUtils.copyResource( bundle, resourceConfFolderPath.append( SERVER_XML ), new File( confFolder,
            SERVER_XML ) );
        LdapServersUtils.copyResource( bundle, resourceConfFolderPath.append( LOG4J_PROPERTIES ), new File( confFolder,
            LOG4J_PROPERTIES ) );

        // Creating an empty log file
        new File( logFolder, "apacheds.log" ).createNewFile(); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     */
    public void delete( LdapServer server, StudioProgressMonitor monitor ) throws Exception
    {
        // Nothing to do (nothing more than the default behavior of 
        // the delete action before this method is called)
    }


    /**
     * {@inheritDoc}
     */
    public void openConfiguration( final LdapServer server, final StudioProgressMonitor monitor ) throws Exception
    {
        // Opening the editor
        Display.getDefault().syncExec( new Runnable()
        {
            public void run()
            {
                try
                {
                    PathEditorInput input = new PathEditorInput( LdapServersManager.getServerFolder( server )
                        .append( CONF ).append( SERVER_XML ) );
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                        .openEditor( input, ServerConfigurationEditor.ID );
                }
                catch ( PartInitException e )
                {
                    monitor.reportError( e );
                }
            }
        } );
    }


    /**
     * {@inheritDoc}
     */
    public void start( LdapServer server, StudioProgressMonitor monitor ) throws Exception
    {
        // Getting the bundle associated with the plugin
        Bundle bundle = ApacheDS155Plugin.getDefault().getBundle();

        // Verifying and copying ApacheDS 1.5.5 libraries
        LdapServersUtils.verifyAndCopyLibraries( bundle, new Path( RESOURCES ).append( LIBS ),
            getServerLibrariesFolder(), libraries, monitor, "verifying and copying ApacheDS 1.5.5 libraries" );
        
        // Starting the console printer thread
        LdapServersUtils.startConsolePrinterThread( server );

        // Launching ApacheDS
        ILaunch launch = LdapServersUtils.launchApacheDS( server, getServerLibrariesFolder(), libraries );

        // Starting the "terminate" listener thread
        LdapServersUtils.startTerminateListenerThread( server, launch );

        // Running the startup listener watchdog
        LdapServersUtils.runStartupListenerWatchdog( server, getTestingPort( server ) );
    }


    /**
     * {@inheritDoc}
     */
    public void stop( LdapServer server, StudioProgressMonitor monitor ) throws Exception
    {
        // Stopping the console printer thread
        LdapServersUtils.stopConsolePrinterThread( server );

        // Terminating the launch configuration
        LdapServersUtils.terminateLaunchConfiguration( server );
    }


    /**
     * Gets the path to the server libraries folder.
     *
     * @return
     *      the path to the server libraries folder
     */
    private static IPath getServerLibrariesFolder()
    {
        return ApacheDS155Plugin.getDefault().getStateLocation().append( LIBS );
    }


    /**
    * Gets the server configuration.
    *
    * @param server
    *      the server
    * @return
    *      the associated server configuration
    * @throws ServerXmlIOException 
    * @throws FileNotFoundException 
    */
    public static ServerConfigurationV155 getServerConfiguration( LdapServer server ) throws ServerXmlIOException,
        FileNotFoundException
    {
        InputStream fis = new FileInputStream( LdapServersManager.getServerFolder( server ).append( CONF )
            .append( SERVER_XML ).toFile() );

        ServerXmlIOV155 serverXmlIOV155 = new ServerXmlIOV155();
        return ( ServerConfigurationV155 ) serverXmlIOV155.parse( fis );
    }


    /**
     * Gets the testing port.
     *
     * @param configuration
     *      the 1.5.5 server configuration
     * @return
     *      the testing port
     * @throws IOException 
     * @throws ServerXmlIOException 
     */
    private int getTestingPort( LdapServer server ) throws ServerXmlIOException, IOException
    {
        ServerConfigurationV155 configuration = getServerConfiguration( server );

        // LDAP
        if ( configuration.isEnableLdap() )
        {
            return configuration.getLdapPort();
        }
        // LDAPS
        else if ( configuration.isEnableLdaps() )
        {
            return configuration.getLdapsPort();
        }
        // Kerberos
        else if ( configuration.isEnableKerberos() )
        {
            return configuration.getKerberosPort();
        }
        // DNS
        else if ( configuration.isEnableDns() )
        {
            return configuration.getDnsPort();
        }
        // NTP
        else if ( configuration.isEnableNtp() )
        {
            return configuration.getNtpPort();
        }
        // ChangePassword
        else if ( configuration.isEnableChangePassword() )
        {
            return configuration.getChangePasswordPort();
        }
        else
        {
            return 0;
        }
    }


    /**
     * {@inheritDoc}
     */
    public String[] checkPortsBeforeServerStart( LdapServer server ) throws Exception
    {
        List<String> alreadyInUseProtocolPortsList = new ArrayList<String>();

        ServerConfigurationV155 configuration = getServerConfiguration( server );

        // LDAP
        if ( configuration.isEnableLdap() )
        {
            if ( !AvailablePortFinder.available( configuration.getLdapPort() ) )
            {
                alreadyInUseProtocolPortsList
                    .add( NLS.bind(
                        Messages.getString( "ApacheDS155LdapServerAdapter.LDAPPort" ), new Object[] { configuration.getLdapPort() } ) ); //$NON-NLS-1$
            }
        }

        // LDAPS
        if ( configuration.isEnableLdaps() )
        {
            if ( !AvailablePortFinder.available( configuration.getLdapsPort() ) )
            {
                alreadyInUseProtocolPortsList
                    .add( NLS.bind(
                        Messages.getString( "ApacheDS155LdapServerAdapter.LDAPSPort" ), new Object[] { configuration.getLdapsPort() } ) ); //$NON-NLS-1$
            }
        }

        // Kerberos
        if ( configuration.isEnableKerberos() )
        {
            if ( !AvailablePortFinder.available( configuration.getKerberosPort() ) )
            {
                alreadyInUseProtocolPortsList
                    .add( NLS
                        .bind(
                            Messages.getString( "ApacheDS155LdapServerAdapter.KerberosPort" ), new Object[] { configuration.getKerberosPort() } ) ); //$NON-NLS-1$
            }
        }

        // DNS
        if ( configuration.isEnableDns() )
        {
            if ( !AvailablePortFinder.available( configuration.getDnsPort() ) )
            {
                alreadyInUseProtocolPortsList
                    .add( NLS.bind(
                        Messages.getString( "ApacheDS155LdapServerAdapter.DNSPort" ), new Object[] { configuration.getDnsPort() } ) ); //$NON-NLS-1$
            }
        }

        // NTP
        if ( configuration.isEnableNtp() )
        {
            if ( !AvailablePortFinder.available( configuration.getNtpPort() ) )
            {
                alreadyInUseProtocolPortsList.add( NLS.bind(
                    Messages.getString( "ApacheDS155LdapServerAdapter.NTPPort" ), new Object[] //$NON-NLS-1$
                    { configuration.getNtpPort() } ) );
            }
        }

        // Change Password
        if ( configuration.isEnableChangePassword() )
        {
            if ( !AvailablePortFinder.available( configuration.getChangePasswordPort() ) )
            {
                alreadyInUseProtocolPortsList
                    .add( NLS
                        .bind(
                            Messages.getString( "ApacheDS155LdapServerAdapter.ChangePasswordPort" ), new Object[] { configuration.getChangePasswordPort() } ) ); //$NON-NLS-1$
            }
        }

        return alreadyInUseProtocolPortsList.toArray( new String[0] );
    }
}
