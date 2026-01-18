/*     */ package com.hypixel.hytale.server.core.entity.entities.player.movement;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.MovementSettings;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.player.UpdateMovementSettings;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class MovementManager implements Component<EntityStore> {
/*     */   public static ComponentType<EntityStore, MovementManager> getComponentType() {
/*  23 */     return EntityModule.get().getMovementManagerComponentType();
/*     */   }
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
/*     */   public static final BiFunction<PhysicsValues, GameMode, MovementSettings> MASTER_DEFAULT = (physicsValues, gameMode) -> new MovementSettings()
/*     */     {
/*     */     
/*     */     };
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
/*     */   protected MovementSettings defaultSettings;
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
/*     */   protected MovementSettings settings;
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
/*     */   public MovementManager() {}
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
/*     */   public MovementManager(@Nonnull MovementManager other) {
/* 102 */     this();
/* 103 */     this.defaultSettings = new MovementSettings(other.defaultSettings);
/* 104 */     this.settings = new MovementSettings(other.settings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetDefaultsAndUpdate(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 115 */     refreshDefaultSettings(ref, componentAccessor);
/* 116 */     applyDefaultSettings();
/*     */     
/* 118 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 119 */     assert playerRefComponent != null;
/* 120 */     update(playerRefComponent.getPacketHandler());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshDefaultSettings(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 131 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 133 */     int movementConfigIndex = world.getGameplayConfig().getPlayerConfig().getMovementConfigIndex();
/* 134 */     MovementConfig movementConfig = (MovementConfig)((IndexedLookupTableAssetMap)MovementConfig.getAssetStore().getAssetMap()).getAsset(movementConfigIndex);
/*     */     
/* 136 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 137 */     assert playerComponent != null;
/*     */     
/* 139 */     setDefaultSettings(movementConfig.toPacket(), EntityUtils.getPhysicsValues(ref, componentAccessor), playerComponent.getGameMode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyDefaultSettings() {
/* 146 */     this.settings = new MovementSettings(this.defaultSettings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(@Nonnull PacketHandler playerPacketHandler) {
/* 155 */     playerPacketHandler.writeNoCache((Packet)new UpdateMovementSettings(getSettings()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovementSettings getSettings() {
/* 162 */     return this.settings;
/*     */   }
/*     */   
/*     */   public void setDefaultSettings(MovementSettings settings, @Nonnull PhysicsValues physicsValues, GameMode gameMode) {
/* 166 */     this.defaultSettings = settings;
/* 167 */     this.defaultSettings.mass = (float)physicsValues.getMass();
/* 168 */     this.defaultSettings.dragCoefficient = (float)physicsValues.getDragCoefficient();
/* 169 */     this.defaultSettings.invertedGravity = physicsValues.isInvertedGravity();
/* 170 */     this.defaultSettings.canFly = (gameMode == GameMode.Creative);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovementSettings getDefaultSettings() {
/* 177 */     return this.defaultSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 183 */     return "MovementManager{defaultSettings=" + String.valueOf(this.defaultSettings) + ", settings=" + String.valueOf(this.settings) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 192 */     return new MovementManager(this);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\movement\MovementManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */