package com.lucilu.marvelpedia.base.data.common.cache;

import com.lucilu.marvelpedia.base.testcommon.BaseTest;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;
import polanski.option.Option;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

public class ReactiveCacheTest extends BaseTest {

    @Mock
    private Cache<String, TestObject> cache;

    private ReactiveCache<String, TestObject> reactiveCache;

    private TestSubscriber<Option<TestObject>> ts;
    private TestSubscriber<Option<List<TestObject>>> tsList;

    @Override
    public void setUp() {
        reactiveCache = new ReactiveCache<>(TestObject::id, cache);
        ts = new TestSubscriber<>();
        tsList = new TestSubscriber<>();
    }

    @Test
    public void noneIsEmittedWhenCacheIsEmpty() {
        new ArrangeBuilder().withEmptyCache();

        reactiveCache.getBehaviorStream("ID1").subscribe(ts);
        reactiveCache.getAllBehaviorStream().subscribe(tsList);

        ts.assertValue(none());
        tsList.assertValue(none());
    }

    @Test
    public void lastStoredObjectIsEmittedAfterSubscription() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObject(model);

        reactiveCache.store(model);
        reactiveCache.getBehaviorStream("ID1").subscribe(ts);

        ts.assertValue(ofObj(model));
    }

    @Test
    public void listOfObjectsAreEmittedAfterSubscription() {
        List<TestObject> list = createTestObjectList();
        new ArrangeBuilder().withCachedObjectList(list);

        reactiveCache.storeAll(list);
        reactiveCache.getAllBehaviorStream().subscribe(tsList);

        tsList.assertValue(ofObj(list));
    }

    @Test
    public void streamsEmitWhenSingleObjectIsStored() {
        List<TestObject> list = createTestObjectList();
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(list)
                            .withCachedObject(model);

        // Skip 1 since the streams emit once after subscription. This is tested in other test cases
        reactiveCache.getBehaviorStream("ID1").skip(1).subscribe(ts);
        reactiveCache.getAllBehaviorStream().skip(1).subscribe(tsList);
        reactiveCache.store(model);

        ts.assertValue(ofObj(model));
        tsList.assertValue(ofObj(list));
    }

    @Test
    public void streamsEmitWhenObjectListIsStored() {
        List<TestObject> list = createTestObjectList();
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(list)
                            .withCachedObject(model);

        // Skip 1 since the streams emit once after subscription. This is tested in other test cases
        reactiveCache.getBehaviorStream("ID1").skip(1).subscribe(ts);
        reactiveCache.getAllBehaviorStream().skip(1).subscribe(tsList);
        reactiveCache.storeAll(list);

        ts.assertValue(ofObj(model));
        tsList.assertValue(ofObj(list));
    }

    @Test
    public void streamsEmitWhenObjectListIsReplaced() {
        List<TestObject> list = createTestObjectList();
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(list)
                            .withCachedObject(model);

        // Skip 1 since the streams emit once after subscription. This is tested in other test cases
        reactiveCache.getBehaviorStream("ID1").skip(1).subscribe(ts);
        reactiveCache.getAllBehaviorStream().skip(1).subscribe(tsList);
        reactiveCache.replaceAll(list);

        ts.assertValue(ofObj(model));
        tsList.assertValue(ofObj(list));
    }

    @Test
    public void objectIsStoredInCache() {
        TestObject model = new TestObject("ID1");

        reactiveCache.store(model);

        Mockito.verify(cache).store("ID1", model);
    }

    @Test
    public void objectListIsStoredInCache() {
        List<TestObject> list = createTestObjectList();

        reactiveCache.storeAll(list);

        Mockito.verify(cache).store("ID1", list.get(0));
        Mockito.verify(cache).store("ID2", list.get(1));
        Mockito.verify(cache).store("ID3", list.get(2));
    }

    @Test
    public void cacheIsClearedInReplaceAll() {
        List<TestObject> list = createTestObjectList();

        reactiveCache.replaceAll(list);

        Mockito.verify(cache).clear();
    }

    private static List<TestObject> createTestObjectList() {
        return new ArrayList<TestObject>() {{
            add(new TestObject("ID1"));
            add(new TestObject("ID2"));
            add(new TestObject("ID3"));
        }};
    }

    private static class TestObject {

        private final String id;

        private TestObject(final String id) {this.id = id;}

        private String id() {
            return id;
        }
    }

    private class ArrangeBuilder {

        private ArrangeBuilder withCachedObject(TestObject object) {
            Mockito.when(cache.get(object.id())).thenReturn(ofObj(object));
            return this;
        }

        private ArrangeBuilder withCachedObjectList(List<TestObject> objectList) {
            Mockito.when(cache.getAll()).thenReturn(ofObj(objectList));
            return this;
        }

        private ArrangeBuilder withEmptyCache() {
            Mockito.when(cache.get(Mockito.anyString())).thenReturn(none());
            Mockito.when(cache.getAll()).thenReturn(none());
            return this;
        }
    }
}