/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.PickupLocation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public final class PlayerSettings
/*    */   extends Record
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private final boolean showEntityMarkers;
/*    */   private final PickupLocation armorItemsPreferredPickupLocation;
/*    */   private final PickupLocation weaponAndToolItemsPreferredPickupLocation;
/*    */   private final PickupLocation usableItemsItemsPreferredPickupLocation;
/*    */   private final PickupLocation solidBlockItemsPreferredPickupLocation;
/*    */   
/*    */   public boolean showEntityMarkers() {
/* 22 */     return this.showEntityMarkers; } private final PickupLocation miscItemsPreferredPickupLocation; private final PlayerCreativeSettings creativeSettings; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerSettings;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerSettings; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerSettings;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerSettings; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerSettings;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerSettings;
/* 22 */     //   0	8	1	o	Ljava/lang/Object; } public PickupLocation armorItemsPreferredPickupLocation() { return this.armorItemsPreferredPickupLocation; } public PickupLocation weaponAndToolItemsPreferredPickupLocation() { return this.weaponAndToolItemsPreferredPickupLocation; } public PickupLocation usableItemsItemsPreferredPickupLocation() { return this.usableItemsItemsPreferredPickupLocation; } public PickupLocation solidBlockItemsPreferredPickupLocation() { return this.solidBlockItemsPreferredPickupLocation; } public PickupLocation miscItemsPreferredPickupLocation() { return this.miscItemsPreferredPickupLocation; } public PlayerCreativeSettings creativeSettings() { return this.creativeSettings; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private static final PlayerSettings INSTANCE = new PlayerSettings(false, PickupLocation.Hotbar, PickupLocation.Hotbar, PickupLocation.Hotbar, PickupLocation.Hotbar, PickupLocation.Hotbar, new PlayerCreativeSettings());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, PlayerSettings> getComponentType() {
/* 41 */     return EntityModule.get().getPlayerSettingsComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerSettings(boolean showEntityMarkers, @Nonnull PickupLocation armorItemsPreferredPickupLocation, @Nonnull PickupLocation weaponAndToolItemsPreferredPickupLocation, @Nonnull PickupLocation usableItemsItemsPreferredPickupLocation, @Nonnull PickupLocation solidBlockItemsPreferredPickupLocation, @Nonnull PickupLocation miscItemsPreferredPickupLocation, PlayerCreativeSettings creativeSettings) {
/* 62 */     this.showEntityMarkers = showEntityMarkers;
/* 63 */     this.armorItemsPreferredPickupLocation = armorItemsPreferredPickupLocation;
/* 64 */     this.weaponAndToolItemsPreferredPickupLocation = weaponAndToolItemsPreferredPickupLocation;
/* 65 */     this.usableItemsItemsPreferredPickupLocation = usableItemsItemsPreferredPickupLocation;
/* 66 */     this.solidBlockItemsPreferredPickupLocation = solidBlockItemsPreferredPickupLocation;
/* 67 */     this.miscItemsPreferredPickupLocation = miscItemsPreferredPickupLocation;
/* 68 */     this.creativeSettings = creativeSettings;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static PlayerSettings defaults() {
/* 76 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 82 */     return new PlayerSettings(this.showEntityMarkers, this.armorItemsPreferredPickupLocation, this.weaponAndToolItemsPreferredPickupLocation, this.usableItemsItemsPreferredPickupLocation, this.solidBlockItemsPreferredPickupLocation, this.miscItemsPreferredPickupLocation, this.creativeSettings.clone());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */