package com.starskyxiii.collapsible_groups.defaults.integration;

import com.starskyxiii.collapsible_groups.platform.Services;
import com.starskyxiii.collapsible_groups.core.Filters;
import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider;
import static com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider.*;

import java.util.List;

public final class FarmersDelight implements DefaultGroupProvider {

	@Override
	public int priority() {
		return 300;
	}

	@Override
	public List<GroupDefinition> getGroups() {
		if (!Services.CONFIG.shouldLoadFarmersDelight()) return List.of();
		return List.of(
			group("__default_farmersdelight_knives", "Knives", Filters.itemTag("forge:tools/knives")),
			group("__default_farmersdelight_cabinets", "Cabinets", Filters.all(Filters.itemNamespace("farmersdelight"), Filters.itemPathEndsWith("cabinet"))),
			group("__default_farmersdelight_canvas_signs", "Canvas Signs", Filters.all(Filters.itemNamespace("farmersdelight"), Filters.itemPathEndsWith("canvas_sign")))
		);
	}
}
