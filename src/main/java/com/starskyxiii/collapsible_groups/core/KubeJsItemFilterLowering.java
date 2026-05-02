package com.starskyxiii.collapsible_groups.core;

import net.minecraft.world.item.ItemStack;

/**
 * Converts a resolved KubeJS {@link ItemStack} into the appropriate {@link GroupFilter} node.
 *
 * <p>Produces {@link GroupFilter.Id} when the stack has no custom tag data,
 * or {@link GroupFilter.ExactStack} to preserve NBT.
 */
public final class KubeJsItemFilterLowering {
	private KubeJsItemFilterLowering() {}

	public static GroupFilter lowerResolvedStack(ItemStack stack) {
		ItemStack normalized = GroupItemSelector.normalizedCopy(stack);
		return normalized.hasTag()
			? Filters.exactStack(normalized)
			: Filters.itemId(GroupItemSelector.wholeItemSelector(normalized));
	}
}
