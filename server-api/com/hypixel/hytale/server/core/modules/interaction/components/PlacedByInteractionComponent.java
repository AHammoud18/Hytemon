/*    */ package com.hypixel.hytale.server.core.modules.interaction.components;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PlacedByInteractionComponent implements Component<ChunkStore> {
/*    */   public static ComponentType<ChunkStore, PlacedByInteractionComponent> getComponentType() {
/* 16 */     return InteractionModule.get().getPlacedByComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<PlacedByInteractionComponent> CODEC;
/*    */   
/*    */   private UUID whoPlacedUuid;
/*    */ 
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PlacedByInteractionComponent.class, PlacedByInteractionComponent::new).appendInherited(new KeyedCodec("WhoPlacedUuid", (Codec)Codec.UUID_BINARY), (o, i) -> o.whoPlacedUuid = i, o -> o.whoPlacedUuid, (o, parent) -> o.whoPlacedUuid = parent.whoPlacedUuid).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacedByInteractionComponent() {}
/*    */ 
/*    */   
/*    */   public PlacedByInteractionComponent(UUID whoPlacedUuid) {
/* 35 */     this.whoPlacedUuid = whoPlacedUuid;
/*    */   }
/*    */   
/*    */   public UUID getWhoPlacedUuid() {
/* 39 */     return this.whoPlacedUuid;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Component<ChunkStore> clone() {
/* 45 */     PlacedByInteractionComponent clone = new PlacedByInteractionComponent();
/* 46 */     clone.whoPlacedUuid = this.whoPlacedUuid;
/* 47 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\components\PlacedByInteractionComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */