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
/*    */ public class CodecException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final String message;
/*    */   
/*    */   public CodecException(String message) {
/* 18 */     super(message);
/* 19 */     this.message = message;
/*    */   }
/*    */   
/*    */   public CodecException(String message, Throwable cause) {
/* 23 */     super(message, cause);
/* 24 */     this.message = message;
/*    */   }
/*    */   
/*    */   public CodecException(String message, BsonValue bsonValue, @Nonnull ExtraInfo extraInfo, Throwable cause) {
/* 28 */     super(message + " '" + message + "' " + extraInfo.peekKey(), cause);
/* 29 */     this.message = message;
/*    */   }
/*    */   
/*    */   public CodecException(String message, RawJsonReader reader, @Nonnull ExtraInfo extraInfo, Throwable cause) {
/* 33 */     super(message + " '" + message + "' " + extraInfo.peekKey(), cause);
/* 34 */     this.message = message;
/*    */   }
/*    */   
/*    */   public CodecException(String message, Object obj, @Nonnull ExtraInfo extraInfo, Throwable cause) {
/* 38 */     super(message + " '" + message + "' " + extraInfo.peekKey(), cause);
/* 39 */     this.message = message;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 44 */     return this.message;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\exception\CodecException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */