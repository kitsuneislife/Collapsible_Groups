package com.starskyxiii.collapsible_groups.defaults.integration;

import com.starskyxiii.collapsible_groups.platform.Services;
import com.starskyxiii.collapsible_groups.core.Filters;
import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider;
import static com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider.*;

import java.util.List;

public final class AllTheOres implements DefaultGroupProvider {

	@Override
	public int priority() {
		return 300;
	}

	@Override
	public List<GroupDefinition> getGroups() {
		if (!Services.CONFIG.shouldLoadAllTheOres()) return List.of();
		return List.of(
			group("__default_alltheores_dusts", "AllTheOres: Dusts", Filters.all(Filters.itemNamespace("alltheores"), Filters.itemPathEndsWith("_dust"))),
			group("__default_alltheores_gears", "AllTheOres: Gears", Filters.all(Filters.itemNamespace("alltheores"), Filters.itemPathEndsWith("_gear"))),
			group("__default_alltheores_plates", "AllTheOres: Plates", Filters.all(Filters.itemNamespace("alltheores"), Filters.itemPathEndsWith("_plate"))),
			group("__default_alltheores_nuggets", "AllTheOres: Nuggets", Filters.all(Filters.itemNamespace("alltheores"), Filters.itemPathEndsWith("_nugget"))),
			group("__default_alltheores_ingots", "AllTheOres: Ingots", Filters.all(Filters.itemNamespace("alltheores"), Filters.itemPathEndsWith("_ingot"))),
			group("__default_alltheores_rods", "AllTheOres: Rods", Filters.all(Filters.itemNamespace("alltheores"), Filters.itemPathEndsWith("_rod"))),
			group("__default_alltheores_raw_materials", "AllTheOres: Raw Materials", Filters.all(Filters.itemNamespace("alltheores"), Filters.itemPathStartsWith("raw_")))
		);
	}
}
