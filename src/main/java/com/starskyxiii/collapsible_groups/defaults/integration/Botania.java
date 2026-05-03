package com.starskyxiii.collapsible_groups.defaults.integration;

import com.starskyxiii.collapsible_groups.platform.Services;
import com.starskyxiii.collapsible_groups.core.Filters;
import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider;
import static com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider.*;

import java.util.List;

public final class Botania implements DefaultGroupProvider {

	@Override
	public int priority() {
		return 300;
	}

	@Override
	public List<GroupDefinition> getGroups() {
		if (!Services.CONFIG.shouldLoadBotania()) return List.of();
		return List.of(
			group("__default_botania_mystical_flowers", "Mystical Flowers", Filters.itemTag("botania:mystical_flowers")),
			group("__default_botania_tall_mystical_flowers", "Tall Mystical Flowers", Filters.itemTag("botania:double_mystical_flowers")),
			group("__default_botania_petals", "Floral Petals", Filters.itemTag("botania:petals")),
			group("__default_botania_mushrooms", "Mystical Mushrooms", Filters.itemTag("botania:mystical_mushrooms")),
			group("__default_botania_runes", "Runes", Filters.itemTag("botania:runes")),
			group("__default_botania_mana_lenses", "Mana Lenses", Filters.itemId("botania:lens"))
		);
	}
}
