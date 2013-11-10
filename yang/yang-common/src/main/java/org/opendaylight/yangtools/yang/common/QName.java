/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.common;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.opendaylight.yangtools.concepts.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The QName from XML consists of local name of element and XML namespace, but
 * for our use, we added module revision to it.
 * 
 * In YANG context QName is full name of defined node, type, procedure or
 * notification. QName consists of XML namespace, YANG model revision and local
 * name of defined type. It is used to prevent name clashes between nodes with
 * same local name, but from different schemas.
 * 
 * <ul>
 * <li><b>XMLNamespace</b> - the namespace assigned to the YANG module which
 * defined element, type, procedure or notification.</li>
 * <li><b>Revision</b> - the revision of the YANG module which describes the
 * element</li>
 * <li><b>LocalName</b> - the YANG schema identifier which were defined for this
 * node in the YANG module</li>
 * </ul>
 * 
 * 
 */
public final class QName implements Immutable,Serializable {

    private static final long serialVersionUID = 5398411242927766414L;

    protected static final Logger LOGGER = LoggerFactory.getLogger(QName.class);

    private static final SimpleDateFormat REVISION_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final URI namespace;
    private final String localName;
    private final String prefix;
    private final String formattedRevision;
    private final Date revision;

    /**
     * QName Constructor.
     * 
     * @param namespace
     *            the namespace assigned to the YANG module
     * @param revision
     *            the revision of the YANG module
     * @param prefix
     *            locally defined prefix assigned to local name
     * @param localName
     *            YANG schema identifier
     */
    public QName(URI namespace, Date revision, String prefix, String localName) {
        this.namespace = namespace;
        this.localName = localName;
        this.revision = revision;
        this.prefix = prefix;
        if(revision != null) {
            this.formattedRevision = REVISION_FORMAT.format(revision);
        } else {
            this.formattedRevision = null;
        }
    }

    /**
     * QName Constructor.
     * 
     * @param namespace
     *            the namespace assigned to the YANG module
     * @param localName
     *            YANG schema identifier
     */
    public QName(URI namespace, String localName) {
        this(namespace, null, "", localName);
    }

    /**
     * QName Constructor.
     * 
     * @param namespace
     *            the namespace assigned to the YANG module
     * @param revision
     *            the revision of the YANG module
     * @param localName
     *            YANG schema identifier
     */
    public QName(URI namespace, Date revision, String localName) {
        this(namespace, revision, null, localName);
    }

    public QName(QName base, String localName) {
        this(base.getNamespace(), base.getRevision(), base.getPrefix(), localName);
    }

    /**
     * Returns XMLNamespace assigned to the YANG module.
     * 
     * @return XMLNamespace assigned to the YANG module.
     */
    public URI getNamespace() {
        return namespace;
    }

    /**
     * Returns YANG schema identifier which were defined for this node in the
     * YANG module
     * 
     * @return YANG schema identifier which were defined for this node in the
     *         YANG module
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Returns revision of the YANG module if the module has defined revision,
     * otherwise returns <code>null</code>
     * 
     * @return revision of the YANG module if the module has defined revision,
     *         otherwise returns <code>null</code>
     */
    public Date getRevision() {
        return revision;
    }

    /**
     * Returns locally defined prefix assigned to local name
     * 
     * @return locally defined prefix assigned to local name
     */
    public String getPrefix() {
        return prefix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((localName == null) ? 0 : localName.hashCode());
        result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
        result = prime * result + ((formattedRevision == null) ? 0 : formattedRevision.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        QName other = (QName) obj;
        if (localName == null) {
            if (other.localName != null) {
                return false;
            }
        } else if (!localName.equals(other.localName)) {
            return false;
        }
        if (namespace == null) {
            if (other.namespace != null) {
                return false;
            }
        } else if (!namespace.equals(other.namespace)) {
            return false;
        }
        if (formattedRevision == null) {
            if (other.formattedRevision != null) {
                return false;
            }
        } else if (!revision.equals(other.revision)) {
            return false;
        }
        return true;
    }
    
    
    public static QName create(QName base, String localName){
        return new QName(base, localName);
    }
    
    public static QName create(URI namespace, Date revision, String localName){
        return new QName(namespace, revision, localName);
    }
    
    
    public static QName create(String namespace, String revision, String localName) throws IllegalArgumentException{
        try {
            URI namespaceUri = new URI(namespace);
            Date revisionDate = REVISION_FORMAT.parse(revision);
            return create(namespaceUri, revisionDate, localName);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Revision is not in supported format", pe);
        } catch (URISyntaxException ue) {
            throw new IllegalArgumentException("Namespace is is not valid URI", ue);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (namespace != null) {
            sb.append("(" + namespace);

            if (revision != null) {
                sb.append("?revision=" + REVISION_FORMAT.format(revision));
            }
            sb.append(")");
        }
        sb.append(localName);
        return sb.toString();
    }

    /**
     * Returns a namespace in form defined by section 5.6.4. of {@link https
     * ://tools.ietf.org/html/rfc6020}, if namespace is not correctly defined,
     * the method will return <code>null</code> <br>
     * example "http://example.acme.com/system?revision=2008-04-01"
     * 
     * @return namespace in form defined by section 5.6.4. of {@link https
     *         ://tools.ietf.org/html/rfc6020}, if namespace is not correctly
     *         defined, the method will return <code>null</code>
     * 
     */
    URI getRevisionNamespace() {

        if (namespace == null) {
            return null;
        }

        String query = "";
        if (revision != null) {
            query = "revision=" + formattedRevision;
        }

        URI compositeURI = null;
        try {
            compositeURI = new URI(namespace.getScheme(), namespace.getUserInfo(), namespace.getHost(),
                    namespace.getPort(), namespace.getPath(), query, namespace.getFragment());
        } catch (URISyntaxException e) {
            LOGGER.error("", e);
        }
        return compositeURI;
    }
    
    public String getFormattedRevision() {
        return formattedRevision;
    }
}
