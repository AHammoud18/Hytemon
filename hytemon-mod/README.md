# Hytemon Mod (Prototype)

This folder contains a **starter implementation** for a Hytale mod that mirrors Pokemon-style capture and battle flow. It provides:

- **Capture mechanics** scaffolding that can catch existing animals/monsters.
- **Battle encounter** scaffolding to trigger a fight for hostile targets.
- **Trader NPC** spawn command plus a sample shop asset for custom items.

The implementation is intentionally lightweight so you can connect it to Hytale’s interaction, projectile, and NPC role systems once you have concrete asset definitions and gameplay rules.

## What’s Included

### Core plugin
- `HytemonPlugin` registers command helpers, core managers, and the trainer data component.
- `TrainerData` stores captures and the active battle state.

### Capture flow
- `CaptureManager` exposes `attemptCapture(...)` and `throwCaptureItem(...)`.
  - `attemptCapture` supports catching **animals** and **monsters**.
  - Monster targets can trigger battles before capture.

### Battle flow
- `BattleManager` creates/clears `BattleEncounter` data.
  - It is designed to be extended with turn order, moves, effects, and UI.

### Trader NPC
- `TraderNpcSpawner` calls `NPCPlugin.spawnNPC(...)` using the role id `Hytemon:Trader`.
- `assets/Server/Adventure/Shops/HytemonTraderShop.json` defines a simple shop with Pokeballs and Potions.

### Command helpers
- `/hytemon give <item> [quantity]` – queues up a grant of Hytemon items.
- `/hytemon capture <targetId> [displayName] [disposition]` – simulates a capture attempt.
- `/hytemon battle <start|end> [targetId] [displayName]` – simulates a battle start/end.
- `/hytemon spawntrader` – spawns the test trader NPC near the player.

## Wiring the Capture Throw
The `throwCaptureItem(...)` method in `CaptureManager` is a stub. The recommended approach is:

1. Create a **RootInteraction** asset for the Pokeball item that drives a throw animation.
2. Use `InteractionManager.startChain(...)` to spawn a projectile.
3. On projectile hit, resolve the entity and call `attemptCapture(...)`.

This keeps the capture logic server-authoritative while still allowing client-side animation and feedback.

## Wiring the Battle System
`BattleManager` currently stores a `BattleEncounter`. Extend it to:

- Freeze the target AI and player movement.
- Swap the UI to a turn-based battle page.
- Track turn order, moves, status effects, and rewards.

A recommended model is to create an **EncounterState** component for each participant and tick it with a system.

## Trader NPC Setup
The command spawns an NPC role called `Hytemon:Trader`. You must create that NPC role asset (via the NPC Editor or a JSON asset) and wire it to open the shop.

Suggested NPC role configuration steps:

1. Create a role named `Hytemon:Trader`.
2. Add the **OpenShop** action with `ShopId = Hytemon:TraderShop`.
3. Ensure the role uses a valid model and idle behavior.

The provided shop asset lives at:

```
assets/Server/Adventure/Shops/HytemonTraderShop.json
```

You can extend it with additional `ShopElement` entries for potions, better balls, and revives.

## Installing the Mod in Hytale
1. **Package the mod** into a jar containing the `hytemon-mod/src/main/java` classes.
2. Place the jar in your server’s plugins/mods folder.
3. Include `hytemon-mod/manifest.json` (or merge it into your mod pack’s manifest list).
4. Copy the `hytemon-mod/assets` folder into your server asset pack or mod pack assets.
5. Ensure dependencies (`NPC`, `NPCShop`, `Shop`, `ItemModule`, etc.) are enabled.
6. Start the server and run `/hytemon spawntrader` to verify NPC spawning.

## Next Steps
- Add item assets for Pokeballs, Potions, and Revives.
- Bind the capture interaction to projectile collision.
- Extend `TrainerData` with party slots and storage boxes.
- Implement battle UI pages and move definitions.

---

If you want, I can add item assets and NPC role JSON once you confirm the preferred asset formats.
