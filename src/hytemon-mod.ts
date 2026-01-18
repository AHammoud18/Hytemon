/*
  Hytemon: engine-agnostic pseudocode scaffold for a Hytale mod.
  Replace the placeholder interfaces with official Hytale API types once available.
*/

type EntityId = string;

type Vector3 = {
  x: number;
  y: number;
  z: number;
};

type CaptureResult = "success" | "failed" | "immune";

type StatusEffect = "asleep" | "stunned" | "none";

type CreatureData = {
  id: EntityId;
  name: string;
  maxHealth: number;
  currentHealth: number;
  status: StatusEffect;
  speed: number;
  captureEligible: boolean;
};

type PlayerData = {
  id: string;
  party: CreatureData[];
  inventory: Record<string, number>;
};

type BattleMove = {
  id: string;
  name: string;
  power: number;
  accuracy: number;
};

type BattleState = {
  player: PlayerData;
  opponent: CreatureData;
  playerActive?: CreatureData;
  opponentActive: CreatureData;
  phase: "start" | "playerTurn" | "opponentTurn" | "end";
};

type ShopItem = {
  itemId: string;
  price: number;
};

type TraderConfig = {
  name: string;
  inventory: ShopItem[];
  spawnPosition: Vector3;
};

const DEFAULT_BATTLE_MOVES: BattleMove[] = [
  { id: "tackle", name: "Tackle", power: 40, accuracy: 100 },
  { id: "growl", name: "Growl", power: 0, accuracy: 100 },
];

class CaptureSystem {
  constructor(private readonly captureItemId: string) {}

  attemptCapture(player: PlayerData, creature: CreatureData): CaptureResult {
    if (!creature.captureEligible) {
      return "immune";
    }

    const captureChance = this.calculateCaptureChance(creature);
    const roll = Math.random();

    if (roll <= captureChance) {
      player.party.push({ ...creature });
      return "success";
    }

    return "failed";
  }

  private calculateCaptureChance(creature: CreatureData): number {
    const healthRatio = creature.currentHealth / creature.maxHealth;
    const baseChance = 0.2;
    const statusBonus = creature.status === "asleep" ? 0.25 : creature.status === "stunned" ? 0.15 : 0;
    const healthBonus = Math.max(0, 0.6 - healthRatio);

    return Math.min(0.95, baseChance + statusBonus + healthBonus);
  }
}

class BattleSystem {
  startBattle(player: PlayerData, opponent: CreatureData): BattleState {
    const playerActive = player.party[0];

    return {
      player,
      opponent,
      playerActive,
      opponentActive: opponent,
      phase: "start",
    };
  }

  nextTurn(state: BattleState): BattleState {
    if (state.phase === "start") {
      return { ...state, phase: "playerTurn" };
    }

    if (state.phase === "playerTurn") {
      return { ...state, phase: "opponentTurn" };
    }

    if (state.phase === "opponentTurn") {
      return { ...state, phase: "playerTurn" };
    }

    return state;
  }

  chooseMove(_state: BattleState, _moveId: string): BattleMove {
    return DEFAULT_BATTLE_MOVES[0];
  }
}

class TraderNPC {
  constructor(private readonly config: TraderConfig) {}

  openShop(player: PlayerData): ShopItem[] {
    return this.config.inventory;
  }

  getSpawnPosition(): Vector3 {
    return this.config.spawnPosition;
  }
}

class HytemonMod {
  private captureSystem = new CaptureSystem("capture_ball");
  private battleSystem = new BattleSystem();
  private traderNPC = new TraderNPC({
    name: "Hytemon Trader",
    inventory: [
      { itemId: "capture_ball", price: 50 },
      { itemId: "potion", price: 75 },
      { itemId: "super_potion", price: 150 },
    ],
    spawnPosition: { x: 120, y: 64, z: -32 },
  });

  onProjectileHit(player: PlayerData, creature: CreatureData, itemId: string): CaptureResult {
    if (itemId !== "capture_ball") {
      return "failed";
    }

    return this.captureSystem.attemptCapture(player, creature);
  }

  onCreatureAggro(player: PlayerData, creature: CreatureData): BattleState {
    return this.battleSystem.startBattle(player, creature);
  }

  spawnTrader(): TraderNPC {
    return this.traderNPC;
  }
}

export { HytemonMod, CaptureSystem, BattleSystem, TraderNPC };
