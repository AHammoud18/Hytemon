/*    */ package com.hypixel.hytale.server.worldgen.cave;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.flag.ConstantInt2Flags;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.flag.Int2FlagsCondition;
/*    */ 
/*    */ public class CaveBiomeMaskFlags {
/*  7 */   public static final Int2FlagsCondition DEFAULT_ALLOW = (Int2FlagsCondition)new ConstantInt2Flags(7);
/*  8 */   public static final Int2FlagsCondition DEFAULT_DENY = (Int2FlagsCondition)new ConstantInt2Flags(0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int GENERATE = 1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int POPULATE = 2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int CONTINUE = 4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean canGenerate(int value) {
/* 33 */     return test(value, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean canPopulate(int value) {
/* 40 */     return test(value, 2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean canContinue(int value) {
/* 47 */     return test(value, 4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean test(int value, int flag) {
/* 58 */     return ((value & flag) == flag);
/*    */   }
/*    */   
/*    */   public static class Defaults {
/*    */     public static final int DEFAULT_RESULT = 4;
/*    */     public static final int DISALLOW_ALL = 0;
/*    */     public static final int ALLOW_ALL = 7;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\CaveBiomeMaskFlags.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */