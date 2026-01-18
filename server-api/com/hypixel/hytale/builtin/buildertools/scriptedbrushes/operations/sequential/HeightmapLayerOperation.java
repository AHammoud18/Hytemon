/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderTool;
/*     */ import com.hypixel.hytale.server.core.codec.LayerEntryCodec;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class HeightmapLayerOperation
/*     */   extends SequenceBrushOperation
/*     */ {
/*     */   public static final BuilderCodec<HeightmapLayerOperation> CODEC;
/*     */   
/*     */   static {
/*  37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HeightmapLayerOperation.class, HeightmapLayerOperation::new).append(new KeyedCodec("Layers", (Codec)new ArrayCodec((Codec)LayerEntryCodec.CODEC, x$0 -> new LayerEntryCodec[x$0])), (op, val) -> op.layerArgs = (val != null) ? new ArrayList<>(Arrays.asList(val)) : List.<LayerEntryCodec>of(), op -> (op.layerArgs != null) ? op.layerArgs.<LayerEntryCodec>toArray(new LayerEntryCodec[0]) : new LayerEntryCodec[0]).documentation("The layers to set").add()).documentation("Replace blocks according to the specified layers in terms of their depth from the tallest block in its column")).build();
/*     */   }
/*  39 */   private List<LayerEntryCodec> layerArgs = new ArrayList<>();
/*     */   
/*     */   public HeightmapLayerOperation() {
/*  42 */     super("Heightmap Layer", "Replace blocks according to the specified layers in terms of their depth from the tallest block in its column", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifyBlocks(Ref<EntityStore> ref, BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/*  47 */     WorldChunk chunk = edit.getAccessor().getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/*  48 */     int depth = chunk.getHeight(x, z) - y;
/*     */     
/*  50 */     if (depth < 0 || edit.getBlock(x, y, z) <= 0) {
/*  51 */       return true;
/*     */     }
/*     */     
/*  54 */     Map<String, Object> toolArgs = getToolArgs(ref, componentAccessor);
/*     */     
/*  56 */     int depthTestingAt = 0;
/*  57 */     for (LayerEntryCodec entry : this.layerArgs) {
/*  58 */       depthTestingAt += entry.getDepth().intValue();
/*  59 */       if (depth < depthTestingAt) {
/*  60 */         int blockId = resolveBlockId(entry, toolArgs, brushConfig);
/*  61 */         if (blockId >= 0) {
/*  62 */           edit.setBlock(x, y, z, blockId);
/*     */         }
/*  64 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   private int resolveBlockId(LayerEntryCodec entry, @Nullable Map<String, Object> toolArgs, BrushConfig brushConfig) {
/*  72 */     if (entry.isUseToolArg()) {
/*  73 */       if (toolArgs == null || !toolArgs.containsKey(entry.getMaterial())) {
/*  74 */         brushConfig.setErrorFlag("HeightmapLayer: Tool arg '" + entry.getMaterial() + "' not found");
/*  75 */         return -1;
/*     */       } 
/*  77 */       Object argValue = toolArgs.get(entry.getMaterial());
/*  78 */       if (argValue instanceof BlockPattern) { BlockPattern blockPattern = (BlockPattern)argValue;
/*  79 */         return blockPattern.nextBlock(brushConfig.getRandom()); }
/*     */       
/*  81 */       brushConfig.setErrorFlag("HeightmapLayer: Tool arg '" + entry.getMaterial() + "' is not a Block type");
/*  82 */       return -1;
/*     */     } 
/*     */     
/*  85 */     return BlockType.getAssetMap().getIndex(entry.getMaterial());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Map<String, Object> getToolArgs(Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {
/*  91 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*  92 */     if (playerComponent == null) return null;
/*     */     
/*  94 */     BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/*  95 */     if (builderTool == null) return null;
/*     */     
/*  97 */     ItemStack itemStack = playerComponent.getInventory().getItemInHand();
/*  98 */     if (itemStack == null) return null;
/*     */     
/* 100 */     BuilderTool.ArgData argData = builderTool.getItemArgData(itemStack);
/* 101 */     return argData.tool();
/*     */   }
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\HeightmapLayerOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */