/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.TemporalArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import java.time.Duration;
/*    */ import java.time.Period;
/*    */ import java.time.format.DateTimeParseException;
/*    */ import java.time.temporal.TemporalAmount;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TemporalArrayHolder
/*    */   extends StringArrayHolder
/*    */ {
/*    */   protected TemporalArrayValidator validator;
/*    */   private TemporalAmount[] cachedTemporalArray;
/*    */   
/*    */   public static TemporalAmount[] convertStringToTemporalArray(@Nullable String[] source) {
/* 26 */     if (source == null) return null; 
/* 27 */     int length = source.length;
/* 28 */     TemporalAmount[] result = new TemporalAmount[length];
/* 29 */     for (int i = 0; i < length; i++) {
/* 30 */       String text = source[i];
/* 31 */       String period = (!source[i].isEmpty() && source[i].charAt(0) == 'P') ? text : ("P" + text);
/*    */       try {
/* 33 */         result[i] = Period.parse(period);
/* 34 */       } catch (DateTimeParseException e) {
/*    */         try {
/* 36 */           result[i] = Duration.parse(period);
/* 37 */         } catch (DateTimeParseException e2) {
/* 38 */           throw new IllegalStateException(String.format("Cannot parse text %s to Duration or Period", new Object[] { source[i] }));
/*    */         } 
/*    */       } 
/*    */     } 
/* 42 */     return result;
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, int minLength, int maxLength, TemporalArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 46 */     readJSON(requiredJsonElement, minLength, maxLength, name, builderParameters);
/* 47 */     this.validator = validator;
/* 48 */     if (isStatic()) {
/* 49 */       String[] array = this.expression.getStringArray(null);
/* 50 */       this.cachedTemporalArray = convertStringToTemporalArray(array);
/* 51 */       validate(this.cachedTemporalArray);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public TemporalAmount[] getTemporalArray(ExecutionContext executionContext) {
/* 57 */     TemporalAmount[] value = rawGetTemporalArray(executionContext);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     return value;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public TemporalAmount[] rawGetTemporalArray(ExecutionContext executionContext) {
/* 67 */     if (isStatic()) return this.cachedTemporalArray;
/*    */     
/* 69 */     String[] array = this.expression.getStringArray(executionContext);
/* 70 */     TemporalAmount[] value = convertStringToTemporalArray(array);
/* 71 */     validate(value);
/* 72 */     return value;
/*    */   }
/*    */   
/*    */   public void validate(@Nullable TemporalAmount[] value) {
/* 76 */     if (value != null) validateLength(value.length); 
/* 77 */     if (this.validator != null && !this.validator.test(value))
/* 78 */       throw new IllegalStateException(this.validator.errorMessage(this.name, value)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\TemporalArrayHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */