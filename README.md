# Collapsible Groups

Collapsible Groups is a JEI grouping mod for Minecraft 1.21.1. It lets players, modpack developers, and modders define collapsible item, fluid, and other ingredient type groups through an in-game editor, JSON config files, KubeJS, or built-in providers.

## Overview

This mod provides JEI ingredient grouping for Minecraft 1.21.1, with the richest feature set on NeoForge and lighter-weight builds for Forge and Fabric.

## Forge 1.20.1 Backport Progress

Progress (0% -> 100%): [######----] 58%

Backport Changelog (minimal):
- Iter 01 (5%): Repo trimmed to Forge-only layout; sources/resources merged into root module.
- Iter 02 (12%): Build retargeted to Forge 1.20.1 baseline (Java 17, deps placeholders).
- Iter 03 (25%): KubeJS + soft-dep hooks restored; 1.20.1 tag-based stack handling.
- Iter 04 (32%): Replaced 1.21.1-only APIs (ResourceLocation, ItemStack compare) for 1.20.1.
- Iter 05 (35%): Build script guard for missing sourcesJar; compile requires Java 17 toolchain.
- Iter 06 (40%): Targeted JEI/KubeJS versions for Forge 1.20.1.
- Iter 07 (43%): Added Architectury maven repo for KubeJS deps.
- Iter 08 (50%): Rewrote preview switch patterns for Java 17.
- Iter 09 (58%): JEI 1.20.1 button API update; KubeJS custom event bridge.