/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.SwitchStateDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SwitchStateDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<SwitchStateDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SwitchStateDensityAsset.class, SwitchStateDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("SwitchState", (Codec)Codec.STRING, true), (t, k) -> t.switchState = k, t -> t.switchState).add()).build();
/*    */   }
/* 23 */   private String switchState = "";
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 27 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 29 */     int stateHash = Objects.hash(new Object[] { this.switchState });
/* 30 */     return (Density)new SwitchStateDensity(buildFirstInput(argument), stateHash);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 35 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SwitchStateDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */