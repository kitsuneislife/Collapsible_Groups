package com.starskyxiii.collapsible_groups.defaults.integration;

import com.starskyxiii.collapsible_groups.platform.Services;
import com.starskyxiii.collapsible_groups.core.Filters;
import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider;
import static com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider.*;

import java.util.List;

public final class TravelersBackpack implements DefaultGroupProvider {

	@Override
	public int priority() {
		return 300;
	}

	@Override
	public List<GroupDefinition> getGroups() {
		if (!Services.CONFIG.shouldLoadTravelersBackpack()) return List.of();
		return List.of(
			group("__default_travelers_backpacks", "Traveler's Backpacks", Filters.all(Filters.itemTag("travelersbackpack:backpacks"), Filters.not(Filters.itemId("travelersbackpack:standard"))))
		);
	}
}
