package com.lucilu.marvelpedia.base.data.common.network;

import android.support.annotation.NonNull;

import com.lucilu.marvelpedia.base.testcommon.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.TestSubscriber;
import polanski.option.Option;
import polanski.option.Unit;

import static org.assertj.core.api.Assertions.assertThat;
import static polanski.option.Option.ofObj;

public class FetcherTest extends BaseTest {

    private Fetcher<String, Object> fetcher;

    private PublishProcessor<Object> fetchProcessor;

    private AtomicInteger timesStored;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        timesStored = new AtomicInteger();
        fetchProcessor = PublishProcessor.create();

        fetcher = new Fetcher<String, Object>() {
            @Override
            protected void store(@NonNull final Object model) {
                timesStored.incrementAndGet();
            }

            @NonNull
            @Override
            protected Flowable<Object> getFetchSingle(@NonNull final Option<String> params) {
                return fetchProcessor;
            }
        };
    }

    @Test
    public void resultsAreNotStoredIfFetchSingleDoesNotEmit() {
        fetcher.fetchSingle(ofObj("Param1"));

        assertThat(timesStored.get()).isEqualTo(0);
    }

    @Test
    public void multipleFetchesTriggerStoreOnceIfTheyHappenBeforeFirstFetchHasCompleted() {
        fetcher.fetchSingle(ofObj("Param1"));
        fetcher.fetchSingle(ofObj("Param1"));
        fetcher.fetchSingle(ofObj("Param1"));
        fetcher.fetchSingle(ofObj("Param1"));

        fetchProcessor.onNext(new Object());

        assertThat(timesStored.get()).isEqualTo(1);
    }

    @Test
    public void fetchesWithDifferentParamsTriggerStoreIndependently() {
        fetcher.fetchSingle(ofObj("Param1"));
        fetcher.fetchSingle(ofObj("Param2"));

        fetchProcessor.onNext(new Object());

        assertThat(timesStored.get()).isEqualTo(2);
    }

    @Test
    public void newStoreIsTriggeredIfSecondFetchComesWhenFirstIsOver() {
        fetcher.fetchSingle(ofObj("Param1"));
        fetchProcessor.onNext(new Object());
        fetcher.fetchSingle(ofObj("Param1"));
        fetchProcessor.onNext(new Object());

        assertThat(timesStored.get()).isEqualTo(2);
    }

    @Test
    public void fetchSingleDoesNotEmitIfFetchDoesNotFinish() {
        TestSubscriber<Unit> ts = new TestSubscriber<>();
        fetcher.fetchSingle(ofObj("Param1")).subscribe(ts);

        ts.assertNoValues();
        ts.assertSubscribed();
    }

    @Test
    public void fetchSingleEmitsUnitWhenFetchFinishes() {
        TestSubscriber<Unit> ts = new TestSubscriber<>();
        fetcher.fetchSingle(ofObj("Param1")).subscribe(ts);

        fetchProcessor.onNext(new Object());
        fetchProcessor.onComplete();

        ts.assertValue(Unit.DEFAULT);
        ts.assertSubscribed();
    }

    @Test
    public void fetchSingleEmitsErrorWhenFetchErrors() {
        TestSubscriber<Unit> ts = new TestSubscriber<>();
        Throwable throwable = Mockito.mock(Throwable.class);
        fetcher.fetchSingle(ofObj("Param1")).subscribe(ts);

        fetchProcessor.onError(throwable);

        ts.assertError(throwable);
    }
}