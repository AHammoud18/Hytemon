/*    */ package org.bson.codecs;
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
/*    */ final class UuidCodecHelper
/*    */ {
/*    */   public static void reverseByteArray(byte[] data, int start, int length) {
/* 22 */     for (int left = start, right = start + length - 1; left < right; left++, right--) {
/*    */       
/* 24 */       byte temp = data[left];
/* 25 */       data[left] = data[right];
/* 26 */       data[right] = temp;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\UuidCodecHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */