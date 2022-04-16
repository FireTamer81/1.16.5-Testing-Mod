package io.github.FireTamer81.GeoPlayerModelTest._library.server.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IValueAccess<T> extends Supplier<T>, Consumer<T> { }
