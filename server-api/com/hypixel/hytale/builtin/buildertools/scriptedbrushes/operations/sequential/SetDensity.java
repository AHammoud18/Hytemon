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
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetDensity
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<SetDensity> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SetDensity.class, SetDensity::new).append(new KeyedCodec("Density", (Codec)Codec.INTEGER), (op, val) -> op.density = val, op -> op.density).documentation("Changes the likelyhood that a given block will be processed").add()).documentation("Sets the random chance that any given block being set will actually get set, otherwise getting cancelled. Ex: a value of 30 is a 30% chance blocks will appear with a set operation.")).build();
/*    */   }
/* 29 */   public Integer density = Integer.valueOf(100);
/*    */   
/*    */   public SetDensity() {
/* 32 */     super("Density", "Sets the random chance that any given block being set will actually get set, otherwise getting cancelled. Ex: a value of 30 is a 30% chance blocks will appear with a set operation.", false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 38 */     brushConfig.setDensity(MathUtil.clamp(this.density.intValue(), 1, 100));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\SetDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */