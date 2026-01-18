/*    */ package com.hypixel.hytale.server.core.command.commands.utility.lighting;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.lighting.ChunkLightingManager;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class LightingInfoCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 26 */   private final FlagArg detailFlag = withFlagArg("detail", "server.commands.lighting.info.detail.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LightingInfoCommand() {
/* 32 */     super("info", "server.commands.lighting.info.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 37 */     Store<ChunkStore> chunkStoreStore = world.getChunkStore().getStore();
/* 38 */     ChunkLightingManager chunkLighting = world.getChunkLighting();
/*    */     
/* 40 */     context.sendMessage(Message.translation("server.commands.lighting.info.summary")
/* 41 */         .param("queueSize", chunkLighting.getQueueSize())
/* 42 */         .param("lightCalculation", chunkLighting.getLightCalculation().getClass().getSimpleName()));
/*    */     
/* 44 */     if (((Boolean)this.detailFlag.get(context)).booleanValue()) {
/* 45 */       AtomicInteger total = new AtomicInteger();
/* 46 */       AtomicInteger localLightCount = new AtomicInteger();
/* 47 */       AtomicInteger globalLightCount = new AtomicInteger();
/*    */       
/* 49 */       chunkStoreStore.forEachEntityParallel((Query)WorldChunk.getComponentType(), (index, archetypeChunk, commandBuffer) -> {
/*    */             int hasLocalCount = 0;
/*    */             int hasGlobalCount = 0;
/*    */             BlockChunk blockChunkComponent = (BlockChunk)archetypeChunk.getComponent(index, BlockChunk.getComponentType());
/*    */             assert blockChunkComponent != null;
/*    */             for (int y = 0; y < 10; y++) {
/*    */               BlockSection section = blockChunkComponent.getSectionAtBlockY(y);
/*    */               if (section.hasLocalLight()) {
/*    */                 hasLocalCount++;
/*    */               }
/*    */               if (section.hasGlobalLight()) {
/*    */                 hasGlobalCount++;
/*    */               }
/*    */             } 
/*    */             total.getAndAdd(10);
/*    */             localLightCount.getAndAdd(hasLocalCount);
/*    */             globalLightCount.getAndAdd(hasGlobalCount);
/*    */           });
/* 67 */       context.sendMessage(Message.translation("server.commands.lighting.info.chunkDetails")
/* 68 */           .param("totalChunkSections", total.get())
/* 69 */           .param("chunksWithLocalLight", localLightCount.get())
/* 70 */           .param("chunksWithGlobalLight", globalLightCount.get()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\lighting\LightingInfoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */