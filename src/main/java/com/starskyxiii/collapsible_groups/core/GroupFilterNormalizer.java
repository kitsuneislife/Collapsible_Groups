package com.starskyxiii.collapsible_groups.core;

import java.util.ArrayList;
import java.util.List;

public final class GroupFilterNormalizer {
	private GroupFilterNormalizer() {}

	public static GroupFilter normalize(GroupFilter filter) {
		if (filter instanceof GroupFilter.Any any) {
			return normalizeAny(any.children());
		}
		if (filter instanceof GroupFilter.All all) {
			return normalizeAll(all.children());
		}
		if (filter instanceof GroupFilter.Not not) {
			return normalizeNot(not.child());
		}
		return filter;
	}

	private static GroupFilter normalizeAny(List<GroupFilter> children) {
		List<GroupFilter> normalized = new ArrayList<>();
		for (GroupFilter child : children) {
			GroupFilter node = normalize(child);
			if (node instanceof GroupFilter.Any nested) {
				normalized.addAll(nested.children());
			} else {
				normalized.add(node);
			}
		}
		if (normalized.size() == 1) {
			return normalized.get(0);
		}
		return new GroupFilter.Any(normalized);
	}

	private static GroupFilter normalizeAll(List<GroupFilter> children) {
		List<GroupFilter> normalized = new ArrayList<>();
		for (GroupFilter child : children) {
			GroupFilter node = normalize(child);
			if (node instanceof GroupFilter.All nested) {
				normalized.addAll(nested.children());
			} else {
				normalized.add(node);
			}
		}
		if (normalized.size() == 1) {
			return normalized.get(0);
		}
		return new GroupFilter.All(normalized);
	}

	private static GroupFilter normalizeNot(GroupFilter child) {
		GroupFilter normalizedChild = normalize(child);
		if (normalizedChild instanceof GroupFilter.Not nested) {
			return normalize(nested.child());
		}
		return new GroupFilter.Not(normalizedChild);
	}
}
