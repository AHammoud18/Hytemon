/*    */ package com.hypixel.hytale.codec.validation;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.WrappedCodec;
/*    */ import java.util.Set;
/*    */ 
/*    */ public interface ValidatableCodec<T>
/*    */   extends Codec<T> {
/*    */   void validate(T paramT, ExtraInfo paramExtraInfo);
/*    */   
/*    */   void validateDefaults(ExtraInfo paramExtraInfo, Set<Codec<?>> paramSet);
/*    */   
/*    */   static void validateDefaults(Codec<?> codec, ExtraInfo extraInfo, Set<Codec<?>> tested) {
/*    */     do {
/* 16 */       if (codec instanceof WrappedCodec) { WrappedCodec<?> wrappedCodec = (WrappedCodec)codec;
/* 17 */         codec = wrappedCodec.getChildCodec(); }
/* 18 */       else { if (codec instanceof ValidatableCodec) { ValidatableCodec<?> validatableCodec = (ValidatableCodec)codec;
/* 19 */           validatableCodec.validateDefaults(extraInfo, tested); }
/*    */ 
/*    */         
/*    */         break; }
/*    */     
/* 24 */     } while (codec != null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\validation\ValidatableCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */