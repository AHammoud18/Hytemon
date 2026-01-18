/*    */ package com.hypixel.hytale.server.worldgen.chunk;
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
/*    */ public interface BlockPriorityModifier
/*    */ {
/* 14 */   public static final BlockPriorityModifier NONE = new BlockPriorityModifier()
/*    */     {
/*    */       public byte modifyCurrent(byte current, byte target) {
/* 17 */         return current;
/*    */       }
/*    */ 
/*    */       
/*    */       public byte modifyTarget(byte original, byte target) {
/* 22 */         return target;
/*    */       }
/*    */     };
/*    */   
/*    */   byte modifyCurrent(byte paramByte1, byte paramByte2);
/*    */   
/*    */   byte modifyTarget(byte paramByte1, byte paramByte2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\BlockPriorityModifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */