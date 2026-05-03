package com.starskyxiii.collapsible_groups.defaults.integration;

import com.starskyxiii.collapsible_groups.platform.Services;
import com.starskyxiii.collapsible_groups.core.Filters;
import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider;
import static com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider.*;

import java.util.List;

public final class Create implements DefaultGroupProvider {

	@Override
	public int priority() {
		return 300;
	}

	@Override
	public List<GroupDefinition> getGroups() {
		if (!Services.CONFIG.shouldLoadCreate()) return List.of();
		return List.of(
			group("__default_create_crushed_raw_materials", "Crushed Raw Materials", Filters.itemTag("create:crushed_raw_materials")),
			group("__default_create_seats", "Seats", Filters.itemTag("create:seats")),
			group("__default_create_valves", "Valve Handles", Filters.itemTag("create:valve_handles"))
		);
	}
}
