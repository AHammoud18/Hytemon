/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.AndQuery;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.BlockPositionProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
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
/*     */ public class ChunksCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   public ChunksCommand() {
/*  77 */     super("chunks", "server.commands.npc.blackboard.chunks.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  82 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */ 
/*     */     
/*  85 */     StringBuilder sb = new StringBuilder("Blackboard chunk info:\n");
/*     */     
/*  87 */     int[] count = { 0 };
/*  88 */     chunkStore.forEachChunk((Query)BlockPositionProvider.getComponentType(), (archetypeChunk, commandBuffer) -> count[0] = count[0] + archetypeChunk.size());
/*     */ 
/*     */ 
/*     */     
/*  92 */     sb.append(" Total sections: ").append(count[0]).append('\n');
/*  93 */     sb.append(" Chunk sections:\n");
/*     */     
/*  95 */     Message msg = Message.translation("server.commands.npc.blackboard.chunks.chunkInfo").param("nb", count[0]);
/*     */     
/*  97 */     AndQuery<ChunkStore> query = Query.and(new Query[] { (Query)ChunkSection.getComponentType(), (Query)BlockPositionProvider.getComponentType() });
/*  98 */     chunkStore.forEachChunk((Query)query, (archetypeChunk, commandBuffer) -> {
/*     */           for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */             BlockPositionProvider blockPositionProviderComponent = (BlockPositionProvider)archetypeChunk.getComponent(index, BlockPositionProvider.getComponentType());
/*     */             
/*     */             assert blockPositionProviderComponent != null;
/*     */             
/*     */             ChunkSection chunkSectionComponent = (ChunkSection)archetypeChunk.getComponent(index, ChunkSection.getComponentType());
/*     */             
/*     */             assert chunkSectionComponent != null;
/*     */             
/*     */             int x = chunkSectionComponent.getX();
/*     */             
/*     */             int z = chunkSectionComponent.getZ();
/*     */             
/*     */             sb.append(' ').append(x).append(", ").append(chunkSectionComponent.getY()).append(", ").append(z);
/*     */             
/*     */             int[] entryCount = { 0 };
/*     */             
/*     */             blockPositionProviderComponent.forEachBlockSet(());
/*     */             
/*     */             sb.append(" (").append(entryCount[0]).append(" entries, ").append(blockPositionProviderComponent.getSearchedBlockSets().cardinality()).append(" BlockSets)\n");
/*     */             
/*     */             msg.insert(Message.translation("server.commands.npc.blackboard.chunks.detailed_entry").param("x", x).param("y", chunkSectionComponent.getY()).param("z", z).param("count", entryCount[0]).param("blockSets", blockPositionProviderComponent.getSearchedBlockSets().cardinality()));
/*     */           } 
/*     */         });
/* 123 */     context.sendMessage(msg);
/* 124 */     NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCBlackboardCommand$ChunksCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */