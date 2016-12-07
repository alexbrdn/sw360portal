/*
 * Copyright (c) Bosch Software Innovations GmbH 2016.
 * With modifications by Siemens AG, 2016.
 * Part of the SW360 Portal Project.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.sw360.cvesearch.datasource;

import org.eclipse.sw360.cvesearch.datasource.matcher.Match;
import org.eclipse.sw360.cvesearch.service.CveSearchHandler;
import org.eclipse.sw360.datahandler.common.CommonUtils;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.eclipse.sw360.cvesearch.datasource.CveSearchDataTestHelper.isUrlReachable;
import static org.junit.Assert.*;

public class CveSearchGuesserTest {

    private CveSearchGuesser cveSearchGuesser;
    private CveSearchApiImpl cveSearchApi;

    @Before
    public void setup() throws IOException {
        Properties props = CommonUtils.loadProperties(CveSearchGuesserTest.class, "/cvesearch.properties");
        String host = props.getProperty(CveSearchHandler.CVESEARCH_HOST_PROPERTY, "https://cve.circl.lu");
        Assume.assumeTrue("CVE Search host is reachable", isUrlReachable(host));
        cveSearchApi = new CveSearchApiImpl(host);
        this.cveSearchGuesser = new CveSearchGuesser(cveSearchApi);
    }

    @Test
    public void getBestZERO() throws Exception {
        assert(cveSearchGuesser.getBest(Collections.emptyList(), Integer.MAX_VALUE).size() == 0);
    }

    @Test
    public void guessVendorTestApacheFullMatch() throws Exception {
        String apache = "apache";

        String result2 = this.cveSearchGuesser.guessVendors(apache).get(0).getNeedle();
        assert(result2.equals(apache));
    }

    @Test
    public void guessProductTestMavenFullMatch() throws Exception {
        String apache = "apache";
        String maven  = "maven";

        String result2 = this.cveSearchGuesser.guessProducts(apache,maven).get(0).getNeedle();
        assert(result2.equals(maven));
    }

    @Test
    public void guessProductTestApacheFullMatchWithThreshold() throws Exception {
        String apache = "apache";
        this.cveSearchGuesser.setVendorThreshold(5);
        List<Match> result = this.cveSearchGuesser.guessVendors(apache);
        assert(result.size() > 1);
    }
}