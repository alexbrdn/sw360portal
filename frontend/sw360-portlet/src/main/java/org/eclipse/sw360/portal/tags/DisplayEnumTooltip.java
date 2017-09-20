/*
 * Copyright Siemens AG, 2017. Part of the SW360 Portal Project.
 *
 * SPDX-License-Identifier: EPL-1.0
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.sw360.portal.tags;

import org.apache.thrift.TEnum;
import org.eclipse.sw360.datahandler.common.ThriftEnumUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This displays a list
 *
 * @author thomas.maier@evosoft.com
 */
public class DisplayEnumTooltip extends SimpleTagSupport {

    private static final String SW360_TOOLTIP = "sw360-tt";

    private TEnum value;

    public void setValue(TEnum value) {
        this.value = value;
    }

    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(getSpanElement());
    }

    private String getSpanElement() {
        return "<span class='" + SW360_TOOLTIP + " "
                + SW360_TOOLTIP + "-" + value.getClass().getSimpleName() + "-" + value.toString() + "'>"
                + ThriftEnumUtils.enumToString(value) + "</span>";
    }
}
