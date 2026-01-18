/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderCodecObjectHelper<T>
/*    */ {
/*    */   protected final Codec<T> codec;
/*    */   protected final Class<?> classType;
/*    */   protected final Validator<T> validator;
/*    */   @Nullable
/*    */   protected T value;
/*    */   
/*    */   public BuilderCodecObjectHelper(Class<?> classType, Codec<T> codec, Validator<T> validator) {
/* 26 */     this.classType = classType;
/* 27 */     this.codec = codec;
/* 28 */     this.validator = validator;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T build() {
/* 33 */     return this.value;
/*    */   }
/*    */   
/*    */   public void readConfig(@Nonnull JsonElement data, @Nonnull ExtraInfo extraInfo) {
/* 37 */     this.value = (T)this.codec.decode(BsonUtil.translateJsonToBson(data), extraInfo);
/*    */     
/* 39 */     if (this.validator != null) {
/* 40 */       this.validator.accept(this.value, extraInfo.getValidationResults());
/*    */     }
/*    */     
/* 43 */     extraInfo.getValidationResults()._processValidationResults();
/* 44 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(HytaleLogger.getLogger());
/*    */   }
/*    */   
/*    */   public boolean hasValue() {
/* 48 */     return (this.value != null);
/*    */   }
/*    */   
/*    */   public Class<?> getClassType() {
/* 52 */     return this.classType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderCodecObjectHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */