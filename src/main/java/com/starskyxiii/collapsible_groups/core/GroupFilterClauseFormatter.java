package com.starskyxiii.collapsible_groups.core;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Read-only formatter that traverses a {@link GroupFilter} tree and produces a flat list of
 * depth-annotated {@link Clause} entries for indented display in the editor.
 */
public final class GroupFilterClauseFormatter {
	private static final String ITEM_TYPE = "item";
	private static final String FLUID_TYPE = "fluid";

	private GroupFilterClauseFormatter() {}

	public record Clause(int depth, String label, @Nullable String value) {}

	public static List<Clause> format(@Nullable GroupFilter filter) {
		if (filter == null) {
			return List.of();
		}

		List<Clause> clauses = new ArrayList<>();
		appendClauses(GroupFilterNormalizer.normalize(filter), 0, clauses);
		return List.copyOf(clauses);
	}

	public static boolean shouldDisplay(@Nullable GroupFilter filter) {
		if (filter == null) {
			return false;
		}

		return hasSpecialClause(GroupFilterNormalizer.normalize(filter));
	}

	private static void appendClauses(GroupFilter filter, int depth, List<Clause> clauses) {
		if (filter instanceof GroupFilter.Any any) {
			clauses.add(new Clause(depth, "ANY", null));
			for (GroupFilter child : any.children()) {
				appendClauses(child, depth + 1, clauses);
			}
			return;
		}
		if (filter instanceof GroupFilter.All all) {
			clauses.add(new Clause(depth, "ALL", null));
			for (GroupFilter child : all.children()) {
				appendClauses(child, depth + 1, clauses);
			}
			return;
		}
		if (filter instanceof GroupFilter.Not not) {
			clauses.add(new Clause(depth, "NOT", null));
			appendClauses(not.child(), depth + 1, clauses);
			return;
		}
		if (filter instanceof GroupFilter.Id id) {
			clauses.add(new Clause(depth, typedLabel(id.ingredientType(), "Id"), id.id()));
			return;
		}
		if (filter instanceof GroupFilter.Tag tag) {
			clauses.add(new Clause(depth, typedLabel(tag.ingredientType(), "Tag"), tag.tag()));
			return;
		}
		if (filter instanceof GroupFilter.BlockTag blockTag) {
			clauses.add(new Clause(depth, "Block Tag", blockTag.tag()));
			return;
		}
		if (filter instanceof GroupFilter.ItemPathStartsWith startsWith) {
			clauses.add(new Clause(depth, "Item Path Starts With", startsWith.prefix()));
			return;
		}
		if (filter instanceof GroupFilter.ItemPathEndsWith endsWith) {
			clauses.add(new Clause(depth, "Item Path Ends With", endsWith.suffix()));
			return;
		}
		if (filter instanceof GroupFilter.Namespace namespace) {
			clauses.add(new Clause(depth, typedLabel(namespace.ingredientType(), "Namespace"), namespace.namespace()));
			return;
		}
		if (filter instanceof GroupFilter.ExactStack stack) {
			clauses.add(new Clause(depth, "Exact Stack", stack.encodedStack()));
			return;
		}
		if (filter instanceof GroupFilter.HasComponent hc) {
			clauses.add(new Clause(depth, "Has Component", hc.componentTypeId() + " = " + hc.encodedValue()));
			return;
		}
		if (filter instanceof GroupFilter.ComponentPath cp) {
			clauses.add(new Clause(depth, "Component Path", cp.componentTypeId() + " / " + cp.path() + " = " + cp.expectedValue()));
		}
	}

	private static boolean hasSpecialClause(GroupFilter filter) {
		if (filter instanceof GroupFilter.Id) {
			return false;
		}
		if (filter instanceof GroupFilter.ExactStack) {
			return false;
		}
		if (filter instanceof GroupFilter.HasComponent || filter instanceof GroupFilter.ComponentPath) {
			return true;
		}
		if (filter instanceof GroupFilter.Any any) {
			return any.children().stream().anyMatch(GroupFilterClauseFormatter::hasSpecialClause);
		}
		if (filter instanceof GroupFilter.Tag
			|| filter instanceof GroupFilter.BlockTag
			|| filter instanceof GroupFilter.ItemPathStartsWith
			|| filter instanceof GroupFilter.ItemPathEndsWith
			|| filter instanceof GroupFilter.Namespace
			|| filter instanceof GroupFilter.All
			|| filter instanceof GroupFilter.Not) {
			return true;
		}
		return false;
	}

	private static String typedLabel(String ingredientType, String baseLabel) {
		return switch (ingredientType) {
			case ITEM_TYPE -> "Item " + baseLabel;
			case FLUID_TYPE -> "Fluid " + baseLabel;
			default -> ingredientType + " " + baseLabel;
		};
	}
}
