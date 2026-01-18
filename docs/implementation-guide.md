# Hytemon Implementation Guide

This guide describes how to build a Hytale mod that mimics Pokemon-style capture and battles using existing animals and monsters. The examples are written as **engine-agnostic pseudocode** in TypeScript so you can map the logic into the official Hytale modding API when it becomes available.

## Goals

1. **Capture mechanic**: Throw a capture item ("pokeball") at existing entities and store them in a player's party.
2. **Battle flow**: Initiate a battle state when a hostile or aggressive creature wants to fight.
3. **Trader NPC**: Spawn a test merchant that sells capture items, potions, and other consumables.
4. **Integration guidance**: Steps for wiring this into the Hytale mod loader.

## Core Systems Overview

### 1. Capture System

The capture system listens for a thrown capture item colliding with a living entity. It calculates a success rate based on the target's remaining health and any status effects, then stores a reference to the captured entity.

Key concepts:
- **Capture item** (pokeball): throwable item with collision handling.
- **Capture success formula**: uses target health, optional debuffs, and ball modifiers.
- **Party storage**: a data structure saved to player persistent data.

### 2. Battle System

The battle system is a lightweight state machine to pause normal combat and run turn-based logic. The battle starts when:
- Player attacks a creature marked as `battleEligible`, or
- A creature with `aggressive` AI notices the player.

Key concepts:
- **Battle session**: ties together the player and enemy entity.
- **Turn queue**: determines action order (speed stat or fixed order).
- **Move set**: placeholder moves for creatures; integrate later.

### 3. Trader NPC

A test trader NPC sells capture-related items. The trader uses a custom inventory list and a simple dialog to open a shop UI.

Key concepts:
- **Vendor inventory**: list of items and prices.
- **Spawn hook**: spawn a trader near player start or in a town.

## Pseudocode Implementation

See [`src/hytemon-mod.ts`](../src/hytemon-mod.ts) for a structured example of these systems.

## Integration Steps (Hytale Mod)

> These steps are conceptual until the official modding API is finalized.

1. **Create a mod package**
   - Create a new mod folder (e.g., `Hytemon/`).
   - Add `mod.json` with metadata (name, version, author, dependencies).

2. **Register custom items**
   - Register a `capture_ball` item with a throwable component.
   - Register `potion` items that restore health in battle sessions.

3. **Hook entity events**
   - Subscribe to projectile collision events for capture logic.
   - Subscribe to entity aggro or attack events to start a battle session.

4. **Persist player party**
   - Store captured entities in player save data as IDs + stats.
   - On login, rebuild the party list.

5. **Spawn the trader NPC**
   - Add a custom NPC template with shop inventory.
   - Spawn the NPC in a starter village or as a debug command.

6. **UI Layer**
   - Use the Hytale UI system to open a "battle" panel when a battle starts.
   - Use a shop UI for the trader inventory.

7. **Balance and tuning**
   - Adjust capture rates and item costs.
   - Decide which creatures can be captured and which are battle-only.

## Notes for Future Expansion

- Add custom monsters by registering new entity definitions.
- Introduce experience, leveling, evolutions, and status effects.
- Build battle animations using Hytale's animation framework.

