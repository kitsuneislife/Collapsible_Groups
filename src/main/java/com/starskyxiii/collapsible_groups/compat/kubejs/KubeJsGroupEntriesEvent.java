package com.starskyxiii.collapsible_groups.compat.kubejs;

import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import com.starskyxiii.collapsible_groups.core.GroupFilter;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KubeJsGroupEntriesEvent extends EventJS {
    private final List<ItemStack> allItems;
    private final List<GroupDefinition> collected = new ArrayList<>();

    public KubeJsGroupEntriesEvent(List<ItemStack> allItems) {
        this.allItems = allItems;
    }

    public void group(String groupId, String displayName, Object filter) {
        String id = "__kjs_" + sanitizeId(groupId);
        String name = displayName == null ? groupId : displayName;
        GroupFilter compiled = KubeJsFilterCompiler.compileItemFilter(filter);
        if (compiled != null) {
            collected.add(new GroupDefinition(id, name, true, compiled));
            return;
        }

        // Fallback: if filter is null or unsupported, skip without crashing.
    }

    public List<GroupDefinition> getCollected() {
        return List.copyOf(collected);
    }

    private static String sanitizeId(String raw) {
        if (raw == null || raw.isBlank()) {
            return "group";
        }
        ResourceLocation parsed = ResourceLocation.tryParse(raw);
        String value = parsed == null ? raw : parsed.toString();
        return value.replace(':', '_').replace('/', '_');
    }
}
