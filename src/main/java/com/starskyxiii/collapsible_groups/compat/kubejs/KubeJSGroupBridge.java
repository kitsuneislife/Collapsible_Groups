package com.starskyxiii.collapsible_groups.compat.kubejs;

import com.starskyxiii.collapsible_groups.compat.jei.runtime.GroupRegistry;
import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class KubeJSGroupBridge {
    private KubeJSGroupBridge() {}

    public static void applyGroups(List<ItemStack> allItems) {
        if (!KubeJsGroupEvents.GROUP_ENTRIES.hasListeners()) {
            GroupRegistry.setKubeJsGroups(List.of());
            return;
        }
        KubeJsGroupEntriesEvent event = new KubeJsGroupEntriesEvent(allItems);
        KubeJsGroupEvents.GROUP_ENTRIES.post(ScriptType.CLIENT, event);
        List<GroupDefinition> groups = event.getCollected();
        GroupRegistry.setKubeJsGroups(groups);
    }
}
