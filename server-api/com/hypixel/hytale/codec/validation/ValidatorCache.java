/*    */ package com.hypixel.hytale.codec.validation;
/*    */ 
/*    */ import com.hypixel.hytale.codec.validation.validator.ArrayValidator;
/*    */ import com.hypixel.hytale.codec.validation.validator.MapKeyValidator;
/*    */ import com.hypixel.hytale.codec.validation.validator.MapValueValidator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ValidatorCache<T>
/*    */ {
/*    */   private final Validator<T> validator;
/*    */   private ArrayValidator<T> arrayValidator;
/*    */   private ArrayValidator<T[]> arrayofArrayValidator;
/*    */   private MapKeyValidator<T> mapKeyValidator;
/*    */   private MapKeyValidator<T[]> mapArrayKeyValidator;
/*    */   private MapValueValidator<T> mapValueValidator;
/*    */   private MapValueValidator<T[]> mapArrayValueValidator;
/*    */   
/*    */   public ValidatorCache(Validator<T> validator) {
/* 19 */     this.validator = validator;
/*    */   }
/*    */   
/*    */   public Validator<T> getValidator() {
/* 23 */     return this.validator;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ArrayValidator<T> getArrayValidator() {
/* 28 */     if (this.arrayValidator == null) this.arrayValidator = new ArrayValidator(getValidator()); 
/* 29 */     return this.arrayValidator;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ArrayValidator<T[]> getArrayOfArrayValidator() {
/* 34 */     if (this.arrayofArrayValidator == null) this.arrayofArrayValidator = new ArrayValidator((Validator)getArrayValidator()); 
/* 35 */     return this.arrayofArrayValidator;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public MapKeyValidator<T> getMapKeyValidator() {
/* 40 */     if (this.mapKeyValidator == null) this.mapKeyValidator = new MapKeyValidator(getValidator()); 
/* 41 */     return this.mapKeyValidator;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public MapKeyValidator<T[]> getMapArrayKeyValidator() {
/* 46 */     if (this.mapArrayKeyValidator == null) this.mapArrayKeyValidator = new MapKeyValidator((Validator)getArrayValidator()); 
/* 47 */     return this.mapArrayKeyValidator;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public MapValueValidator<T> getMapValueValidator() {
/* 52 */     if (this.mapValueValidator == null) this.mapValueValidator = new MapValueValidator(getValidator()); 
/* 53 */     return this.mapValueValidator;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public MapValueValidator<T[]> getMapArrayValueValidator() {
/* 58 */     if (this.mapArrayValueValidator == null) this.mapArrayValueValidator = new MapValueValidator((Validator)getArrayValidator()); 
/* 59 */     return this.mapArrayValueValidator;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\validation\ValidatorCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */