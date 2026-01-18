/*    */ package com.hypixel.hytale.server.core.codec;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class LayerEntryCodec
/*    */ {
/*    */   public static final BuilderCodec<LayerEntryCodec> CODEC;
/*    */   private Integer depth;
/*    */   private String material;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LayerEntryCodec.class, LayerEntryCodec::new).append(new KeyedCodec("Left", (Codec)Codec.INTEGER), (entry, depth) -> entry.depth = depth, entry -> entry.depth).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Right", (Codec)Codec.STRING), (entry, material) -> entry.material = material, entry -> entry.material).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("UseToolArg", (Codec)Codec.BOOLEAN), (entry, useToolArg) -> entry.useToolArg = (useToolArg != null && useToolArg.booleanValue()), entry -> Boolean.valueOf(entry.useToolArg)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean useToolArg = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public LayerEntryCodec(Integer depth, String material, boolean useToolArg) {
/* 46 */     this.depth = depth;
/* 47 */     this.material = material;
/* 48 */     this.useToolArg = useToolArg;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Integer getDepth() {
/* 53 */     return this.depth;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getMaterial() {
/* 58 */     return this.material;
/*    */   }
/*    */   
/*    */   public boolean isUseToolArg() {
/* 62 */     return this.useToolArg;
/*    */   }
/*    */   
/*    */   public LayerEntryCodec() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\codec\LayerEntryCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */