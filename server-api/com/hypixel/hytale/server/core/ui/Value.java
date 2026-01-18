/*    */ package com.hypixel.hytale.server.core.ui;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Value<T> {
/*    */   private T value;
/*    */   private String documentPath;
/*    */   private String valueName;
/*    */   
/*    */   private Value(String documentPath, String valueName) {
/* 11 */     this.documentPath = documentPath;
/* 12 */     this.valueName = valueName;
/*    */   }
/*    */   
/*    */   private Value(T value) {
/* 16 */     this.value = value;
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 20 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getDocumentPath() {
/* 24 */     return this.documentPath;
/*    */   }
/*    */   
/*    */   public String getValueName() {
/* 28 */     return this.valueName;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T> Value<T> ref(String document, String value) {
/* 33 */     return new Value<>(document, value);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T> Value<T> of(T value) {
/* 38 */     return new Value<>(value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\Value.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */