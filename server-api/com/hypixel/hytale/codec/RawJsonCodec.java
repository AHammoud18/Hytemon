/*    */ package com.hypixel.hytale.codec;
/*    */ 
/*    */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface RawJsonCodec<T>
/*    */ {
/*    */   @Nullable
/*    */   @Deprecated
/*    */   default T decodeJson(RawJsonReader reader) throws IOException {
/* 12 */     return decodeJson(reader, EmptyExtraInfo.EMPTY);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   T decodeJson(RawJsonReader paramRawJsonReader, ExtraInfo paramExtraInfo) throws IOException;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\RawJsonCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */