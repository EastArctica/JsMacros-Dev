package xyz.wagyourtail.jsmacros.client.api.helper;

import com.mojang.brigadier.tree.CommandNode;
import xyz.wagyourtail.doclet.DocletCategory;
import xyz.wagyourtail.jsmacros.core.helpers.BaseHelper;

@DocletCategory("Commands")
public class CommandNodeHelper extends BaseHelper<CommandNode> {
    public final CommandNode<?> fabric;

    public CommandNodeHelper(CommandNode<?> base) {
        super(base);
        fabric = null;
    }

    public CommandNodeHelper(CommandNode<?> base, CommandNode<?> fabric) {
        super(base);
        this.fabric = fabric;
    }

}
