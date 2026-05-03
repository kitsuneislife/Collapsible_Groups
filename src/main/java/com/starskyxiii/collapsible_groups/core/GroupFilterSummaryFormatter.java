package com.starskyxiii.collapsible_groups.core;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Read-only formatter for compact structural filter summaries used by the editor UI.
 */
public final class GroupFilterSummaryFormatter {
	private static final String ITEM_TYPE = "item";
	private static final String FLUID_TYPE = "fluid";

	private GroupFilterSummaryFormatter() {}

	public static String format(@Nullable GroupFilter filter) {
		if (filter == null) {
			return "";
		}
		return formatNode(GroupFilterNormalizer.normalize(filter), true);
	}

	private static String formatNode(GroupFilter filter, boolean expandAtomicChildren) {
		if (filter instanceof GroupFilter.Any any) {
			return formatComposite("ANY", any.children(), expandAtomicChildren);
		}
		if (filter instanceof GroupFilter.All all) {
			return formatComposite("ALL", all.children(), expandAtomicChildren);
		}
		if (filter instanceof GroupFilter.Not not) {
			return "NOT(" + formatNestedChild(not.child()) + ")";
		}
		if (filter instanceof GroupFilter.Id id) {
			return formatId(id.ingredientType(), id.id());
		}
		if (filter instanceof GroupFilter.Tag tag) {
			return formatTag(tag.ingredientType(), tag.tag());
		}
		if (filter instanceof GroupFilter.BlockTag blockTag) {
			return "block tag " + blockTag.tag();
		}
		if (filter instanceof GroupFilter.ItemPathStartsWith startsWith) {
			return "item path starts with " + startsWith.prefix();
		}
		if (filter instanceof GroupFilter.ItemPathEndsWith endsWith) {
			return "item path ends with " + endsWith.suffix();
		}
		if (filter instanceof GroupFilter.Namespace namespace) {
			return formatNamespace(namespace.ingredientType(), namespace.namespace());
		}
		if (filter instanceof GroupFilter.ExactStack) {
			return "exact stack";
		}
		if (filter instanceof GroupFilter.HasComponent hc) {
			return "has component " + hc.componentTypeId() + "=" + hc.encodedValue();
		}
		if (filter instanceof GroupFilter.ComponentPath cp) {
			return "component path " + cp.componentTypeId() + "/" + cp.path() + "=" + cp.expectedValue();
		}
		return "";
	}

	private static String formatComposite(String operator, List<GroupFilter> children, boolean expandAtomicChildren) {
		if (children.isEmpty()) {
			return operator + "()";
		}
		if (expandAtomicChildren && children.stream().allMatch(GroupFilterSummaryFormatter::isAtomic)) {
			return operator + "(" + formatAtomicChildren(children) + ")";
		}

		List<String> parts = new ArrayList<>(children.size());
		for (GroupFilter child : children) {
			parts.add(formatNestedChild(child));
		}
		return operator + "(" + String.join(", ", parts) + ")";
	}

	private static String formatAtomicChildren(List<GroupFilter> children) {
		if (children.size() <= 2) {
			return children.stream()
				.map(child -> formatNode(child, true))
				.reduce((left, right) -> left + ", " + right)
				.orElse("");
		}

		Map<String, Integer> counts = new LinkedHashMap<>();
		for (GroupFilter child : children) {
			counts.merge(categoryLabel(child), 1, Integer::sum);
		}

		List<String> parts = new ArrayList<>(counts.size());
		for (Map.Entry<String, Integer> entry : counts.entrySet()) {
			parts.add(entry.getKey() + " x" + entry.getValue());
		}
		return String.join(", ", parts);
	}

	private static String formatNestedChild(GroupFilter child) {
		if (isAtomic(child)) {
			return formatNode(child, true);
		}
		if (child instanceof GroupFilter.Any) {
			return "ANY(...)";
		}
		if (child instanceof GroupFilter.All) {
			return "ALL(...)";
		}
		if (child instanceof GroupFilter.Not) {
			return "NOT(...)";
		}
		return formatNode(child, false);
	}

	private static boolean isAtomic(GroupFilter filter) {
		return filter instanceof GroupFilter.Id
			|| filter instanceof GroupFilter.Tag
			|| filter instanceof GroupFilter.BlockTag
			|| filter instanceof GroupFilter.ItemPathStartsWith
			|| filter instanceof GroupFilter.ItemPathEndsWith
			|| filter instanceof GroupFilter.Namespace
			|| filter instanceof GroupFilter.ExactStack
			|| filter instanceof GroupFilter.HasComponent
			|| filter instanceof GroupFilter.ComponentPath;
	}

	private static String categoryLabel(GroupFilter filter) {
		if (filter instanceof GroupFilter.Id id) {
			return categoryPrefix(id.ingredientType()) + "id";
		}
		if (filter instanceof GroupFilter.Tag tag) {
			return categoryPrefix(tag.ingredientType()) + "tag";
		}
		if (filter instanceof GroupFilter.BlockTag) {
			return "block tag";
		}
		if (filter instanceof GroupFilter.ItemPathStartsWith) {
			return "item path starts with";
		}
		if (filter instanceof GroupFilter.ItemPathEndsWith) {
			return "item path ends with";
		}
		if (filter instanceof GroupFilter.Namespace namespace) {
			return categoryPrefix(namespace.ingredientType()) + "namespace";
		}
		if (filter instanceof GroupFilter.ExactStack) {
			return "exact stack";
		}
		if (filter instanceof GroupFilter.HasComponent) {
			return "has component";
		}
		if (filter instanceof GroupFilter.ComponentPath) {
			return "component path";
		}
		return formatNode(filter, false);
	}

	private static String formatId(String ingredientType, String id) {
		return categoryPrefix(ingredientType) + "id " + id;
	}

	private static String formatTag(String ingredientType, String tag) {
		return valuePrefix(ingredientType) + "tag " + tag;
	}

	private static String formatNamespace(String ingredientType, String namespace) {
		return valuePrefix(ingredientType) + "namespace " + namespace;
	}

	private static String categoryPrefix(String ingredientType) {
		if (ITEM_TYPE.equals(ingredientType)) {
			return "item ";
		}
		if (FLUID_TYPE.equals(ingredientType)) {
			return "fluid ";
		}
		return ingredientType + " ";
	}

	private static String valuePrefix(String ingredientType) {
		if (ITEM_TYPE.equals(ingredientType)) {
			return "";
		}
		if (FLUID_TYPE.equals(ingredientType)) {
			return "fluid ";
		}
		return ingredientType + " ";
	}
}
