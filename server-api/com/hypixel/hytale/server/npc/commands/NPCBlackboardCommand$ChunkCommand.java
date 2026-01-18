/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.BlockPositionProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.IBlockPositionData;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.BlockRegionView;
/*     */ import java.util.BitSet;
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
/*     */ public class ChunkCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   @Nonnull
/* 137 */   private final RequiredArg<RelativeIntPosition> positionArg = withRequiredArg("position", "server.commands.npc.blackboard.chunk.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkCommand() {
/* 143 */     super("chunk", "server.commands.npc.blackboard.chunk.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 148 */     Vector3i blockPosition = ((RelativeIntPosition)this.positionArg.get(context)).getBlockPosition(context, (ComponentAccessor)store);
/*     */ 
/*     */ 
/*     */     
/* 152 */     Vector3i position = new Vector3i(ChunkUtil.chunkCoordinate(blockPosition.x), ChunkUtil.chunkCoordinate(blockPosition.y), ChunkUtil.chunkCoordinate(blockPosition.z));
/*     */     
/* 154 */     long chunkIndex = ChunkUtil.indexChunk(position.x, position.z);
/*     */ 
/*     */     
/* 157 */     StringBuilder sb = new StringBuilder("Blackboard chunk entry " + chunkIndex);
/* 158 */     sb.append(" (").append(position.x).append(", ").append(position.y).append(", ").append(position.z).append("):\n");
/* 159 */     sb.append(" Partial blackboard grid coordinates: ");
/* 160 */     sb.append(BlockRegionView.chunkToRegionalBlackboardCoordinate(position.x)).append(", ");
/* 161 */     sb.append(BlockRegionView.chunkToRegionalBlackboardCoordinate(position.z)).append('\n');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     Message msg = Message.translation("server.commands.npc.blackboard.chunk.entry").param("index", chunkIndex).param("chunkX", position.x).param("chunkY", position.y).param("chunkZ", position.z).param("regionChunkX", BlockRegionView.chunkToRegionalBlackboardCoordinate(position.x)).param("regionChunkZ", BlockRegionView.chunkToRegionalBlackboardCoordinate(position.z)).insert("\n");
/*     */     
/* 172 */     ChunkStore chunkStore = world.getChunkStore();
/* 173 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/* 174 */     Ref<ChunkStore> chunkSection = chunkStore.getChunkSectionReference(position.x, position.y, position.z);
/*     */     
/* 176 */     if (chunkSection == null) {
/* 177 */       sb.append(" Chunk not loaded");
/* 178 */       msg.insert(Message.translation("server.commands.npc.blackboard.chunk.notLoaded"));
/*     */     } else {
/* 180 */       BlockPositionProvider entry = (BlockPositionProvider)chunkStoreStore.getComponent(chunkSection, BlockPositionProvider.getComponentType());
/* 181 */       if (entry == null) {
/* 182 */         sb.append(" No entry exists");
/* 183 */         msg.insert(Message.translation("server.commands.npc.blackboard.chunk.noEntry"));
/*     */       } else {
/* 185 */         sb.append(" Searched BlockSets: [ ");
/* 186 */         msg.insert(Message.translation("server.commands.npc.blockSetsSearched"));
/* 187 */         BitSet searchedBlockSets = entry.getSearchedBlockSets();
/* 188 */         boolean subsequent = false; int i;
/* 189 */         for (i = searchedBlockSets.nextSetBit(0); i >= 0; i = searchedBlockSets.nextSetBit(i + 1)) {
/* 190 */           if (subsequent) {
/* 191 */             sb.append(", ");
/* 192 */             msg.insert(", ");
/*     */           } 
/* 194 */           sb.append(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId());
/* 195 */           msg.insert(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId());
/* 196 */           subsequent = true;
/* 197 */           if (i == Integer.MAX_VALUE) {
/*     */             break;
/*     */           }
/*     */         } 
/* 201 */         sb.append(" ]\n Entries:\n");
/* 202 */         msg.insert(Message.translation("server.commands.npc.blackboard.chunk.entries"));
/* 203 */         entry.forEachBlockSet((blockSet, list) -> {
/*     */               sb.append("  BlockSet: ").append(((BlockSet)BlockSet.getAssetMap().getAsset(blockSet)).getId()).append("\n   Blocks:\n");
/*     */ 
/*     */               
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.chunk.blockSet").param("id", ((BlockSet)BlockSet.getAssetMap().getAsset(blockSet)).getId()));
/*     */ 
/*     */               
/*     */               msg.insert("\n");
/*     */               
/*     */               list.forEach(());
/*     */             });
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     context.sendMessage(msg);
/* 218 */     NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCBlackboardCommand$ChunkCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */