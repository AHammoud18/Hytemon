/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.completion;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ObjectiveCompletionAsset
/*    */ {
/* 13 */   public static final CodecMapCodec<ObjectiveCompletionAsset> CODEC = new CodecMapCodec("Type");
/*    */   
/* 15 */   public static final BuilderCodec<ObjectiveCompletionAsset> BASE_CODEC = BuilderCodec.abstractBuilder(ObjectiveCompletionAsset.class)
/* 16 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 24 */     return "ObjectiveCompletionAsset{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\completion\ObjectiveCompletionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */