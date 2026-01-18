/*     */ package com.hypixel.hytale.builtin.buildertools.utils;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.PlaceFluidInteraction;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public final class FluidPatternHelper {
/*     */   public static final class FluidInfo extends Record {
/*     */     private final int fluidId;
/*     */     private final byte fluidLevel;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #31	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #31	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #31	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     public FluidInfo(int fluidId, byte fluidLevel) {
/*  31 */       this.fluidId = fluidId; this.fluidLevel = fluidLevel; } public int fluidId() { return this.fluidId; } public byte fluidLevel() { return this.fluidLevel; }
/*     */   
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
/*     */   @Nullable
/*     */   public static FluidInfo getFluidInfo(@Nonnull String itemKey) {
/*  45 */     Item item = (Item)Item.getAssetMap().getAsset(itemKey);
/*  46 */     if (item == null) {
/*  47 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  51 */     Map<InteractionType, String> interactions = item.getInteractions();
/*  52 */     String secondaryRootId = interactions.get(InteractionType.Secondary);
/*  53 */     if (secondaryRootId == null) {
/*  54 */       return null;
/*     */     }
/*     */     
/*  57 */     RootInteraction rootInteraction = (RootInteraction)RootInteraction.getAssetMap().getAsset(secondaryRootId);
/*  58 */     if (rootInteraction == null) {
/*  59 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  63 */     for (String interactionId : rootInteraction.getInteractionIds()) {
/*  64 */       Interaction interaction = (Interaction)Interaction.getAssetMap().getAsset(interactionId);
/*  65 */       if (interaction instanceof PlaceFluidInteraction) { PlaceFluidInteraction placeFluidInteraction = (PlaceFluidInteraction)interaction;
/*  66 */         String fluidKey = placeFluidInteraction.getFluidKey();
/*  67 */         if (fluidKey != null) {
/*  68 */           int fluidId = Fluid.getAssetMap().getIndex(fluidKey);
/*  69 */           if (fluidId >= 0) {
/*  70 */             Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/*  71 */             byte maxLevel = (byte)((fluid != null) ? fluid.getMaxFluidLevel() : 8);
/*  72 */             return new FluidInfo(fluidId, maxLevel);
/*     */           } 
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFluidItem(@Nonnull String itemKey) {
/*  88 */     return (getFluidInfo(itemKey) != null);
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
/*     */   @Nullable
/*     */   public static FluidInfo getFluidInfoFromBlockType(@Nonnull String blockTypeKey) {
/* 102 */     return getFluidInfo(blockTypeKey);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertool\\utils\FluidPatternHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */