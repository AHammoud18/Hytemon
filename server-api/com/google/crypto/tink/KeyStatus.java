/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public final class KeyStatus
/*    */ {
/* 29 */   public static final KeyStatus ENABLED = new KeyStatus("ENABLED");
/* 30 */   public static final KeyStatus DISABLED = new KeyStatus("DISABLED");
/* 31 */   public static final KeyStatus DESTROYED = new KeyStatus("DESTROYED");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   private KeyStatus(String name) {
/* 36 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     return this.name;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KeyStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */