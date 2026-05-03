package com.starskyxiii.collapsible_groups.compat.kubejs;

import com.starskyxiii.collapsible_groups.core.Filters;
import com.starskyxiii.collapsible_groups.core.GroupFilter;
import com.starskyxiii.collapsible_groups.core.KubeJsItemFilterLowering;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public final class KubeJsFilterCompiler {
    private KubeJsFilterCompiler() {}

    public static GroupFilter compileItemFilter(Object filter) {
        Object unwrapped = unwrap(filter);
        if (unwrapped == null) {
            return null;
        }
        if (unwrapped instanceof GroupFilter groupFilter) {
            return groupFilter;
        }
        if (unwrapped instanceof ItemStack stack) {
            return KubeJsItemFilterLowering.lowerResolvedStack(stack);
        }
        if (unwrapped instanceof ItemLike itemLike) {
            return Filters.itemId(BuiltInRegistries.ITEM.getKey(itemLike.asItem()).toString());
        }
        if (unwrapped instanceof Ingredient ingredient) {
            return compileItemFilter(ingredient);
        }
        if (unwrapped instanceof CharSequence str) {
            return compileItemString(str.toString());
        }
        List<?> list = ListJS.of(unwrapped);
        if (list != null) {
            return compileItemList(list);
        }
        return null;
    }

    public static GroupFilter compileItemFilter(Ingredient ingredient) {
        if (ingredient.isEmpty()) {
            return null;
        }
        ItemStack[] stacks = ingredient.getItems();
        if (stacks.length == 0) {
            return null;
        }
        List<GroupFilter> nodes = new ArrayList<>(stacks.length);
        for (ItemStack stack : stacks) {
            nodes.add(KubeJsItemFilterLowering.lowerResolvedStack(stack));
        }
        return composeFallback(nodes);
    }

    private static GroupFilter compileItemString(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        if (trimmed.startsWith("#")) {
            String tag = trimmed.substring(1);
            if (ResourceLocation.tryParse(tag) == null) {
                return null;
            }
            return Filters.itemTag(tag);
        }
        if (trimmed.startsWith("@")) {
            String namespace = trimmed.substring(1);
            if (ResourceLocation.isValidNamespace(namespace)) {
                return Filters.itemNamespace(namespace);
            }
            return null;
        }
        if (ResourceLocation.tryParse(trimmed) != null) {
            return Filters.itemId(trimmed);
        }
        return null;
    }

    private static GroupFilter compileItemList(List<?> list) {
        List<GroupFilter> children = new ArrayList<>();
        for (Object element : list) {
            GroupFilter child = compileItemFilter(element);
            if (child == null) {
                return null;
            }
            children.add(child);
        }
        return composeFallback(children);
    }

    private static GroupFilter composeFallback(List<GroupFilter> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        if (nodes.size() == 1) {
            return nodes.get(0);
        }
        return Filters.any(nodes.toArray(GroupFilter[]::new));
    }

    private static Object unwrap(Object filter) {
        while (filter instanceof Wrapper wrapper) {
            filter = wrapper.unwrap();
        }
        return filter;
    }
}
