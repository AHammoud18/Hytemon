/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DialogOptions
/*     */ {
/*     */   @Nonnull
/*     */   public static BuilderCodec<DialogOptions> CODEC;
/*     */   protected String entityNameKey;
/*     */   protected String dialogKey;
/*     */   
/*     */   static {
/*  98 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DialogOptions.class, DialogOptions::new).append(new KeyedCodec("EntityNameKey", (Codec)Codec.STRING), (dialogOptions, s) -> dialogOptions.entityNameKey = s, dialogOptions -> dialogOptions.entityNameKey).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("DialogKey", (Codec)Codec.STRING), (dialogOptions, s) -> dialogOptions.dialogKey = s, dialogOptions -> dialogOptions.dialogKey).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DialogOptions(String entityNameKey, String dialogKey) {
/* 104 */     this.entityNameKey = entityNameKey;
/* 105 */     this.dialogKey = dialogKey;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DialogOptions() {}
/*     */   
/*     */   public String getEntityNameKey() {
/* 112 */     return this.entityNameKey;
/*     */   }
/*     */   
/*     */   public String getDialogKey() {
/* 116 */     return this.dialogKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 121 */     if (this == o) return true; 
/* 122 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 124 */     DialogOptions that = (DialogOptions)o;
/*     */     
/* 126 */     if (!this.entityNameKey.equals(that.entityNameKey)) return false; 
/* 127 */     return this.dialogKey.equals(that.dialogKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 132 */     int result = this.entityNameKey.hashCode();
/* 133 */     result = 31 * result + this.dialogKey.hashCode();
/* 134 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 140 */     return "DialogOptions{entityNameKey='" + this.entityNameKey + "', dialogKey='" + this.dialogKey + "'}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\UseEntityObjectiveTaskAsset$DialogOptions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */