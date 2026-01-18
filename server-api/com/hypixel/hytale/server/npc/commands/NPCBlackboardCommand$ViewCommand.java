/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeChunkPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeView;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViewCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   @Nonnull
/* 290 */   private final RequiredArg<RelativeChunkPosition> chunkArg = withRequiredArg("chunk", "server.commands.npc.blackboard.view.chunk.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ViewCommand() {
/* 296 */     super("view", "server.commands.npc.blackboard.view.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 301 */     Vector2i chunkPosition = ((RelativeChunkPosition)this.chunkArg.get(context)).getChunkPosition(context, (ComponentAccessor)store);
/* 302 */     long viewIndex = BlockTypeView.indexView(chunkPosition.x, chunkPosition.y);
/*     */     
/* 304 */     Blackboard blackboard = (Blackboard)store.getResource(Blackboard.getResourceType());
/* 305 */     BlockTypeView view = (BlockTypeView)blackboard.getView(BlockTypeView.class, viewIndex);
/*     */     
/* 307 */     int viewX = BlockTypeView.xOfViewIndex(viewIndex);
/* 308 */     int viewZ = BlockTypeView.zOfViewIndex(viewIndex);
/*     */ 
/*     */     
/* 311 */     StringBuilder sb = new StringBuilder("View (");
/* 312 */     sb.append(viewX).append(", ").append(viewZ).append(")\n");
/* 313 */     sb.append(" Spans world coordinates: (").append(BlockTypeView.toWorldCoordinate(viewX)).append(", ").append(BlockTypeView.toWorldCoordinate(viewZ));
/* 314 */     sb.append(") to (").append(BlockTypeView.toWorldCoordinate(viewX + 1)).append(", ").append(BlockTypeView.toWorldCoordinate(viewZ + 1)).append(")\n");
/*     */     
/* 316 */     Message msg = Message.translation("server.commands.npc.blackboard.view.title").param("x", viewX).param("z", viewZ);
/* 317 */     msg.insert(Message.translation("server.commands.npc.blackboard.view.coordinates")
/* 318 */         .param("x1", BlockTypeView.toWorldCoordinate(viewX))
/* 319 */         .param("z1", BlockTypeView.toWorldCoordinate(viewZ))
/* 320 */         .param("x2", BlockTypeView.toWorldCoordinate(viewX + 1))
/* 321 */         .param("z2", BlockTypeView.toWorldCoordinate(viewZ + 1)));
/* 322 */     msg.insert("\n");
/*     */     
/* 324 */     if (view == null) {
/* 325 */       sb.append(" No partial view exists");
/* 326 */       msg.insert(Message.translation("server.commands.npc.blackboard.view.noPartialViews"));
/*     */     } else {
/* 328 */       sb.append(" Searched BlockSets: [ ");
/* 329 */       msg.insert(Message.translation("server.commands.npc.blockSetsSearched"));
/* 330 */       BitSet searchedBlockSets = view.getAllBlockSets();
/* 331 */       Int2IntMap counts = view.getBlockSetCounts();
/* 332 */       boolean subsequent = false; int i;
/* 333 */       for (i = searchedBlockSets.nextSetBit(0); i >= 0; i = searchedBlockSets.nextSetBit(i + 1)) {
/* 334 */         if (subsequent) {
/* 335 */           sb.append(", ");
/* 336 */           msg.insert(", ");
/*     */         } 
/* 338 */         sb.append(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId()).append(" (").append(counts.getOrDefault(i, 0)).append(')');
/* 339 */         msg.insert(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId() + " (" + ((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId() + ")");
/* 340 */         subsequent = true;
/* 341 */         if (i == Integer.MAX_VALUE) {
/*     */           break;
/*     */         }
/*     */       } 
/* 345 */       Set<Ref<EntityStore>> entities = view.getEntities();
/* 346 */       sb.append(" ]\n Entities (").append(entities.size()).append("):\n");
/* 347 */       msg.insert(Message.translation("server.commands.npc.blackboard.view.entities").param("count", entities.size()));
/*     */       
/* 349 */       entities.forEach(ref -> {
/*     */             sb.append("  [").append(ref.getIndex()).append("] ");
/*     */             
/*     */             msg.insert("  [" + ref.getIndex() + "] ");
/*     */             
/*     */             if (!ref.isValid()) {
/*     */               sb.append("!!!INVALID ENTITY!!!");
/*     */               
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.view.invalidEntity"));
/*     */               
/*     */               return;
/*     */             } 
/*     */             NPCEntity npc = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/*     */             if (npc == null) {
/*     */               sb.append("!!!NON-NPC ENTITY!!!\n");
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.view.nonNpcEntity"));
/*     */               return;
/*     */             } 
/*     */             sb.append(npc.getRoleName()).append("\n    BlockSets: [ ");
/*     */             msg.insert(Message.translation("server.commands.npc.blackboard.view.blockSets"));
/*     */             IntList blockSets = npc.getBlackboardBlockTypeSets();
/*     */             for (int i = 0; i < blockSets.size(); i++) {
/*     */               if (i > 0) {
/*     */                 sb.append(", ");
/*     */                 msg.insert(", ");
/*     */               } 
/*     */               String blockSetId = ((BlockSet)BlockSet.getAssetMap().getAsset(blockSets.getInt(i))).getId();
/*     */               sb.append(blockSetId);
/*     */               msg.insert(blockSetId);
/*     */             } 
/*     */             sb.append(" ]\n");
/*     */             msg.insert(" ]\n");
/*     */           });
/*     */     } 
/* 383 */     context.sendMessage(msg);
/* 384 */     NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCBlackboardCommand$ViewCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */