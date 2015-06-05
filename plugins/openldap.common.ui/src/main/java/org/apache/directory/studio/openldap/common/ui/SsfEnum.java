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
package org.apache.directory.studio.openldap.common.ui;

import org.apache.directory.api.util.Strings;

/**
 * An enumeratuon of all the possible SSF freatures.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public enum SsfEnum
{
    SSF( "ssf" ),
    TRANSPORT( "transport" ),
    TLS( "tls" ),
    SASL( "sasl" ),
    UPDATE_SSF( "update_ssf" ),
    UPDATE_TRANSPORT( "update_transport" ),
    UPDATE_TLS( "update_tls" ),
    UPDATE_SASL( "update_sasl" ),
    SIMPLE_BIND( "simple_bind" ),
    NONE( "" );
    
    /** The associated Text */
    private String text;
    
    /**
     * Creates an SsfEnum instance
     */
    private SsfEnum( String text )
    {
        this.text = text;
    }

    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }
    
    
    /**
     * Retrieve the instance associated to a String. Return NONE if not found.
     * 
     * @param feature The feature to retrieve
     * @return The SsfEnum instance found, or NONE.
     */
    public static SsfEnum getSsf( String feature )
    {
        String ssfText = Strings.toLowerCase( feature );
        
        if ( SSF.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( TRANSPORT.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( TLS.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( SASL.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( SIMPLE_BIND.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( UPDATE_SASL.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( UPDATE_SSF.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( UPDATE_TLS.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        if ( UPDATE_TRANSPORT.text.equals( ssfText ) )
        {
            return SSF;
        }
        
        // Default...
        return NONE;
    }
}
