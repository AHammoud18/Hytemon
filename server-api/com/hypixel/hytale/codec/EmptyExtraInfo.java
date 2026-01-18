/*    */ package com.hypixel.hytale.codec;
/*    */ 
/*    */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ @Deprecated
/*    */ public class EmptyExtraInfo
/*    */   extends ExtraInfo
/*    */ {
/* 13 */   public static final EmptyExtraInfo EMPTY = new EmptyExtraInfo();
/*    */   
/*    */   private EmptyExtraInfo() {
/* 16 */     super(2147483647, com.hypixel.hytale.codec.validation.ThrowingValidationResults::new);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushKey(String key) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushIntKey(int i) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushKey(String key, RawJsonReader reader) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushIntKey(int key, RawJsonReader reader) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void popKey() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void addUnknownKey(@Nonnull String key) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void ignoreUnusedKey(String key) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void popIgnoredUnusedKey() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String peekKey() {
/* 54 */     return "<empty>";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String peekKey(char separator) {
/* 60 */     return "<empty>";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<String> getUnknownKeys() {
/* 66 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendDetailsTo(@Nonnull StringBuilder sb) {
/* 71 */     sb.append("EmptyExtraInfo\n");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 77 */     return "EmptyExtraInfo{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\EmptyExtraInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */