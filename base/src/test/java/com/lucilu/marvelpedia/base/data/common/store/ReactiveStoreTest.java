package com.lucilu.marvelpedia.base.data.common.store;

import com.lucilu.marvelpedia.base.data.common.store.ReactivePersistence.Memory;
import com.lucilu.marvelpedia.base.testcommon.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.subscribers.TestSubscriber;
import polanski.option.Option;

import static org.assertj.core.api.Assertions.assertThat;
import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

public class ReactiveStoreTest extends BaseTest {

    @Mock
    private Memory<String, Object> cache;

    private ReactiveStore<String, Object> store;

    @Before
    public void setUp() {
        store = new ReactiveStore<>(cache);
    }

    @Test
    public void store() {
        Object model = new Object();

        store.store(model);

        ArgumentCaptor<Object> modelCaptor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(cache).store(modelCaptor.capture());
        assertThat(modelCaptor.getValue()).isEqualTo(model);
    }

    @Test
    public void storeAll() {
        List<Object> modelList = new ArrayList<Object>(){{
            add(new Object());
            add(new Object());
            add(new Object());
        }};

        store.storeAll(modelList);

        ArgumentCaptor<List<Object>> modelCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(cache).storeAll(modelCaptor.capture());
        assertThat(modelCaptor.getValue()).isEqualTo(modelList);
    }

    @Test
    public void replaceAll() {
        List<Object> modelList = new ArrayList<Object>(){{
            add(new Object());
            add(new Object());
            add(new Object());
        }};

        store.replaceAll(modelList);

        ArgumentCaptor<List<Object>> modelCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(cache).replaceAll(modelCaptor.capture());
        assertThat(modelCaptor.getValue()).isEqualTo(modelList);
    }

    @Test
    public void streamByKeyEmitsCachedValueByKey() {
        Object model = new Object();
        new ArrangeBuilder().enqueueCachedModel(model);

        TestSubscriber<Option<Object>> ts = new TestSubscriber<>();
        store.getBehaviorStream("").subscribe(ts);

        ts.assertValue(ofObj(model));
        ts.assertSubscribed();
    }

    @Test
    public void streamAllEmitsCachedValueAll() {
        List<Object> modelList = new ArrayList<Object>(){{
            add(new Object());
            add(new Object());
            add(new Object());
        }};
        new ArrangeBuilder().enqueueCachedModel(modelList);

        TestSubscriber<Option<List<Object>>> ts = new TestSubscriber<>();
        store.getAllBehaviorStream().subscribe(ts);

        ts.assertValue(ofObj(modelList));
        ts.assertSubscribed();
    }

    @Test
    public void streamByKeyEmitsNoneWhenCacheIsEmpty() {
        new ArrangeBuilder().withEmptyCache();

        TestSubscriber<Option<Object>> ts = new TestSubscriber<>();
        store.getBehaviorStream("").subscribe(ts);

        ts.assertValue(none());
        ts.assertSubscribed();
    }

    @Test
    public void streamAllEmitsNoneWhenCacheIsEmpty() {
        new ArrangeBuilder().withEmptyCache();

        TestSubscriber<Option<List<Object>>> ts = new TestSubscriber<>();
        store.getAllBehaviorStream().subscribe(ts);

        ts.assertValue(none());
        ts.assertSubscribed();
    }

    private class ArrangeBuilder {

        private BehaviorProcessor<Option<Object>> cacheByKeyRelay = BehaviorProcessor.create();
        private BehaviorProcessor<Option<List<Object>>> cacheAllRelay = BehaviorProcessor.create();

        private ArrangeBuilder() {
            Mockito.when(cache.getBehaviorStream("")).thenReturn(cacheByKeyRelay);
            Mockito.when(cache.getAllBehaviorStream()).thenReturn(cacheAllRelay);
        }

        private ArrangeBuilder enqueueCachedModel(Object model) {
            cacheByKeyRelay.onNext(ofObj(model));
            return this;
        }

        private ArrangeBuilder enqueueCachedModel(List<Object> modelList) {
            cacheAllRelay.onNext(ofObj(modelList));
            return this;
        }

        private ArrangeBuilder withEmptyCache() {
            cacheByKeyRelay.onNext(none());
            cacheAllRelay.onNext(none());
            return this;
        }
    }
}