/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollisionModuleConfig
/*    */ {
/*    */   public static final BuilderCodec<CollisionModuleConfig> CODEC;
/*    */   public static final double MOVEMENT_THRESHOLD = 1.0E-5D;
/*    */   public static final double MOVEMENT_THRESHOLD_SQUARED = 1.0000000000000002E-10D;
/*    */   public static final double EXTENT = 1.0E-5D;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CollisionModuleConfig.class, CollisionModuleConfig::new).addField(new KeyedCodec("ExtentMax", (Codec)Codec.DOUBLE), (config, d) -> config.extentMax = d.doubleValue(), config -> Double.valueOf(config.extentMax))).addField(new KeyedCodec("DumpInvalidBlocks", (Codec)Codec.BOOLEAN), (config, b) -> config.dumpInvalidBlocks = b.booleanValue(), config -> Boolean.valueOf(config.dumpInvalidBlocks))).addField(new KeyedCodec("MinimumThickness", (Codec)Codec.DOUBLE), (config, d) -> config.minimumThickness = d, config -> config.minimumThickness)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 31 */   private double extentMax = 0.0D;
/*    */   @Nullable
/* 33 */   private Double minimumThickness = null;
/*    */   private boolean dumpInvalidBlocks = false;
/*    */   
/*    */   public double getExtentMax() {
/* 37 */     return this.extentMax;
/*    */   }
/*    */   
/*    */   public void setExtentMax(double extentMax) {
/* 41 */     this.extentMax = extentMax;
/*    */   }
/*    */   
/*    */   public boolean isDumpInvalidBlocks() {
/* 45 */     return this.dumpInvalidBlocks;
/*    */   }
/*    */   
/*    */   public void setDumpInvalidBlocks(boolean dumpInvalidBlocks) {
/* 49 */     this.dumpInvalidBlocks = dumpInvalidBlocks;
/*    */   }
/*    */   
/*    */   public double getMinimumThickness() {
/* 53 */     return this.minimumThickness.doubleValue();
/*    */   }
/*    */   
/*    */   public void setMinimumThickness(double minimumThickness) {
/* 57 */     this.minimumThickness = Double.valueOf(minimumThickness);
/*    */   }
/*    */   
/*    */   public boolean hasMinimumThickness() {
/* 61 */     return (this.minimumThickness != null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionModuleConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */