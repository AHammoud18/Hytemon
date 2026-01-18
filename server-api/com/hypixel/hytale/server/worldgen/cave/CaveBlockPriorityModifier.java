/*    */ package com.hypixel.hytale.server.worldgen.cave;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.chunk.BlockPriorityModifier;
/*    */ 
/*    */ public class CaveBlockPriorityModifier
/*    */   implements BlockPriorityModifier
/*    */ {
/*  8 */   public static final BlockPriorityModifier INSTANCE = new CaveBlockPriorityModifier();
/*    */ 
/*    */ 
/*    */   
/*    */   public byte modifyCurrent(byte current, byte target) {
/* 13 */     if (current == 8 && target == 6) return 6;
/*    */ 
/*    */     
/* 16 */     if (current == 6 && target == 5) return 5;
/*    */     
/* 18 */     return current;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte modifyTarget(byte current, byte target) {
/* 24 */     if (current == 8 && target == 6) return 8;
/*    */     
/* 26 */     return target;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\CaveBlockPriorityModifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */