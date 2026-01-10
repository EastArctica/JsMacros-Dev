package xyz.wagyourtail.jsmacros.client.api.classes.worldscanner.filter.api;

import xyz.wagyourtail.doclet.DocletCategory;

import java.util.function.Function;

/**
 * @author Etheradon
 * @since 1.6.5
 */
@DocletCategory("Filters and Predicates")
public interface IFilter<T> extends Function<T, Boolean> {

    @Override
    Boolean apply(T t);

}
