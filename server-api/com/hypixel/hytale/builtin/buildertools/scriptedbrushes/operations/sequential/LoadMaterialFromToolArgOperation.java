/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderTool;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoadMaterialFromToolArgOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<LoadMaterialFromToolArgOperation> CODEC;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LoadMaterialFromToolArgOperation.class, LoadMaterialFromToolArgOperation::new).append(new KeyedCodec("ArgName", (Codec)Codec.STRING), (op, val) -> op.argNameArg = val, op -> op.argNameArg).documentation("The name of the Block tool arg to load the material pattern from").add()).documentation("Load a block pattern from a Block tool arg and set it as the brush material")).build();
/*    */   } @Nonnull
/* 30 */   public String argNameArg = "";
/*    */ 
/*    */   
/*    */   public LoadMaterialFromToolArgOperation() {
/* 34 */     super("Load Material", "Load material pattern from a Block tool arg", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 39 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 40 */     assert playerComponent != null;
/*    */     
/* 42 */     BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/* 43 */     if (builderTool == null) {
/* 44 */       brushConfig.setErrorFlag("LoadMaterial: No active builder tool");
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     ItemStack itemStack = playerComponent.getInventory().getItemInHand();
/* 49 */     if (itemStack == null) {
/* 50 */       brushConfig.setErrorFlag("LoadMaterial: No item in hand");
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     BuilderTool.ArgData argData = builderTool.getItemArgData(itemStack);
/* 55 */     Map<String, Object> toolArgs = argData.tool();
/*    */     
/* 57 */     if (toolArgs == null || !toolArgs.containsKey(this.argNameArg)) {
/* 58 */       brushConfig.setErrorFlag("LoadMaterial: Tool arg '" + this.argNameArg + "' not found");
/*    */       
/*    */       return;
/*    */     } 
/* 62 */     Object argValue = toolArgs.get(this.argNameArg);
/*    */     
/* 64 */     if (argValue instanceof BlockPattern) { BlockPattern blockPattern = (BlockPattern)argValue;
/* 65 */       brushConfig.setPattern(blockPattern); }
/*    */     else
/* 67 */     { brushConfig.setErrorFlag("LoadMaterial: Tool arg '" + this.argNameArg + "' is not a Block type (found " + argValue.getClass().getSimpleName() + ")"); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\LoadMaterialFromToolArgOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */