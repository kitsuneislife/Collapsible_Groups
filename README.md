# Collapsible Groups (Forge 1.20.1)

Collapsible Groups is a powerful JEI grouping mod that dramatically cleans up your JEI ingredient list. It allows players, modpack developers, and modders to define collapsible groups for items, fluids, and other ingredient types. 

You can define groups via an **in-game editor**, **JSON config files**, **KubeJS scripts**, or by relying on our **built-in providers** for popular mods.

---

## 🚀 Features

- **Declutter JEI:** Hide hundreds of tool parts, colored blocks, and raw ores behind clean, expandable folders in the JEI menu.
- **In-Game Editor:** Create, edit, and delete groups visually directly from inside the game without needing to write code.
- **KubeJS Integration:** Full KubeJS 1.20.1 runtime support to define dynamic groups via `StartupEvents.registry('collapsible_groups:group')`.
- **Advanced Filtering:** Group items by ID, namespace, tags, path prefix/suffix, or specific components.
- **Extensive Mod Support:** Natively supports collapsing items for dozens of popular mods out-of-the-box (toggleable in configs).

## 🔙 The Forge 1.20.1 Backport

This repository is a dedicated backport of the original `1.21.1` multi-loader project, specifically tailored and optimized for **Forge 1.20.1**. 

### What's Different from the Original?
1. **Forge-Only Layout:** Stripped away the complex `fabric/`, `neoforge/`, and `common/` multi-loader architecture to provide a clean, single-project Forge build environment.
2. **Java 17 Downgrade:** Refactored modern Java 21 features (e.g., Pattern Matching for switch, `List.getFirst()`) to be fully compatible with the Java 17 standard required by 1.20.1.
3. **Minecraft 1.20.1 APIs:** Replaced `1.21.1` mechanisms like `ResourceLocation.parse()` and Component-based item comparisons with classic NBT and 1.20.1 mapping standards.
4. **Restored Mod Integrations:** Rescued and ported built-in integration providers that were previously exclusive to NeoForge, including **Tinkers' Construct**, **Create**, **AllTheOres**, **Botania**, **Farmer's Delight**, **AE2**, **Apotheosis**, **Chisel**, **EnderIO**, **Iron's Spellbooks**, and **Traveler's Backpack**.

## ⚙️ Building from Source

To compile the mod yourself, you will need JDK 17 installed.

```bash
git clone https://github.com/YourName/Collapsible_Groups.git
cd Collapsible_Groups
./gradlew build
```
The compiled `.jar` file will be located in `build/libs/`.

## 📦 Releases

This repository includes a **GitHub Actions Workflow** configured for manual dispatch. Modpack authors or server admins can trigger a `workflow_dispatch` run on the Actions tab to automatically compile the project and generate a release `.jar` directly on GitHub.