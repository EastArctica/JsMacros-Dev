package xyz.wagyourtail.jsmacros.client.api.classes.worldscanner.filter.compare;

import xyz.wagyourtail.doclet.DocletCategory;
import xyz.wagyourtail.jsmacros.client.api.classes.worldscanner.filter.api.IFilter;

/**
 * @author Etheradon
 * @since 1.6.5
 */
@DocletCategory("Filters/Predicates")
public class BooleanCompareFilter implements IFilter<Boolean> {

    private final boolean compareTo;

    public BooleanCompareFilter(boolean compareTo) {
        this.compareTo = compareTo;
    }

    @Override
    public Boolean apply(Boolean bool) {
        return bool.equals(compareTo);
    }

}
