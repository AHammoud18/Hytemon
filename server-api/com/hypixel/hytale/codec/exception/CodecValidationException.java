/*    */ package com.hypixel.hytale.codec.exception;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CodecValidationException
/*    */   extends CodecException
/*    */ {
/*    */   public CodecValidationException(String message) {
/* 16 */     super(message);
/*    */   }
/*    */   
/*    */   public CodecValidationException(String message, Throwable cause) {
/* 20 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public CodecValidationException(String message, BsonValue bsonValue, @Nonnull ExtraInfo extraInfo, Throwable cause) {
/* 24 */     super(message, bsonValue, extraInfo, cause);
/*    */   }
/*    */   
/*    */   public CodecValidationException(String message, RawJsonReader reader, @Nonnull ExtraInfo extraInfo, Throwable cause) {
/* 28 */     super(message, reader, extraInfo, cause);
/*    */   }
/*    */   
/*    */   public CodecValidationException(String message, Object obj, @Nonnull ExtraInfo extraInfo, Throwable cause) {
/* 32 */     super(message, obj, extraInfo, cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\exception\CodecValidationException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */