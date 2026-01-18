/*     */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.WildcardMatch;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockRowCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*  43 */   private final RequiredArg<String> queryArg = withRequiredArg("wildcard block query", "server.commands.block.row.arg.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_MATCHES = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockRowCommand() {
/*  54 */     super("row", "server.commands.block.row.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  59 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  60 */     assert transformComponent != null;
/*     */     
/*  62 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  63 */     assert headRotationComponent != null;
/*     */     
/*  65 */     Vector3d playerPos = transformComponent.getPosition();
/*  66 */     Vector3i axisDirection = getDominantCardinal(headRotationComponent.getDirection());
/*     */     
/*  68 */     String query = (String)context.get((Argument)this.queryArg);
/*     */     
/*  70 */     List<BlockType> blockTypes = findBlockTypes(query);
/*  71 */     if (blockTypes.isEmpty()) {
/*  72 */       playerRef.sendMessage(Message.translation("server.commands.block.row.nonefound")
/*  73 */           .param("query", query));
/*     */       
/*  75 */       List<String> fuzzyMatches = StringUtil.sortByFuzzyDistance(query, BlockType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT);
/*  76 */       if (!fuzzyMatches.isEmpty()) {
/*  77 */         playerRef.sendMessage(Message.translation("server.commands.block.row.fuzzymatches")
/*  78 */             .param("choices", fuzzyMatches.toString()));
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     if (blockTypes.size() >= 64000) {
/*  84 */       playerRef.sendMessage(Message.translation("server.commands.block.row.toomanymatches")
/*  85 */           .param("max", 64));
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     spawnBlocksRow(world, playerPos, axisDirection, blockTypes);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnBlocksRow(@Nonnull World world, @Nonnull Vector3d origin, @Nonnull Vector3i direction, @Nonnull List<BlockType> blockTypes) {
/* 112 */     IndexedLookupTableAssetMap<String, BlockBoundingBoxes> boundingBoxes = BlockBoundingBoxes.getAssetMap();
/* 113 */     Axis axis = getAxis(direction);
/*     */     
/* 115 */     int step = 25; int x;
/* 116 */     for (x = 0; x < blockTypes.size(); x += step) {
/* 117 */       double distance = 1.0D;
/* 118 */       for (int i = 0; i < step; i++) {
/* 119 */         BlockType blockType = blockTypes.get(i + x);
/*     */ 
/*     */         
/* 122 */         Box boundingBox = ((BlockBoundingBoxes)boundingBoxes.getAsset(blockType.getHitboxTypeIndex())).get(0).getBoundingBox();
/* 123 */         double dimension = Math.ceil(boundingBox.dimension(axis));
/*     */         
/* 125 */         distance += Math.floor(dimension) + 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 130 */         Vector3i blockPos = origin.clone().add(direction.clone().scale(distance)).add(Rotation.Ninety.rotateY(direction, new Vector3i()).scale(x / step * 2)).toVector3i();
/*     */         
/* 132 */         long chunkIndex = ChunkUtil.indexChunkFromBlock(blockPos.x, blockPos.z);
/* 133 */         world.getChunkAsync(chunkIndex).thenAccept(chunk -> {
/*     */               int settings = 196;
/*     */               chunk.setBlock(blockPos.x, blockPos.y, blockPos.z, blockType, 196);
/*     */             });
/*     */       } 
/*     */     } 
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
/*     */   @Nonnull
/*     */   private static Vector3i getDominantCardinal(@Nonnull Vector3d direction) {
/* 151 */     double ax = Math.abs(direction.x);
/* 152 */     double ay = Math.abs(direction.y);
/* 153 */     double az = Math.abs(direction.z);
/*     */     
/* 155 */     if (ax > ay && ax > az)
/* 156 */       return new Vector3i((int)Math.signum(direction.x), 0, 0); 
/* 157 */     if (ay > az) {
/* 158 */       return new Vector3i(0, (int)Math.signum(direction.y), 0);
/*     */     }
/* 160 */     return new Vector3i(0, 0, (int)Math.signum(direction.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Axis getAxis(@Nonnull Vector3i direction) {
/* 171 */     if (direction.x != 0) return Axis.X; 
/* 172 */     if (direction.z != 0) return Axis.Z; 
/* 173 */     return Axis.Y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private List<BlockType> findBlockTypes(String wildcardQuery) {
/* 184 */     ObjectArrayList<BlockType> objectArrayList = new ObjectArrayList();
/*     */     
/* 186 */     BlockTypeAssetMap<String, BlockType> blockTypeAssets = BlockType.getAssetMap();
/* 187 */     Set<String> parentKeys = blockTypeAssets.getAssetMap().keySet();
/* 188 */     for (String blockName : parentKeys) {
/*     */       
/* 190 */       if (WildcardMatch.test(blockName, wildcardQuery)) {
/* 191 */         BlockType blockType = (BlockType)blockTypeAssets.getAsset(blockName);
/* 192 */         objectArrayList.add(blockType);
/*     */       } 
/*     */     } 
/* 195 */     return objectArrayList.stream().sorted(Comparator.comparing(BlockType::getId)).toList();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockRowCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */