/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeChunkPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.resource.ResourceView;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.UUID;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceViewCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   @Nonnull
/* 507 */   private final RequiredArg<RelativeChunkPosition> chunkArg = withRequiredArg("chunk", "server.commands.npc.blackboard.resourceview.chunk.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceViewCommand() {
/* 513 */     super("resourceview", "server.commands.npc.blackboard.resourceview.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 518 */     Vector2i chunkPosition = ((RelativeChunkPosition)this.chunkArg.get(context)).getChunkPosition(context, (ComponentAccessor)store);
/* 519 */     long viewIndex = ResourceView.indexView(chunkPosition.x, chunkPosition.y);
/*     */     
/* 521 */     Blackboard blackboard = (Blackboard)store.getResource(Blackboard.getResourceType());
/* 522 */     ResourceView view = (ResourceView)blackboard.getView(ResourceView.class, viewIndex);
/*     */     
/* 524 */     int viewX = ResourceView.xOfViewIndex(viewIndex);
/* 525 */     int viewZ = ResourceView.zOfViewIndex(viewIndex);
/*     */ 
/*     */     
/* 528 */     StringBuilder sb = new StringBuilder("View (");
/* 529 */     sb.append(viewX).append(", ").append(viewZ).append(")\n");
/* 530 */     sb.append(" Spans world coordinates: (").append(ResourceView.toWorldCoordinate(viewX)).append(", ").append(ResourceView.toWorldCoordinate(viewZ));
/* 531 */     sb.append(") to (").append(ResourceView.toWorldCoordinate(viewX + 1)).append(", ").append(ResourceView.toWorldCoordinate(viewZ + 1)).append(")\n");
/*     */     
/* 533 */     Message msg = Message.translation("server.commands.npc.blackboard.view.title").param("x", viewX).param("z", viewZ);
/* 534 */     msg.insert(Message.translation("server.commands.npc.blackboard.view.coordinates")
/* 535 */         .param("x1", ResourceView.toWorldCoordinate(viewX))
/* 536 */         .param("z1", ResourceView.toWorldCoordinate(viewZ))
/* 537 */         .param("x2", ResourceView.toWorldCoordinate(viewX + 1))
/* 538 */         .param("z2", ResourceView.toWorldCoordinate(viewZ + 1)));
/* 539 */     msg.insert("\n");
/*     */     
/* 541 */     if (view == null) {
/* 542 */       sb.append(" No resource view exists");
/* 543 */       msg.insert(Message.translation("server.commands.npc.blackboard.resourceview.noResourceView"));
/*     */     } else {
/* 545 */       sb.append(" Reservations: [ ").append('\n');
/* 546 */       msg.insert(Message.translation("server.commands.npc.blackboard.resourceview.reservations"));
/* 547 */       view.getReservationsByEntity().forEach((ref, reservation) -> {
/*     */             if (!ref.isValid()) {
/*     */               sb.append("!!!INVALID ENTITY!!!");
/*     */               
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.view.invalidEntity"));
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/*     */             
/*     */             assert uuidComponent != null;
/*     */             
/*     */             UUID uuid = uuidComponent.getUuid();
/*     */             
/*     */             sb.append("  ").append(uuid).append(": ");
/*     */             msg.insert("  " + String.valueOf(uuid) + ": ");
/*     */             NPCEntity npc = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/*     */             if (npc == null) {
/*     */               sb.append("!!!NON-NPC ENTITY!!!");
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.view.nonNpcEntity"));
/*     */             } else {
/*     */               sb.append(npc.getRoleName());
/*     */               msg.insert(npc.getRoleName());
/*     */             } 
/*     */             int blockIndex = reservation.getBlockIndex();
/*     */             int blockX = ResourceView.xFromIndex(blockIndex) + (viewX << 7);
/*     */             int blockY = ResourceView.yFromIndex(blockIndex) + (reservation.getSectionIndex() << 5);
/*     */             int blockZ = ResourceView.zFromIndex(blockIndex) + (viewZ << 7);
/*     */             BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(world.getBlock(blockX, blockY, blockZ));
/*     */             sb.append(" reserved block ").append(blockType.getId()).append(" at ").append(blockX).append(", ").append(blockY).append(", ").append(blockZ).append('\n');
/*     */             msg.insert(Message.translation("server.commands.npc.blackboard.resourceview.reservedBlock").param("name", blockType.getId()).param("x", blockX).param("y", blockY).param("z", blockZ));
/*     */           });
/* 580 */       sb.append(" ]");
/* 581 */       msg.insert(" ]");
/*     */     } 
/* 583 */     context.sendMessage(msg);
/* 584 */     NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCBlackboardCommand$ResourceViewCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */