/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential.flowcontrol.loops;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BuilderTool;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
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
/*     */ public class LoadLoopFromToolArgOperation
/*     */   extends SequenceBrushOperation
/*     */ {
/*     */   public static final int MAX_REPETITIONS = 100;
/*     */   public static final int IDLE_STATE = -1;
/*     */   public static final BuilderCodec<LoadLoopFromToolArgOperation> CODEC;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LoadLoopFromToolArgOperation.class, LoadLoopFromToolArgOperation::new).append(new KeyedCodec("StoredIndexName", (Codec)Codec.STRING), (op, val) -> op.indexNameArg = val, op -> op.indexNameArg).documentation("The name of the previously stored index to begin the loop at. Note: This can only be an index previous to the current.").add()).append(new KeyedCodec("ArgName", (Codec)Codec.STRING), (op, val) -> op.argNameArg = val, op -> op.argNameArg).documentation("The amount of additional times to repeat the loop after the initial, normal execution").add()).documentation("Loop the execution of instructions a set amount of times")).build();
/*     */   } @Nonnull
/*  40 */   public String indexNameArg = "Undefined";
/*     */   @Nonnull
/*  42 */   public String argNameArg = "";
/*     */ 
/*     */   
/*  45 */   private int repetitionsRemaining = -1;
/*     */   
/*     */   public LoadLoopFromToolArgOperation() {
/*  48 */     super("Loop Operations", "Loop the execution of instructions a variable amount of times", false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetInternalState() {
/*  53 */     this.repetitionsRemaining = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  60 */     if (this.repetitionsRemaining == -1) {
/*     */       
/*  62 */       Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*  63 */       assert playerComponent != null;
/*     */       
/*  65 */       BuilderTool builderTool = BuilderTool.getActiveBuilderTool(playerComponent);
/*  66 */       if (builderTool == null) {
/*  67 */         brushConfig.setErrorFlag("LoadLoop: No active builder tool");
/*     */         
/*     */         return;
/*     */       } 
/*  71 */       ItemStack itemStack = playerComponent.getInventory().getItemInHand();
/*  72 */       if (itemStack == null) {
/*  73 */         brushConfig.setErrorFlag("LoadLoop: No item in hand");
/*     */         
/*     */         return;
/*     */       } 
/*  77 */       BuilderTool.ArgData argData = builderTool.getItemArgData(itemStack);
/*  78 */       Map<String, Object> toolArgs = argData.tool();
/*     */       
/*  80 */       if (toolArgs == null || !toolArgs.containsKey(this.argNameArg)) {
/*  81 */         brushConfig.setErrorFlag("LoadLoop: Tool arg '" + this.argNameArg + "' not found");
/*     */         
/*     */         return;
/*     */       } 
/*  85 */       Object argValue = toolArgs.get(this.argNameArg);
/*     */       
/*  87 */       if (argValue instanceof Integer) { Integer intValue = (Integer)argValue;
/*  88 */         if (intValue.intValue() > 100 || intValue.intValue() < 0) {
/*  89 */           brushConfig.setErrorFlag("Cannot have more than 100 repetitions, or negative repetitions");
/*     */           return;
/*     */         } 
/*  92 */         this.repetitionsRemaining = intValue.intValue(); }
/*     */       else
/*     */       
/*  95 */       { brushConfig.setErrorFlag("LoadLoop: Tool arg '" + this.argNameArg + "' is not an Int type (found " + argValue.getClass().getSimpleName() + ")");
/*     */         
/*     */         return; }
/*     */     
/*     */     } 
/*     */     
/* 101 */     if (this.repetitionsRemaining == 0) {
/* 102 */       this.repetitionsRemaining = -1;
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     this.repetitionsRemaining--;
/* 107 */     brushConfigCommandExecutor.loadOperatingIndex(this.indexNameArg, false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\flowcontrol\loops\LoadLoopFromToolArgOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */