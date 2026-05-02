package com.starskyxiii.collapsible_groups.core;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public final class ItemStackIngredientView implements IngredientView {
	private static final String STACK_PREFIX = "stack:";

	private final ItemStack stack;
	private final ResourceLocation itemId;

	public ItemStackIngredientView(ItemStack stack) {
		this.stack = stack;
		this.itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
	}

	@Override
	public String ingredientType() {
		return "item";
	}

	@Override
	public ResourceLocation resourceLocation() {
		return itemId;
	}

	@Override
	public boolean hasTag(ResourceLocation tagId) {
		return stack.is(TagKey.create(Registries.ITEM, tagId));
	}

	@Override
	public boolean hasBlockTag(ResourceLocation tagId) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			return blockItem.getBlock().builtInRegistryHolder().is(TagKey.create(Registries.BLOCK, tagId));
		}
		return false;
	}

	@Override
	public boolean matchesExactStack(String encodedStack) {
		return GroupItemSelector.decodeExactSelector(STACK_PREFIX + encodedStack)
			.map(decoded -> ItemStack.isSameItemSameTags(decoded, GroupItemSelector.normalizedCopy(stack)))
			.orElse(false);
	}

	@Override
	public boolean hasComponent(String componentTypeId, String encodedValue) {
		// Data components are not available in 1.20.1; treat component filters as unsupported.
		return false;
	}

	@Override
	public boolean hasComponentPath(String componentTypeId, String path, String expectedValue) {
		// Data components are not available in 1.20.1; treat component filters as unsupported.
		return false;
	}
}
