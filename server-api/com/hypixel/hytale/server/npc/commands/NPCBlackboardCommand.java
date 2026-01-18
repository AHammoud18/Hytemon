/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.AndQuery;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeChunkPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.BlockPositionProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.IBlockPositionData;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.BlockRegionView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventType;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventType;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.interaction.InteractionView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.interaction.ReservationStatus;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.resource.ResourceView;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NPCBlackboardCommand extends AbstractCommandCollection {
/*     */   public NPCBlackboardCommand() {
/*  54 */     super("blackboard", "server.commands.npc.blackboard.desc");
/*  55 */     addSubCommand((AbstractCommand)new ChunksCommand());
/*  56 */     addSubCommand((AbstractCommand)new ChunkCommand());
/*  57 */     addSubCommand((AbstractCommand)new DropCommand());
/*  58 */     addSubCommand((AbstractCommand)new ViewsCommand());
/*  59 */     addSubCommand((AbstractCommand)new ViewCommand());
/*  60 */     addSubCommand((AbstractCommand)new BlockEventsCommand());
/*  61 */     addSubCommand((AbstractCommand)new EntityEventsCommand());
/*  62 */     addSubCommand((AbstractCommand)new ResourceViewsCommand());
/*  63 */     addSubCommand((AbstractCommand)new ResourceViewCommand());
/*  64 */     addSubCommand((AbstractCommand)new ReserveCommand());
/*  65 */     addSubCommand((AbstractCommand)new ReservationCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ChunksCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public ChunksCommand() {
/*  77 */       super("chunks", "server.commands.npc.blackboard.chunks.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  82 */       Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */ 
/*     */       
/*  85 */       StringBuilder sb = new StringBuilder("Blackboard chunk info:\n");
/*     */       
/*  87 */       int[] count = { 0 };
/*  88 */       chunkStore.forEachChunk((Query)BlockPositionProvider.getComponentType(), (archetypeChunk, commandBuffer) -> count[0] = count[0] + archetypeChunk.size());
/*     */ 
/*     */ 
/*     */       
/*  92 */       sb.append(" Total sections: ").append(count[0]).append('\n');
/*  93 */       sb.append(" Chunk sections:\n");
/*     */       
/*  95 */       Message msg = Message.translation("server.commands.npc.blackboard.chunks.chunkInfo").param("nb", count[0]);
/*     */       
/*  97 */       AndQuery<ChunkStore> query = Query.and(new Query[] { (Query)ChunkSection.getComponentType(), (Query)BlockPositionProvider.getComponentType() });
/*  98 */       chunkStore.forEachChunk((Query)query, (archetypeChunk, commandBuffer) -> {
/*     */             for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */               BlockPositionProvider blockPositionProviderComponent = (BlockPositionProvider)archetypeChunk.getComponent(index, BlockPositionProvider.getComponentType());
/*     */               
/*     */               assert blockPositionProviderComponent != null;
/*     */               
/*     */               ChunkSection chunkSectionComponent = (ChunkSection)archetypeChunk.getComponent(index, ChunkSection.getComponentType());
/*     */               
/*     */               assert chunkSectionComponent != null;
/*     */               
/*     */               int x = chunkSectionComponent.getX();
/*     */               
/*     */               int z = chunkSectionComponent.getZ();
/*     */               
/*     */               sb.append(' ').append(x).append(", ").append(chunkSectionComponent.getY()).append(", ").append(z);
/*     */               
/*     */               int[] entryCount = { 0 };
/*     */               
/*     */               blockPositionProviderComponent.forEachBlockSet(());
/*     */               
/*     */               sb.append(" (").append(entryCount[0]).append(" entries, ").append(blockPositionProviderComponent.getSearchedBlockSets().cardinality()).append(" BlockSets)\n");
/*     */               
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.chunks.detailed_entry").param("x", x).param("y", chunkSectionComponent.getY()).param("z", z).param("count", entryCount[0]).param("blockSets", blockPositionProviderComponent.getSearchedBlockSets().cardinality()));
/*     */             } 
/*     */           });
/* 123 */       context.sendMessage(msg);
/* 124 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ChunkCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     @Nonnull
/* 137 */     private final RequiredArg<RelativeIntPosition> positionArg = withRequiredArg("position", "server.commands.npc.blackboard.chunk.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ChunkCommand() {
/* 143 */       super("chunk", "server.commands.npc.blackboard.chunk.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 148 */       Vector3i blockPosition = ((RelativeIntPosition)this.positionArg.get(context)).getBlockPosition(context, (ComponentAccessor)store);
/*     */ 
/*     */ 
/*     */       
/* 152 */       Vector3i position = new Vector3i(ChunkUtil.chunkCoordinate(blockPosition.x), ChunkUtil.chunkCoordinate(blockPosition.y), ChunkUtil.chunkCoordinate(blockPosition.z));
/*     */       
/* 154 */       long chunkIndex = ChunkUtil.indexChunk(position.x, position.z);
/*     */ 
/*     */       
/* 157 */       StringBuilder sb = new StringBuilder("Blackboard chunk entry " + chunkIndex);
/* 158 */       sb.append(" (").append(position.x).append(", ").append(position.y).append(", ").append(position.z).append("):\n");
/* 159 */       sb.append(" Partial blackboard grid coordinates: ");
/* 160 */       sb.append(BlockRegionView.chunkToRegionalBlackboardCoordinate(position.x)).append(", ");
/* 161 */       sb.append(BlockRegionView.chunkToRegionalBlackboardCoordinate(position.z)).append('\n');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       Message msg = Message.translation("server.commands.npc.blackboard.chunk.entry").param("index", chunkIndex).param("chunkX", position.x).param("chunkY", position.y).param("chunkZ", position.z).param("regionChunkX", BlockRegionView.chunkToRegionalBlackboardCoordinate(position.x)).param("regionChunkZ", BlockRegionView.chunkToRegionalBlackboardCoordinate(position.z)).insert("\n");
/*     */       
/* 172 */       ChunkStore chunkStore = world.getChunkStore();
/* 173 */       Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/* 174 */       Ref<ChunkStore> chunkSection = chunkStore.getChunkSectionReference(position.x, position.y, position.z);
/*     */       
/* 176 */       if (chunkSection == null) {
/* 177 */         sb.append(" Chunk not loaded");
/* 178 */         msg.insert(Message.translation("server.commands.npc.blackboard.chunk.notLoaded"));
/*     */       } else {
/* 180 */         BlockPositionProvider entry = (BlockPositionProvider)chunkStoreStore.getComponent(chunkSection, BlockPositionProvider.getComponentType());
/* 181 */         if (entry == null) {
/* 182 */           sb.append(" No entry exists");
/* 183 */           msg.insert(Message.translation("server.commands.npc.blackboard.chunk.noEntry"));
/*     */         } else {
/* 185 */           sb.append(" Searched BlockSets: [ ");
/* 186 */           msg.insert(Message.translation("server.commands.npc.blockSetsSearched"));
/* 187 */           BitSet searchedBlockSets = entry.getSearchedBlockSets();
/* 188 */           boolean subsequent = false; int i;
/* 189 */           for (i = searchedBlockSets.nextSetBit(0); i >= 0; i = searchedBlockSets.nextSetBit(i + 1)) {
/* 190 */             if (subsequent) {
/* 191 */               sb.append(", ");
/* 192 */               msg.insert(", ");
/*     */             } 
/* 194 */             sb.append(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId());
/* 195 */             msg.insert(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId());
/* 196 */             subsequent = true;
/* 197 */             if (i == Integer.MAX_VALUE) {
/*     */               break;
/*     */             }
/*     */           } 
/* 201 */           sb.append(" ]\n Entries:\n");
/* 202 */           msg.insert(Message.translation("server.commands.npc.blackboard.chunk.entries"));
/* 203 */           entry.forEachBlockSet((blockSet, list) -> {
/*     */                 sb.append("  BlockSet: ").append(((BlockSet)BlockSet.getAssetMap().getAsset(blockSet)).getId()).append("\n   Blocks:\n");
/*     */ 
/*     */                 
/*     */                 msg.insert(Message.translation("server.commands.npc.blackboard.chunk.blockSet").param("id", ((BlockSet)BlockSet.getAssetMap().getAsset(blockSet)).getId()));
/*     */ 
/*     */                 
/*     */                 msg.insert("\n");
/*     */                 
/*     */                 list.forEach(());
/*     */               });
/*     */         } 
/*     */       } 
/*     */       
/* 217 */       context.sendMessage(msg);
/* 218 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DropCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public DropCommand() {
/* 231 */       super("drop", "server.commands.npc.blackboard.drop.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 236 */       ((Blackboard)store.getResource(Blackboard.getResourceType())).clear();
/* 237 */       context.sendMessage(Message.translation("server.commands.npc.blackboard.cleared"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ViewsCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public ViewsCommand() {
/* 249 */       super("views", "server.commands.npc.blackboard.views.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 255 */       StringBuilder sb = new StringBuilder("Blackboard views:\n");
/* 256 */       Message msg = Message.translation("server.commands.npc.blackboard.views.title");
/* 257 */       Blackboard blackboard = (Blackboard)store.getResource(Blackboard.getResourceType());
/*     */       
/* 259 */       int[] count = { 0 };
/* 260 */       blackboard.forEachView(BlockTypeView.class, entry -> count[0] = count[0] + 1);
/* 261 */       sb.append(" Total partial views: ").append(count[0]).append('\n').append(" Views:\n");
/* 262 */       msg.insert(Message.translation("server.commands.npc.blackboard.views.partialViews").param("count", count[0]));
/* 263 */       msg.insert("\n");
/*     */       
/* 265 */       blackboard.forEachView(BlockTypeView.class, entry -> {
/*     */             sb.append("  View (").append(BlockTypeView.xOfViewIndex(entry.getIndex())).append(", ").append(BlockTypeView.zOfViewIndex(entry.getIndex()));
/*     */ 
/*     */             
/*     */             sb.append(") Entities: ").append(entry.getEntities().size()).append(", BlockSets: ").append(entry.getAllBlockSets().cardinality()).append('\n');
/*     */             
/*     */             msg.insert(Message.translation("server.commands.npc.blackboard.views.view").param("x", BlockTypeView.xOfViewIndex(entry.getIndex())).param("z", BlockTypeView.zOfViewIndex(entry.getIndex())).param("size", entry.getEntities().size()).param("cardinal", entry.getAllBlockSets().cardinality()));
/*     */             
/*     */             msg.insert("\n");
/*     */           });
/*     */       
/* 276 */       context.sendMessage(msg);
/* 277 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ViewCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     @Nonnull
/* 290 */     private final RequiredArg<RelativeChunkPosition> chunkArg = withRequiredArg("chunk", "server.commands.npc.blackboard.view.chunk.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ViewCommand() {
/* 296 */       super("view", "server.commands.npc.blackboard.view.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 301 */       Vector2i chunkPosition = ((RelativeChunkPosition)this.chunkArg.get(context)).getChunkPosition(context, (ComponentAccessor)store);
/* 302 */       long viewIndex = BlockTypeView.indexView(chunkPosition.x, chunkPosition.y);
/*     */       
/* 304 */       Blackboard blackboard = (Blackboard)store.getResource(Blackboard.getResourceType());
/* 305 */       BlockTypeView view = (BlockTypeView)blackboard.getView(BlockTypeView.class, viewIndex);
/*     */       
/* 307 */       int viewX = BlockTypeView.xOfViewIndex(viewIndex);
/* 308 */       int viewZ = BlockTypeView.zOfViewIndex(viewIndex);
/*     */ 
/*     */       
/* 311 */       StringBuilder sb = new StringBuilder("View (");
/* 312 */       sb.append(viewX).append(", ").append(viewZ).append(")\n");
/* 313 */       sb.append(" Spans world coordinates: (").append(BlockTypeView.toWorldCoordinate(viewX)).append(", ").append(BlockTypeView.toWorldCoordinate(viewZ));
/* 314 */       sb.append(") to (").append(BlockTypeView.toWorldCoordinate(viewX + 1)).append(", ").append(BlockTypeView.toWorldCoordinate(viewZ + 1)).append(")\n");
/*     */       
/* 316 */       Message msg = Message.translation("server.commands.npc.blackboard.view.title").param("x", viewX).param("z", viewZ);
/* 317 */       msg.insert(Message.translation("server.commands.npc.blackboard.view.coordinates")
/* 318 */           .param("x1", BlockTypeView.toWorldCoordinate(viewX))
/* 319 */           .param("z1", BlockTypeView.toWorldCoordinate(viewZ))
/* 320 */           .param("x2", BlockTypeView.toWorldCoordinate(viewX + 1))
/* 321 */           .param("z2", BlockTypeView.toWorldCoordinate(viewZ + 1)));
/* 322 */       msg.insert("\n");
/*     */       
/* 324 */       if (view == null) {
/* 325 */         sb.append(" No partial view exists");
/* 326 */         msg.insert(Message.translation("server.commands.npc.blackboard.view.noPartialViews"));
/*     */       } else {
/* 328 */         sb.append(" Searched BlockSets: [ ");
/* 329 */         msg.insert(Message.translation("server.commands.npc.blockSetsSearched"));
/* 330 */         BitSet searchedBlockSets = view.getAllBlockSets();
/* 331 */         Int2IntMap counts = view.getBlockSetCounts();
/* 332 */         boolean subsequent = false; int i;
/* 333 */         for (i = searchedBlockSets.nextSetBit(0); i >= 0; i = searchedBlockSets.nextSetBit(i + 1)) {
/* 334 */           if (subsequent) {
/* 335 */             sb.append(", ");
/* 336 */             msg.insert(", ");
/*     */           } 
/* 338 */           sb.append(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId()).append(" (").append(counts.getOrDefault(i, 0)).append(')');
/* 339 */           msg.insert(((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId() + " (" + ((BlockSet)BlockSet.getAssetMap().getAsset(i)).getId() + ")");
/* 340 */           subsequent = true;
/* 341 */           if (i == Integer.MAX_VALUE) {
/*     */             break;
/*     */           }
/*     */         } 
/* 345 */         Set<Ref<EntityStore>> entities = view.getEntities();
/* 346 */         sb.append(" ]\n Entities (").append(entities.size()).append("):\n");
/* 347 */         msg.insert(Message.translation("server.commands.npc.blackboard.view.entities").param("count", entities.size()));
/*     */         
/* 349 */         entities.forEach(ref -> {
/*     */               sb.append("  [").append(ref.getIndex()).append("] ");
/*     */               
/*     */               msg.insert("  [" + ref.getIndex() + "] ");
/*     */               
/*     */               if (!ref.isValid()) {
/*     */                 sb.append("!!!INVALID ENTITY!!!");
/*     */                 
/*     */                 msg.insert(Message.translation("server.commands.npc.blackboard.view.invalidEntity"));
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               NPCEntity npc = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/*     */               if (npc == null) {
/*     */                 sb.append("!!!NON-NPC ENTITY!!!\n");
/*     */                 msg.insert(Message.translation("server.commands.npc.blackboard.view.nonNpcEntity"));
/*     */                 return;
/*     */               } 
/*     */               sb.append(npc.getRoleName()).append("\n    BlockSets: [ ");
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.view.blockSets"));
/*     */               IntList blockSets = npc.getBlackboardBlockTypeSets();
/*     */               for (int i = 0; i < blockSets.size(); i++) {
/*     */                 if (i > 0) {
/*     */                   sb.append(", ");
/*     */                   msg.insert(", ");
/*     */                 } 
/*     */                 String blockSetId = ((BlockSet)BlockSet.getAssetMap().getAsset(blockSets.getInt(i))).getId();
/*     */                 sb.append(blockSetId);
/*     */                 msg.insert(blockSetId);
/*     */               } 
/*     */               sb.append(" ]\n");
/*     */               msg.insert(" ]\n");
/*     */             });
/*     */       } 
/* 383 */       context.sendMessage(msg);
/* 384 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BlockEventsCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public BlockEventsCommand() {
/* 397 */       super("blockevents", "server.commands.npc.blackboard.blockevents.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 402 */       BlockEventView blockEventView = (BlockEventView)((Blackboard)store.getResource(Blackboard.getResourceType())).getView(BlockEventView.class, 0L);
/* 403 */       StringBuilder sb = new StringBuilder("Block Event View:\n");
/* 404 */       sb.append(" Total BlockSets: ").append(blockEventView.getSetCount());
/* 405 */       sb.append("\n BlockSets:\n");
/*     */       
/* 407 */       Message msg = Message.translation("server.commands.npc.blackboard.blockevents.title").param("count", blockEventView.getSetCount());
/* 408 */       blockEventView.forEach((b, t) -> {
/*     */             sb.append("  ").append(((BlockSet)BlockSet.getAssetMap().getAsset(b)).getId()).append(" (").append(t.get()).append("):\n");
/*     */             msg.insert("  " + ((BlockSet)BlockSet.getAssetMap().getAsset(b)).getId() + " (" + t.get() + "):\n");
/*     */           }e -> {
/*     */             UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(e, UUIDComponent.getComponentType());
/*     */             assert uuidComponent != null;
/*     */             UUID uuid = uuidComponent.getUuid();
/*     */             NPCEntity npcComponent = (NPCEntity)store.getComponent(e, NPCEntity.getComponentType());
/*     */             assert npcComponent != null;
/*     */             String roleName = npcComponent.getRoleName();
/*     */             sb.append("   ").append(uuid).append(": ").append(roleName).append("\n");
/*     */             msg.insert("   " + String.valueOf(uuid) + ": " + roleName + "\n");
/*     */           });
/* 421 */       context.sendMessage(msg);
/* 422 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EntityEventsCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public EntityEventsCommand() {
/* 434 */       super("entityevents", "server.commands.npc.blackboard.entityevents.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 439 */       EntityEventView view = (EntityEventView)((Blackboard)store.getResource(Blackboard.getResourceType())).getView(EntityEventView.class, 0L);
/* 440 */       StringBuilder sb = new StringBuilder("Entity Event View:\n");
/* 441 */       sb.append(" Total NPCGroups: ").append(view.getSetCount());
/* 442 */       sb.append("\n NPCGroups:\n");
/* 443 */       Message msg = Message.translation("server.commands.npc.blackboard.entityevents.title").param("count", view.getSetCount());
/* 444 */       view.forEach((b, t) -> {
/*     */             sb.append("  ").append(((NPCGroup)NPCGroup.getAssetMap().getAsset(b)).getId()).append(" (").append(t.get()).append("):\n");
/*     */             msg.insert("  " + ((NPCGroup)NPCGroup.getAssetMap().getAsset(b)).getId() + " (" + t.get() + "):\n");
/*     */           }e -> {
/*     */             UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(e, UUIDComponent.getComponentType());
/*     */             assert uuidComponent != null;
/*     */             UUID uuid = uuidComponent.getUuid();
/*     */             NPCEntity npcComponent = (NPCEntity)store.getComponent(e, NPCEntity.getComponentType());
/*     */             assert npcComponent != null;
/*     */             String roleName = npcComponent.getRoleName();
/*     */             sb.append("   ").append(uuid).append(": ").append(roleName).append("\n");
/*     */             msg.insert("   " + String.valueOf(uuid) + ": " + roleName + "\n");
/*     */           });
/* 457 */       context.sendMessage(msg);
/* 458 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ResourceViewsCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     public ResourceViewsCommand() {
/* 470 */       super("resourceviews", "server.commands.npc.blackboard.resourceviews.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 475 */       StringBuilder sb = new StringBuilder("Resource views:\n");
/* 476 */       Message msg = Message.translation("server.commands.npc.blackboard.resourceviews.title");
/* 477 */       Blackboard blackboard = (Blackboard)store.getResource(Blackboard.getResourceType());
/*     */       
/* 479 */       int[] count = { 0 };
/* 480 */       blackboard.forEachView(ResourceView.class, entry -> count[0] = count[0] + 1);
/* 481 */       sb.append(" Total resource views: ").append(count[0]).append('\n').append(" Views:\n");
/* 482 */       msg.insert(Message.translation("server.commands.npc.blackboard.resourceviews.totalViews").param("count", count[0]));
/*     */       
/* 484 */       blackboard.forEachView(ResourceView.class, entry -> {
/*     */             sb.append("  View (").append(ResourceView.xOfViewIndex(entry.getIndex())).append(", ").append(ResourceView.zOfViewIndex(entry.getIndex()));
/*     */ 
/*     */             
/*     */             sb.append(") Reservations: ").append(entry.getReservationsByEntity().size()).append('\n');
/*     */ 
/*     */             
/*     */             msg.insert(Message.translation("server.commands.npc.blackboard.resourceviews.view").param("x", ResourceView.xOfViewIndex(entry.getIndex())).param("z", ResourceView.zOfViewIndex(entry.getIndex())).param("count", entry.getReservationsByEntity().size()));
/*     */           });
/*     */       
/* 494 */       context.sendMessage(msg);
/* 495 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ResourceViewCommand
/*     */     extends AbstractWorldCommand
/*     */   {
/*     */     @Nonnull
/* 507 */     private final RequiredArg<RelativeChunkPosition> chunkArg = withRequiredArg("chunk", "server.commands.npc.blackboard.resourceview.chunk.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ResourceViewCommand() {
/* 513 */       super("resourceview", "server.commands.npc.blackboard.resourceview.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 518 */       Vector2i chunkPosition = ((RelativeChunkPosition)this.chunkArg.get(context)).getChunkPosition(context, (ComponentAccessor)store);
/* 519 */       long viewIndex = ResourceView.indexView(chunkPosition.x, chunkPosition.y);
/*     */       
/* 521 */       Blackboard blackboard = (Blackboard)store.getResource(Blackboard.getResourceType());
/* 522 */       ResourceView view = (ResourceView)blackboard.getView(ResourceView.class, viewIndex);
/*     */       
/* 524 */       int viewX = ResourceView.xOfViewIndex(viewIndex);
/* 525 */       int viewZ = ResourceView.zOfViewIndex(viewIndex);
/*     */ 
/*     */       
/* 528 */       StringBuilder sb = new StringBuilder("View (");
/* 529 */       sb.append(viewX).append(", ").append(viewZ).append(")\n");
/* 530 */       sb.append(" Spans world coordinates: (").append(ResourceView.toWorldCoordinate(viewX)).append(", ").append(ResourceView.toWorldCoordinate(viewZ));
/* 531 */       sb.append(") to (").append(ResourceView.toWorldCoordinate(viewX + 1)).append(", ").append(ResourceView.toWorldCoordinate(viewZ + 1)).append(")\n");
/*     */       
/* 533 */       Message msg = Message.translation("server.commands.npc.blackboard.view.title").param("x", viewX).param("z", viewZ);
/* 534 */       msg.insert(Message.translation("server.commands.npc.blackboard.view.coordinates")
/* 535 */           .param("x1", ResourceView.toWorldCoordinate(viewX))
/* 536 */           .param("z1", ResourceView.toWorldCoordinate(viewZ))
/* 537 */           .param("x2", ResourceView.toWorldCoordinate(viewX + 1))
/* 538 */           .param("z2", ResourceView.toWorldCoordinate(viewZ + 1)));
/* 539 */       msg.insert("\n");
/*     */       
/* 541 */       if (view == null) {
/* 542 */         sb.append(" No resource view exists");
/* 543 */         msg.insert(Message.translation("server.commands.npc.blackboard.resourceview.noResourceView"));
/*     */       } else {
/* 545 */         sb.append(" Reservations: [ ").append('\n');
/* 546 */         msg.insert(Message.translation("server.commands.npc.blackboard.resourceview.reservations"));
/* 547 */         view.getReservationsByEntity().forEach((ref, reservation) -> {
/*     */               if (!ref.isValid()) {
/*     */                 sb.append("!!!INVALID ENTITY!!!");
/*     */                 
/*     */                 msg.insert(Message.translation("server.commands.npc.blackboard.view.invalidEntity"));
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/*     */               UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/*     */               
/*     */               assert uuidComponent != null;
/*     */               
/*     */               UUID uuid = uuidComponent.getUuid();
/*     */               
/*     */               sb.append("  ").append(uuid).append(": ");
/*     */               msg.insert("  " + String.valueOf(uuid) + ": ");
/*     */               NPCEntity npc = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/*     */               if (npc == null) {
/*     */                 sb.append("!!!NON-NPC ENTITY!!!");
/*     */                 msg.insert(Message.translation("server.commands.npc.blackboard.view.nonNpcEntity"));
/*     */               } else {
/*     */                 sb.append(npc.getRoleName());
/*     */                 msg.insert(npc.getRoleName());
/*     */               } 
/*     */               int blockIndex = reservation.getBlockIndex();
/*     */               int blockX = ResourceView.xFromIndex(blockIndex) + (viewX << 7);
/*     */               int blockY = ResourceView.yFromIndex(blockIndex) + (reservation.getSectionIndex() << 5);
/*     */               int blockZ = ResourceView.zFromIndex(blockIndex) + (viewZ << 7);
/*     */               BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(world.getBlock(blockX, blockY, blockZ));
/*     */               sb.append(" reserved block ").append(blockType.getId()).append(" at ").append(blockX).append(", ").append(blockY).append(", ").append(blockZ).append('\n');
/*     */               msg.insert(Message.translation("server.commands.npc.blackboard.resourceview.reservedBlock").param("name", blockType.getId()).param("x", blockX).param("y", blockY).param("z", blockZ));
/*     */             });
/* 580 */         sb.append(" ]");
/* 581 */         msg.insert(" ]");
/*     */       } 
/* 583 */       context.sendMessage(msg);
/* 584 */       NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ReserveCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 596 */     private final RequiredArg<Boolean> reserveArg = withRequiredArg("reserve", "server.commands.npc.blackboard.reserve.reserve.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 601 */     private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 602 */       withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ReserveCommand() {
/* 608 */       super("reserve", "server.commands.npc.blackboard.reserve.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 618 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 619 */       assert playerComponent != null;
/*     */       
/* 621 */       UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 622 */       assert uuidComponent != null;
/*     */       
/* 624 */       UUID playerUUID = uuidComponent.getUuid();
/*     */       
/* 626 */       Ref<EntityStore> npcRef = getNPCRef(context, store);
/* 627 */       if (npcRef == null) {
/*     */         return;
/*     */       }
/*     */       
/* 631 */       NPCEntity npcEntity = (NPCEntity)store.getComponent(npcRef, NPCEntity.getComponentType());
/* 632 */       assert npcEntity != null;
/*     */       
/* 634 */       if (((Boolean)this.reserveArg.get(context)).booleanValue()) {
/* 635 */         npcEntity.addReservation(playerUUID);
/* 636 */         context.sendMessage(Message.translation("server.commands.npc.blackboard.roleReserved").param("role", npcEntity.getRoleName()));
/*     */       } else {
/* 638 */         npcEntity.removeReservation(playerUUID);
/* 639 */         context.sendMessage(Message.translation("server.commands.npc.blackboard.roleReleased").param("role", npcEntity.getRoleName()));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private Ref<EntityStore> getNPCRef(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store) {
/*     */       Ref<EntityStore> ref;
/* 647 */       if (this.entityArg.provided(context)) {
/* 648 */         ref = this.entityArg.get((ComponentAccessor)store, context);
/*     */       } else {
/* 650 */         Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/* 651 */         if (playerRef == null || !playerRef.isValid()) {
/* 652 */           context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/* 653 */               .param("option", "entity"));
/* 654 */           return null;
/*     */         } 
/* 656 */         ref = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store);
/* 657 */         if (ref == null) {
/* 658 */           context.sendMessage(Message.translation("server.commands.errors.no_entity_in_view")
/* 659 */               .param("option", "entity"));
/* 660 */           return null;
/*     */         } 
/*     */       } 
/*     */       
/* 664 */       if (ref == null) {
/* 665 */         return null;
/*     */       }
/*     */       
/* 668 */       NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 669 */       if (npcComponent == null) {
/* 670 */         UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 671 */         assert uuidComponent != null;
/* 672 */         UUID uuid = uuidComponent.getUuid();
/* 673 */         context.sendMessage(Message.translation("server.commands.errors.not_npc").param("uuid", uuid.toString()));
/* 674 */         return null;
/*     */       } 
/*     */       
/* 677 */       return ref;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ReservationCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 688 */     private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 689 */       withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ReservationCommand() {
/* 695 */       super("reservation", "server.commands.npc.blackboard.reservation.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 705 */       Ref<EntityStore> npcRef = getNPCRef(context, store);
/* 706 */       if (npcRef == null) {
/*     */         return;
/*     */       }
/*     */       
/* 710 */       NPCEntity npcEntity = (NPCEntity)store.getComponent(npcRef, NPCEntity.getComponentType());
/* 711 */       assert npcEntity != null;
/*     */       
/* 713 */       Blackboard blackBoardResource = (Blackboard)store.getResource(Blackboard.getResourceType());
/* 714 */       InteractionView reservationView = (InteractionView)blackBoardResource.getView(InteractionView.class, 0L);
/* 715 */       ReservationStatus reservationStatus = reservationView.getReservationStatus(npcRef, ref, (ComponentAccessor)store);
/* 716 */       context.sendMessage(Message.translation("server.commands.npc.blackboard.reservationStatus")
/* 717 */           .param("status", reservationStatus.toString()));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private Ref<EntityStore> getNPCRef(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store) {
/*     */       Ref<EntityStore> ref;
/* 724 */       if (this.entityArg.provided(context)) {
/* 725 */         ref = this.entityArg.get((ComponentAccessor)store, context);
/*     */       } else {
/* 727 */         Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/* 728 */         if (playerRef == null || !playerRef.isValid()) {
/* 729 */           context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/* 730 */               .param("option", "entity"));
/* 731 */           return null;
/*     */         } 
/* 733 */         ref = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store);
/* 734 */         if (ref == null) {
/* 735 */           context.sendMessage(Message.translation("server.commands.errors.no_entity_in_view")
/* 736 */               .param("option", "entity"));
/* 737 */           return null;
/*     */         } 
/*     */       } 
/*     */       
/* 741 */       if (ref == null) {
/* 742 */         return null;
/*     */       }
/*     */       
/* 745 */       NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 746 */       if (npcComponent == null) {
/* 747 */         UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 748 */         assert uuidComponent != null;
/* 749 */         UUID uuid = uuidComponent.getUuid();
/* 750 */         context.sendMessage(Message.translation("server.commands.errors.not_npc").param("uuid", uuid.toString()));
/* 751 */         return null;
/*     */       } 
/*     */       
/* 754 */       return ref;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCBlackboardCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */