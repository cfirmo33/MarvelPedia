package com.lucilu.marvelpedia.base.data.common.cache;

import com.lucilu.marvelpedia.base.common.providers.TimestampProvider;
import com.lucilu.marvelpedia.base.testcommon.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import polanski.option.Option;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest extends BaseTest {

    private static final long timeoutMs = 1000;

    @Mock
    private TimestampProvider timestampProvider;

    private Cache<String, Object> cache;

    @Before
    public void setUp() {
        cache = new Cache<>(timestampProvider, timeoutMs);
    }

    @Test
    public void returnsNoneWhenEmpty() {
        assertThat(cache.get("KEY")).isEqualTo(Option.none());
        assertThat(cache.getAll()).isEqualTo(Option.none());
    }

    @Test
    public void storedObjectIsReturnedWhenItHasNotExpired() {
        Object object = new Object();

        store("1", object, 1);

        get("1", 2).test().assertValue(object);
    }

    @Test
    public void noneIsReturnedWhenObjectHasExpired() {
        Object object = new Object();

        store("1", object, 1);

        get("1", timeoutMs + 1).test().assertIsNone();
    }

    @Test
    public void storedObjectIsReturnedWhenCacheHasNoTimeout() {
        cache = new Cache<>(timestampProvider);
        Object object = new Object();

        store("1", object, 1);

        get("1", timeoutMs + 1).test().assertValue(object);
    }

    @Test
    public void getAllReturnsNonExpiredObjects() {
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();

        store("1", object1, 1);
        store("2", object2, 2);
        store("3", object3, 3);

        Option<List<Object>> result = getAll(timeoutMs + 1);
        List<Object> expected = new ArrayList<Object>() {{
            add(object2);
            add(object3);
        }};
        result.test().assertValue(expected);
    }

    @Test
    public void getAllReturnsAllObjectsWhenCacheHasNoTimeout() {
        cache = new Cache<>(timestampProvider);
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();

        store("1", object1, 1);
        store("2", object2, 2);
        store("3", object3, 3);

        Option<List<Object>> result = getAll(timeoutMs + 1);
        List<Object> expected = new ArrayList<Object>() {{
            add(object1);
            add(object2);
            add(object3);
        }};
        result.test().assertValue(expected);
    }

    @Test
    public void clearRemovesAllTheItemsFromTheCache() {
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();

        store("1", object1, 1);
        store("2", object2, 2);
        store("3", object3, 3);
        cache.clear();

        cache.getAll().test().assertIsNone();
    }

    private void store(String key, Object value, long creationTimestamp) {
        Mockito.when(timestampProvider.currentTimeMillis()).thenReturn(creationTimestamp);
        cache.store(key, value);
    }

    private Option<Object> get(String key, long currentTimestamp) {
        Mockito.when(timestampProvider.currentTimeMillis()).thenReturn(currentTimestamp);
        return cache.get(key);
    }

    private Option<List<Object>> getAll(long currentTimestamp) {
        Mockito.when(timestampProvider.currentTimeMillis()).thenReturn(currentTimestamp);
        return cache.getAll();
    }
}