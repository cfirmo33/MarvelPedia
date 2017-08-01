package com.lucilu.marvelpedia.base.testcommon;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lucia on 01/08/2017.
 */

public class RxSchedulersOverrideRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(__ -> Schedulers.trampoline());
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
