/*     */ package com.hypixel.hytale.server.core;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetModule;
/*     */ import com.hypixel.hytale.server.core.blocktype.BlockTypeModule;
/*     */ import com.hypixel.hytale.server.core.console.ConsoleModule;
/*     */ import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
/*     */ import com.hypixel.hytale.server.core.io.ServerManager;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.AccessControlModule;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.blockhealth.BlockHealthModule;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import com.hypixel.hytale.server.core.modules.camera.FlyCameraModule;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.debug.DebugPlugin;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.stamina.StaminaModule;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*     */ import com.hypixel.hytale.server.core.modules.entityui.EntityUIModule;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*     */ import com.hypixel.hytale.server.core.modules.migrations.MigrationModule;
/*     */ import com.hypixel.hytale.server.core.modules.prefabspawner.PrefabSpawnerModule;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.ProjectileModule;
/*     */ import com.hypixel.hytale.server.core.modules.serverplayerlist.ServerPlayerListModule;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.SplitVelocity;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeModule;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.connectedblocks.ConnectedBlocksModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateModule;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import joptsimple.OptionSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Constants
/*     */ {
/*  47 */   private static final OptionSet OPTION_SET = Options.getOptionSet();
/*     */   
/*     */   public static final boolean DEBUG = true;
/*     */   
/*  51 */   public static final boolean SINGLEPLAYER = OPTION_SET.has(Options.SINGLEPLAYER);
/*  52 */   public static final boolean ALLOWS_SELF_OP_COMMAND = OPTION_SET.has(Options.ALLOW_SELF_OP_COMMAND);
/*     */   
/*  54 */   public static final boolean FRESH_UNIVERSE = checkFreshUniverse();
/*  55 */   public static final boolean FORCE_NETWORK_FLUSH = ((Boolean)OPTION_SET.valueOf(Options.FORCE_NETWORK_FLUSH)).booleanValue();
/*     */   
/*  57 */   public static final Path UNIVERSE_PATH = getUniversePath();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  63 */   public static final PluginManifest[] CORE_PLUGINS = new PluginManifest[] { ConsoleModule.MANIFEST, PermissionsModule.MANIFEST, FlyCameraModule.MANIFEST, AssetModule.MANIFEST, CommonAssetModule.MANIFEST, CosmeticsModule.MANIFEST, ServerManager.MANIFEST, I18nModule.MANIFEST, ItemModule.MANIFEST, BlockTypeModule.MANIFEST, LegacyModule.MANIFEST, BlockModule.MANIFEST, BlockStateModule.MANIFEST, CollisionModule.MANIFEST, BlockSetModule.MANIFEST, MigrationModule.MANIFEST, BlockHealthModule.MANIFEST, PrefabSpawnerModule.MANIFEST, TimeModule.MANIFEST, InteractionModule.MANIFEST, EntityModule.MANIFEST, EntityStatsModule.MANIFEST, EntityUIModule.MANIFEST, DamageModule.MANIFEST, SplitVelocity.MANIFEST, StaminaModule.MANIFEST, DebugPlugin.MANIFEST, ProjectileModule.MANIFEST, ServerPlayerListModule.MANIFEST, AccessControlModule.MANIFEST, SingleplayerModule.MANIFEST, Universe.MANIFEST, ConnectedBlocksModule.MANIFEST };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkFreshUniverse() {
/* 118 */     Path universePath = getUniversePath();
/* 119 */     if (!Files.exists(universePath, new java.nio.file.LinkOption[0])) return true; 
/* 120 */     if (!Files.exists(universePath.resolve("players"), new java.nio.file.LinkOption[0])) return true; 
/* 121 */     Path worlds = universePath.resolve("worlds");
/* 122 */     return !Files.exists(worlds, new java.nio.file.LinkOption[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Path getUniversePath() {
/* 129 */     if (OPTION_SET.has(Options.UNIVERSE)) return (Path)OPTION_SET.valueOf(Options.UNIVERSE); 
/* 130 */     return Path.of("universe", new String[0]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */