/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.blocktrack.BlockCounter;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ public class PlacementCountConditionInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   public static final BuilderCodec<PlacementCountConditionInteraction> CODEC;
/*    */   private String blockType;
/*    */   
/*    */   static {
/* 43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlacementCountConditionInteraction.class, PlacementCountConditionInteraction::new, SimpleInstantInteraction.CODEC).appendInherited(new KeyedCodec("Block", (Codec)Codec.STRING), (o, v) -> o.blockType = v, o -> o.blockType, (o, p) -> o.blockType = p.blockType).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Value", (Codec)Codec.INTEGER), (o, v) -> o.value = v.intValue(), o -> Integer.valueOf(o.value), (o, p) -> o.value = p.value).add()).appendInherited(new KeyedCodec("LessThan", (Codec)Codec.BOOLEAN), (o, v) -> o.lessThan = v.booleanValue(), o -> Boolean.valueOf(o.lessThan), (o, p) -> o.lessThan = p.lessThan).add()).build();
/*    */   }
/*    */   
/* 46 */   private int value = 0;
/*    */   
/*    */   private boolean lessThan = true;
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 51 */     BlockCounter counter = (BlockCounter)((EntityStore)context.getCommandBuffer().getExternalData()).getWorld().getChunkStore().getStore().getResource(BlockCounter.getResourceType());
/* 52 */     int blockCount = counter.getBlockPlacementCount(this.blockType);
/*    */     
/* 54 */     if (this.lessThan) {
/* 55 */       if (blockCount < this.value) {
/* 56 */         (context.getState()).state = InteractionState.Finished;
/*    */       } else {
/* 58 */         (context.getState()).state = InteractionState.Failed;
/*    */       }
/*    */     
/* 61 */     } else if (blockCount > this.value) {
/* 62 */       (context.getState()).state = InteractionState.Finished;
/*    */     } else {
/* 64 */       (context.getState()).state = InteractionState.Failed;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 72 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\PlacementCountConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */