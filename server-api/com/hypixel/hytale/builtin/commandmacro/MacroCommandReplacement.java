/*    */ package com.hypixel.hytale.builtin.commandmacro;
/*    */ 
/*    */ import javax.annotation.Nullable;
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
/*    */ public class MacroCommandReplacement
/*    */ {
/*    */   private final String nameOfReplacingArg;
/*    */   @Nullable
/*    */   private final String optionalArgumentKey;
/*    */   private final String stringToReplaceWithValue;
/*    */   
/*    */   public MacroCommandReplacement(String nameOfReplacingArg, String stringToReplaceWithValue, @Nullable String optionalArgumentKey) {
/* 27 */     this.nameOfReplacingArg = nameOfReplacingArg;
/* 28 */     this.stringToReplaceWithValue = stringToReplaceWithValue;
/* 29 */     this.optionalArgumentKey = (optionalArgumentKey == null) ? null : ("--" + optionalArgumentKey + (optionalArgumentKey.endsWith("=") ? "" : " "));
/*    */   }
/*    */   
/*    */   public MacroCommandReplacement(String replacementKey, String stringToReplaceWithValue) {
/* 33 */     this(replacementKey, stringToReplaceWithValue, null);
/*    */   }
/*    */   
/*    */   public String getNameOfReplacingArg() {
/* 37 */     return this.nameOfReplacingArg;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getOptionalArgumentKey() {
/* 42 */     return this.optionalArgumentKey;
/*    */   }
/*    */   
/*    */   public String getStringToReplaceWithValue() {
/* 46 */     return this.stringToReplaceWithValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\commandmacro\MacroCommandReplacement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */