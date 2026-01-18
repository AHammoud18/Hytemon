/*    */ package com.hypixel.hytale.server.spawning.suppression;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnSuppressorEntry
/*    */ {
/*    */   public static final BuilderCodec<SpawnSuppressorEntry> CODEC;
/*    */   private String suppressionId;
/*    */   private Vector3d position;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnSuppressorEntry.class, SpawnSuppressorEntry::new).append(new KeyedCodec("Position", (Codec)Vector3d.CODEC), (entry, v) -> entry.position = v, entry -> entry.position).add()).append(new KeyedCodec("Suppression", (Codec)Codec.STRING), (entry, s) -> entry.suppressionId = s, entry -> entry.suppressionId).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnSuppressorEntry(String suppressionId, Vector3d position) {
/* 28 */     this.suppressionId = suppressionId;
/* 29 */     this.position = position;
/*    */   }
/*    */ 
/*    */   
/*    */   private SpawnSuppressorEntry() {}
/*    */   
/*    */   public Vector3d getPosition() {
/* 36 */     return this.position;
/*    */   }
/*    */   
/*    */   public String getSuppressionId() {
/* 40 */     return this.suppressionId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\SpawnSuppressorEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */