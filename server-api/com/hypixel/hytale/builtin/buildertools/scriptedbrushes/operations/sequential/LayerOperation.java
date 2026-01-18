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
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderTool;
/*     */ import com.hypixel.hytale.server.core.codec.LayerEntryCodec;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
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
/*     */ 
/*     */ public class LayerOperation
/*     */   extends SequenceBrushOperation
/*     */ {
/*     */   public static final BuilderCodec<LayerOperation> CODEC;
/*     */   
/*     */   static {
/*  36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LayerOperation.class, LayerOperation::new).append(new KeyedCodec("Layers", (Codec)new ArrayCodec((Codec)LayerEntryCodec.CODEC, x$0 -> new LayerEntryCodec[x$0])), (op, val) -> op.layerArgs = (val != null) ? new ArrayList<>(Arrays.asList(val)) : List.<LayerEntryCodec>of(), op -> (op.layerArgs != null) ? op.layerArgs.<LayerEntryCodec>toArray(new LayerEntryCodec[0]) : new LayerEntryCodec[0]).documentation("The layers to set").add()).documentation("Replace blocks according to the specified layers in terms of their depth from the nearest air block")).build();
/*     */   }
/*  38 */   private List<LayerEntryCodec> layerArgs = new ArrayList<>();
/*     */   
/*     */   public LayerOperation() {
/*  41 */     super("Layer", "Replace blocks according to the specified layers in terms of their depth from the nearest air block", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifyBlocks(Ref<EntityStore> ref, BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/*  46 */     int maxDepth = 0;
/*  47 */     for (LayerEntryCodec entry : this.layerArgs) {
/*  48 */       maxDepth += entry.getDepth().intValue();
/*     */     }
/*     */     
/*  51 */     if (edit.getBlock(x, y, z) <= 0) {
/*  52 */       return true;
/*     */     }
/*     */     
/*  55 */     Map<String, Object> toolArgs = getToolArgs(ref, componentAccessor);
/*     */     
/*  57 */     for (int depth = 0; depth < maxDepth; depth++) {
/*  58 */       if (edit.getBlock(x, y + depth + 1, z) <= 0) {
/*  59 */         int depthTestingAt = 0;
/*  60 */         for (LayerEntryCodec entry : this.layerArgs) {
/*  61 */           depthTestingAt += entry.getDepth().intValue();
/*  62 */           if (depth < depthTestingAt) {
/*  63 */             int blockId = resolveBlockId(entry, toolArgs, brushConfig);
/*  64 */             if (blockId >= 0) {
/*  65 */               edit.setBlock(x, y, z, blockId);
/*     */             }
/*  67 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   private int resolveBlockId(LayerEntryCodec entry, @Nullable Map<String, Object> toolArgs, BrushConfig brushConfig) {
/*  77 */     if (entry.isUseToolArg()) {
/*  78 */       if (toolArgs == null || !toolArgs.containsKey(entry.getMaterial())) {
/*  79 */         brushConfig.setErrorFlag("Layer: Tool arg '" + entry.getMaterial() + "' not found");
/*  80 */         return -1;
/*     */       } 
/*  82 */       Object argValue = toolArgs.get(entry.getMaterial());
/*  83 */       if (argValue instanceof BlockPattern) { BlockPattern blockPattern = (BlockPattern)argValue;
/*  84 */         return blockPattern.nextBlock(brushConfig.getRandom()); }
/*     */       
/*  86 */       brushConfig.setErrorFlag("Layer: Tool arg '" + entry.getMaterial() + "' is not a Block type");
/*  87 */       return -1;
/*     */     } 
/*     */     
/*  90 */     return BlockType.getAssetMap().getIndex(entry.getMaterial());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Map<String, Object> getToolArgs(Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {
/*  96 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*  97 */     if (playerComponent == null) return null;
/*     */     
/*  99 */     BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/* 100 */     if (builderTool == null) return null;
/*     */     
/* 102 */     ItemStack itemStack = playerComponent.getInventory().getItemInHand();
/* 103 */     if (itemStack == null) return null;
/*     */     
/* 105 */     BuilderTool.ArgData argData = builderTool.getItemArgData(itemStack);
/* 106 */     return argData.tool();
/*     */   }
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\LayerOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */