package xyz.wagyourtail.jsmacros.client.api.classes.worldscanner.filter.api;

import xyz.wagyourtail.doclet.DocletCategory;

@FunctionalInterface
@DocletCategory("Filters/Predicates")
public interface ICompare<T> {

    boolean compare(T obj1, T obj2);

}
