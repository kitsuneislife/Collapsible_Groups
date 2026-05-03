package com.starskyxiii.collapsible_groups.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public final class KubeJsGroupEvents {
    public static final EventGroup GROUP = EventGroup.of("collapsible_groups");
    public static final EventHandler GROUP_ENTRIES = GROUP.client(
        "groupEntries",
        () -> KubeJsGroupEntriesEvent.class
    );

    private KubeJsGroupEvents() {}
}
