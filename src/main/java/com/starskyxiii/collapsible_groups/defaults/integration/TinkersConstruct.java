package com.starskyxiii.collapsible_groups.defaults.integration;

import com.starskyxiii.collapsible_groups.platform.Services;
import com.starskyxiii.collapsible_groups.core.Filters;
import com.starskyxiii.collapsible_groups.core.GroupDefinition;
import com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider;
import static com.starskyxiii.collapsible_groups.defaults.DefaultGroupProvider.*;

import java.util.List;

public final class TinkersConstruct implements DefaultGroupProvider {

	@Override
	public int priority() {
		return 300;
	}

	@Override
	public List<GroupDefinition> getGroups() {
		if (!Services.CONFIG.shouldLoadTinkersConstruct()) return List.of();
		return List.of(
			group("__default_tconstruct_pickaxe_heads", "Pickaxe Heads", Filters.itemId("tconstruct:pick_head")),
			group("__default_tconstruct_tool_bindings", "Tool Bindings", Filters.itemId("tconstruct:tool_binding")),
			group("__default_tconstruct_tool_handles", "Tool Handles", Filters.itemId("tconstruct:tool_handle")),
			group("__default_tconstruct_sword_blades", "Sword Blades", Filters.itemId("tconstruct:sword_blade")),
			group("__default_tconstruct_tough_handles", "Tough Handles", Filters.itemId("tconstruct:tough_handle")),
			group("__default_tconstruct_hammer_heads", "Hammer Heads", Filters.itemId("tconstruct:hammer_head")),
			group("__default_tconstruct_broad_axe_heads", "Broad Axe Heads", Filters.itemId("tconstruct:broad_axe_head")),
			group("__default_tconstruct_kama_heads", "Kama Heads", Filters.itemId("tconstruct:kama_head")),
			group("__default_tconstruct_round_plates", "Round Plates", Filters.itemId("tconstruct:round_plate")),
			group("__default_tconstruct_small_axe_heads", "Small Axe Heads", Filters.itemId("tconstruct:small_axe_head")),
			group("__default_tconstruct_small_blade", "Small Blades", Filters.itemId("tconstruct:small_blade")),
			group("__default_tconstruct_large_plate", "Large Plates", Filters.itemId("tconstruct:large_plate")),
			group("__default_tconstruct_bow_limbs", "Bow Limbs", Filters.itemId("tconstruct:bow_limb")),
			group("__default_tconstruct_bowstrings", "Bowstrings", Filters.itemId("tconstruct:bowstring")),
			group("__default_tconstruct_repair_kits", "Repair Kits", Filters.itemId("tconstruct:repair_kit")),
			group("__default_tconstruct_slime_saplings", "Slime Saplings", Filters.itemTag("tconstruct:slime_saplings")),
			group("__default_tconstruct_slime_dirt", "Slime Dirt", Filters.itemTag("tconstruct:slime_dirt")),
			group("__default_tconstruct_slime_leaves", "Slime Leaves", Filters.itemTag("tconstruct:slime_leaves"))
		);
	}
}
