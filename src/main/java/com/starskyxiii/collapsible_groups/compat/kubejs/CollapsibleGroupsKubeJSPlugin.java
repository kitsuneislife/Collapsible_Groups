package com.starskyxiii.collapsible_groups.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;

public class CollapsibleGroupsKubeJSPlugin extends KubeJSPlugin {

	@Override
	public void registerEvents() {
		KubeJsGroupEvents.GROUP.register();
	}
}
