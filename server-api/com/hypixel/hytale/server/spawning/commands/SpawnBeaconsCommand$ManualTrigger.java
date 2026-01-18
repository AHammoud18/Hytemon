/*     */ package com.hypixel.hytale.server.spawning.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.beacons.SpawnBeacon;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*     */ import javax.annotation.Nonnull;
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
/*     */ class ManualTrigger
/*     */   extends AbstractPlayerCommand
/*     */ {
/* 132 */   private static final Message MESSAGE_COMMANDS_SPAWNING_BEACONS_TRIGGER_NOT_BEACON = Message.translation("server.commands.spawning.beacons.trigger.notBeacon");
/* 133 */   private static final Message MESSAGE_COMMANDS_SPAWNING_BEACONS_TRIGGER_NO_SPOTS = Message.translation("server.commands.spawning.beacons.trigger.no_spots");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManualTrigger() {
/* 139 */     super("trigger", "server.commands.spawning.beacons.trigger.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 144 */     FloodFillPositionSelector positionSelectorComponent = (FloodFillPositionSelector)store.getComponent(ref, FloodFillPositionSelector.getComponentType());
/* 145 */     if (positionSelectorComponent == null) {
/* 146 */       throw new GeneralCommandException(MESSAGE_COMMANDS_SPAWNING_BEACONS_TRIGGER_NOT_BEACON);
/*     */     }
/*     */     
/* 149 */     SpawnBeacon spawnBeaconComponent = (SpawnBeacon)store.getComponent(ref, SpawnBeacon.getComponentType());
/* 150 */     if (spawnBeaconComponent == null) {
/* 151 */       throw new GeneralCommandException(MESSAGE_COMMANDS_SPAWNING_BEACONS_TRIGGER_NOT_BEACON);
/*     */     }
/*     */     
/* 154 */     if (!spawnBeaconComponent.manualTrigger(ref, positionSelectorComponent, ref, store)) {
/* 155 */       context.sendMessage(MESSAGE_COMMANDS_SPAWNING_BEACONS_TRIGGER_NO_SPOTS);
/*     */     } else {
/* 157 */       context.sendMessage(Message.translation("server.commands.spawning.beacons.trigger.success"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnBeaconsCommand$ManualTrigger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */