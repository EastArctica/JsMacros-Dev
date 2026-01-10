package xyz.wagyourtail.jsmacros.client.api.classes.worldscanner.filter.impl;

import xyz.wagyourtail.doclet.DocletCategory;
import xyz.wagyourtail.jsmacros.client.api.classes.worldscanner.filter.ClassWrapperFilter;
import xyz.wagyourtail.jsmacros.client.api.helper.world.BlockStateHelper;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Etheradon
 * @since 1.6.5
 */
@DocletCategory("Filters/Predicates")
public class BlockStateFilter extends ClassWrapperFilter<BlockStateHelper> {

    private static final Map<String, Method> METHOD_LOOKUP = getPublicNoParameterMethods(BlockStateHelper.class);

    public BlockStateFilter(String methodName, Object[] methodArgs, Object[] filterArgs) {
        super(methodName, METHOD_LOOKUP, methodArgs, filterArgs);
    }

}
