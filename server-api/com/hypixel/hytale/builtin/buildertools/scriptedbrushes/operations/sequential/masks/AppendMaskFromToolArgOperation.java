/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.masks;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderTool;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockFilter;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockMask;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppendMaskFromToolArgOperation
/*     */   extends SequenceBrushOperation
/*     */ {
/*     */   public static final BuilderCodec<AppendMaskFromToolArgOperation> CODEC;
/*     */   
/*     */   static {
/*  55 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AppendMaskFromToolArgOperation.class, AppendMaskFromToolArgOperation::new).append(new KeyedCodec("ArgName", (Codec)Codec.STRING, true), (op, val) -> op.argNameArg = val, op -> op.argNameArg).documentation("The name of the Block tool arg to read the material from").add()).append(new KeyedCodec("FilterType", (Codec)new EnumCodec(BlockFilter.FilterType.class)), (op, val) -> op.filterTypeArg = val, op -> op.filterTypeArg).documentation("The type of block filter mask to apply (e.g., TARGET_BLOCK, ABOVE_BLOCK, BELOW_BLOCK)").add()).append(new KeyedCodec("Invert", (Codec)Codec.BOOLEAN, true), (op, val) -> op.invertArg = val.booleanValue(), op -> Boolean.valueOf(op.invertArg)).documentation("Whether to invert the block filter mask or not").add()).append(new KeyedCodec("AdditionalBlocks", (Codec)Codec.STRING), (op, val) -> op.additionalBlocksArg = val, op -> op.additionalBlocksArg).documentation("Additional block names to append to the mask, comma separated (e.g., Rock_Stone,Rock_Granite)").add()).documentation("Append a mask from a Block tool arg with configurable filter type and optional additional blocks")).build();
/*     */   } @Nonnull
/*  57 */   public String argNameArg = "";
/*     */   
/*     */   @Nonnull
/*     */   public boolean invertArg = false;
/*     */   
/*     */   @Nonnull
/*  63 */   public BlockFilter.FilterType filterTypeArg = BlockFilter.FilterType.TargetBlock;
/*     */   
/*     */   @Nullable
/*     */   public String additionalBlocksArg;
/*     */ 
/*     */   
/*     */   public AppendMaskFromToolArgOperation() {
/*  70 */     super("Append Mask From Tool Arg", "Append a mask from a Block tool arg with configurable filter type", false);
/*     */   }
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     BlockPattern blockPattern;
/*  75 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*  76 */     assert playerComponent != null;
/*     */     
/*  78 */     BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/*  79 */     if (builderTool == null) {
/*  80 */       brushConfig.setErrorFlag("AppendMaskFromToolArg: No active builder tool");
/*     */       
/*     */       return;
/*     */     } 
/*  84 */     ItemStack itemStack = playerComponent.getInventory().getItemInHand();
/*  85 */     if (itemStack == null) {
/*  86 */       brushConfig.setErrorFlag("AppendMaskFromToolArg: No item in hand");
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     BuilderTool.ArgData argData = builderTool.getItemArgData(itemStack);
/*  91 */     Map<String, Object> toolArgs = argData.tool();
/*     */     
/*  93 */     if (toolArgs == null || !toolArgs.containsKey(this.argNameArg)) {
/*  94 */       brushConfig.setErrorFlag("AppendMaskFromToolArg: Tool arg '" + this.argNameArg + "' not found");
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     Object argValue = toolArgs.get(this.argNameArg);
/*     */     
/* 100 */     if (argValue instanceof BlockPattern) { blockPattern = (BlockPattern)argValue; }
/* 101 */     else { brushConfig.setErrorFlag("AppendMaskFromToolArg: Tool arg '" + this.argNameArg + "' is not a Block type (found " + argValue.getClass().getSimpleName() + ")");
/*     */       
/*     */       return; }
/*     */     
/* 105 */     List<String> blockNames = new ArrayList<>();
/*     */ 
/*     */     
/* 108 */     String patternStr = blockPattern.toString();
/* 109 */     if (!patternStr.isEmpty() && !patternStr.equals("-")) {
/* 110 */       for (String entry : patternStr.split(",")) {
/*     */         
/* 112 */         int percentIdx = entry.indexOf('%');
/* 113 */         String blockName = (percentIdx >= 0) ? entry.substring(percentIdx + 1) : entry;
/* 114 */         if (!blockName.isEmpty()) {
/* 115 */           blockNames.add(blockName);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 121 */     if (this.additionalBlocksArg != null && !this.additionalBlocksArg.isEmpty()) {
/* 122 */       for (String block : this.additionalBlocksArg.split(",")) {
/* 123 */         String trimmed = block.trim();
/* 124 */         if (!trimmed.isEmpty()) {
/* 125 */           blockNames.add(trimmed);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 130 */     if (blockNames.isEmpty()) {
/* 131 */       brushConfig.setErrorFlag("AppendMaskFromToolArg: No blocks to add to mask");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 136 */     BlockFilter filter = new BlockFilter(this.filterTypeArg, blockNames.<String>toArray(new String[0]), this.invertArg);
/* 137 */     BlockMask mask = new BlockMask(new BlockFilter[] { filter });
/*     */     
/* 139 */     brushConfig.appendOperationMask(mask);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\masks\AppendMaskFromToolArgOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */