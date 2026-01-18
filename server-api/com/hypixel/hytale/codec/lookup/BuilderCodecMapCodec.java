/*    */ package com.hypixel.hytale.codec.lookup;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ 
/*    */ public class BuilderCodecMapCodec<T>
/*    */   extends StringCodecMapCodec<T, BuilderCodec<? extends T>> {
/*    */   public BuilderCodecMapCodec() {}
/*    */   
/*    */   public BuilderCodecMapCodec(boolean allowDefault) {
/* 10 */     super(allowDefault);
/*    */   }
/*    */   
/*    */   public BuilderCodecMapCodec(String id) {
/* 14 */     super(id);
/*    */   }
/*    */   
/*    */   public BuilderCodecMapCodec(String key, boolean allowDefault) {
/* 18 */     super(key, allowDefault);
/*    */   }
/*    */   
/*    */   public T getDefault() {
/* 22 */     return (T)getDefaultCodec().getDefaultValue();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\lookup\BuilderCodecMapCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */