package com.starskyxiii.collapsible_groups.config;

import com.starskyxiii.collapsible_groups.platform.services.IConfigProvider;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Forge config provider that reads and writes
 * {@code config/collapsiblegroups/collapsiblegroups.toml} via {@link ForgeConfigSpec}.
 */
public final class ForgeConfig implements IConfigProvider {

	private static final String[] MACAWS_SERIES_MODS = {
		"mcwwindows",
		"mcwbridges",
		"mcwdoors",
		"mcwfences",
		"mcwfurnitures",
		"mcwlights",
		"mcwpaths",
		"mcwstairs",
		"mcwtrpdoors",
	};

	// IConfigProvider

	@Override public boolean loadDefaultGroups()                   { return LOAD_DEFAULT_GROUPS.get(); }
	@Override public boolean loadGenericGroups()                   { return LOAD_GENERIC_GROUPS.get(); }
	@Override public boolean loadVanillaGroups()                   { return LOAD_VANILLA_GROUPS.get(); }
	@Override public boolean shouldLoadRechiseled() {
		return LOAD_DEFAULT_GROUPS.get()
			&& LOAD_MOD_INTEGRATION_GROUPS.get()
			&& LOAD_RECHISELED.get()
			&& net.minecraftforge.fml.ModList.get().isLoaded("rechiseled");
	}
	@Override public boolean shouldLoadMacawsSeries() {
		return LOAD_DEFAULT_GROUPS.get()
			&& LOAD_MOD_INTEGRATION_GROUPS.get()
			&& LOAD_MACAWS_SERIES.get()
			&& isAnyMacawsSeriesLoaded();
	}
	@Override public boolean shouldLoadAE2() {
		return LOAD_DEFAULT_GROUPS.get() && LOAD_MOD_INTEGRATION_GROUPS.get() && LOAD_AE2.get() && net.minecraftforge.fml.ModList.get().isLoaded("ae2");
	}
	@Override public boolean shouldLoadChisel() {
		return LOAD_DEFAULT_GROUPS.get() && LOAD_MOD_INTEGRATION_GROUPS.get() && LOAD_CHISEL.get() && net.minecraftforge.fml.ModList.get().isLoaded("chisel");
	}
	@Override public boolean shouldLoadApotheosis() {
		return LOAD_DEFAULT_GROUPS.get() && LOAD_MOD_INTEGRATION_GROUPS.get() && LOAD_APOTHEOSIS.get() && net.minecraftforge.fml.ModList.get().isLoaded("apotheosis");
	}
	@Override public boolean shouldLoadEnderIO() {
		return LOAD_DEFAULT_GROUPS.get() && LOAD_MOD_INTEGRATION_GROUPS.get() && LOAD_ENDERIO.get() && net.minecraftforge.fml.ModList.get().isLoaded("enderio");
	}
	@Override public boolean shouldLoadIronsSpellbooks() {
		return LOAD_DEFAULT_GROUPS.get() && LOAD_MOD_INTEGRATION_GROUPS.get() && LOAD_IRONS_SPELLBOOKS.get() && net.minecraftforge.fml.ModList.get().isLoaded("irons_spellbooks");
	}
	@Override public boolean showManagerButton()                   { return SHOW_MANAGER_BUTTON.get(); }
	@Override public boolean debugTimingEnabled()                  { return DEBUG_TIMING_LOGS.get(); }
	@Override public boolean debugStartupIndexVerificationEnabled() { return DEBUG_STARTUP_INDEX_VERIFY.get(); }
	@Override public boolean debugEditorIndexVerificationEnabled() { return DEBUG_EDITOR_INDEX_VERIFY.get(); }

	// defaultGroups

	/** Master switch: set to false for a completely clean slate with no built-in groups. */
	public static final ForgeConfigSpec.BooleanValue LOAD_DEFAULT_GROUPS;

	/** Whether to load built-in generic cross-mod groups (potions, enchanted books, spawn eggs, etc.). */
	public static final ForgeConfigSpec.BooleanValue LOAD_GENERIC_GROUPS;

	/** Whether to load built-in vanilla groupings (wool, concrete, terracotta, etc.). */
	public static final ForgeConfigSpec.BooleanValue LOAD_VANILLA_GROUPS;

	// defaultGroups.ModIntegration

	/** Master switch for all mod-integration groups. */
	public static final ForgeConfigSpec.BooleanValue LOAD_MOD_INTEGRATION_GROUPS;

	/**
	 * Whether to load built-in Rechiseled block-variant groups.
	 * Ignored if Rechiseled is not installed; the setting cannot take effect without the mod.
	 */
	public static final ForgeConfigSpec.BooleanValue LOAD_RECHISELED;

	/**
	 * Whether to load built-in Macaw's series groups.
	 * Ignored if none of the supported Macaw's mods are installed.
	 */
	public static final ForgeConfigSpec.BooleanValue LOAD_MACAWS_SERIES;

	public static final ForgeConfigSpec.BooleanValue LOAD_AE2;
	public static final ForgeConfigSpec.BooleanValue LOAD_CHISEL;
	public static final ForgeConfigSpec.BooleanValue LOAD_APOTHEOSIS;
	public static final ForgeConfigSpec.BooleanValue LOAD_ENDERIO;
	public static final ForgeConfigSpec.BooleanValue LOAD_IRONS_SPELLBOOKS;

	// ui

	/** Whether to show the group manager button in the JEI overlay. */
	public static final ForgeConfigSpec.BooleanValue SHOW_MANAGER_BUTTON;

	// debug

	/** Whether to emit debug timing/performance logs. */
	public static final ForgeConfigSpec.BooleanValue DEBUG_TIMING_LOGS;

	/** Whether to verify the startup index against a reference implementation. */
	public static final ForgeConfigSpec.BooleanValue DEBUG_STARTUP_INDEX_VERIFY;

	/** Whether to enable editor preview index verification mode. */
	public static final ForgeConfigSpec.BooleanValue DEBUG_EDITOR_INDEX_VERIFY;

	public static final ForgeConfigSpec SPEC;

	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		// [defaultGroups]
		builder.push("defaultGroups");
		LOAD_DEFAULT_GROUPS = builder
			.comment(
				"Master switch for all built-in default groups.",
				"Set to false to start with a completely clean slate (no default groups)."
			)
			.define("enabled", true);
		LOAD_GENERIC_GROUPS = builder
			.comment("Whether to load built-in generic cross-mod groups (potions, enchanted books, spawn eggs, etc.)")
			.define("loadGeneric", true);
		LOAD_VANILLA_GROUPS = builder
			.comment("Whether to load built-in vanilla item groupings (wool, concrete, terracotta, etc.)")
			.define("loadVanilla", true);

		// [defaultGroups.ModIntegration]
		builder.push("ModIntegration");
		LOAD_MOD_INTEGRATION_GROUPS = builder
			.comment(
				"Whether to load built-in mod-integration groups.",
				"Groups for mods that are not currently installed are always skipped."
			)
			.define("loadModIntegration", true);
		LOAD_RECHISELED = builder
			.comment(
				"Whether to load built-in Rechiseled block-variant groups (one group per block type).",
				"Has no effect if Rechiseled is not installed."
			)
			.define("loadRechiseled", true);
		LOAD_MACAWS_SERIES = builder
			.comment(
				"Whether to load built-in Macaw's series block-tag groups.",
				"Has no effect if none of the supported Macaw's mods are installed."
			)
			.define("loadMacawsSeries", true);
		LOAD_AE2 = builder.comment("Whether to load built-in AE2 groups.").define("loadAe2", true);
		LOAD_CHISEL = builder.comment("Whether to load built-in Chisel groups.").define("loadChisel", true);
		LOAD_APOTHEOSIS = builder.comment("Whether to load built-in Apotheosis groups.").define("loadApotheosis", true);
		LOAD_ENDERIO = builder.comment("Whether to load built-in EnderIO groups.").define("loadEnderIO", true);
		LOAD_IRONS_SPELLBOOKS = builder.comment("Whether to load built-in Iron's Spellbooks groups.").define("loadIronsSpellbooks", true);
		// Note: Chipped and RS2 integration groups are not yet implemented on Forge.
		// IConfigProvider defaults shouldLoadChipped() and shouldLoadRS2() to false.
		builder.pop(); // ModIntegration
		builder.pop(); // defaultGroups

		// [ui]
		builder.push("ui");
		SHOW_MANAGER_BUTTON = builder
			.comment("Whether to show the group manager button in the JEI ingredient list overlay.")
			.define("showManagerButton", true);
		builder.pop(); // ui

		// [debug]
		builder.push("debug");
		DEBUG_TIMING_LOGS = builder
			.comment(
				"Show Collapsible Groups timing logs in the game log.",
				"Useful when diagnosing slow JEI startup, group rebuilds, or editor/manager refreshes."
			)
			.define("enableTimingLogs", false);
		DEBUG_STARTUP_INDEX_VERIFY = builder
			.comment(
				"Verify the startup item-group index against a reference implementation.",
				"Builds the reference result and compares it with the optimized startup index. This is slower, but useful for correctness testing."
			)
			.define("verifyStartupIndex", false);
		DEBUG_EDITOR_INDEX_VERIFY = builder
			.comment(
				"Verify the editor preview index against a reference implementation.",
				"Useful when testing editor-side preview correctness."
			)
			.define("verifyEditorPreviewIndex", false);
		builder.pop(); // debug

		SPEC = builder.build();
	}

	public ForgeConfig() {}

	private static boolean isAnyMacawsSeriesLoaded() {
		net.minecraftforge.fml.ModList modList = net.minecraftforge.fml.ModList.get();
		for (String modId : MACAWS_SERIES_MODS) {
			if (modList.isLoaded(modId)) {
				return true;
			}
		}
		return false;
	}
}
