/*    */ package com.hypixel.hytale.server.core.asset.type.item.config.metadata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdventureMetadata
/*    */ {
/*    */   public static final String KEY = "Adventure";
/*    */   public static final BuilderCodec<AdventureMetadata> CODEC;
/*    */   
/*    */   static {
/* 18 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AdventureMetadata.class, AdventureMetadata::new).appendInherited(new KeyedCodec("Cursed", (Codec)Codec.BOOLEAN), (meta, s) -> meta.cursed = s.booleanValue(), meta -> meta.cursed ? Boolean.TRUE : null, (meta, parent) -> meta.cursed = parent.cursed).add()).build();
/* 19 */   } public static final KeyedCodec<AdventureMetadata> KEYED_CODEC = new KeyedCodec("Adventure", (Codec)CODEC);
/*    */   
/*    */   private boolean cursed;
/*    */   
/*    */   public boolean isCursed() {
/* 24 */     return this.cursed;
/*    */   }
/*    */   
/*    */   public void setCursed(boolean cursed) {
/* 28 */     this.cursed = cursed;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\metadata\AdventureMetadata.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */