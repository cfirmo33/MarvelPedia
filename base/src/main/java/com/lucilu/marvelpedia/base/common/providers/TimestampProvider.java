package com.lucilu.marvelpedia.base.common.providers;

import javax.inject.Inject;

/**
 Class to be able to test timestamp related features. Inject this instead of using System.currentTimeMillis()
 */
public class TimestampProvider {

    @Inject
    public TimestampProvider() {}

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
