/*    */ package com.hypixel.hytale.server.worldgen.loader.util;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorUtil
/*    */ {
/*    */   public static int hexString(@Nonnull String s) {
/* 12 */     return Integer.parseInt(s.replace("#", "").replace("0x", ""), 16) & 0xFFFFFF;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loade\\util\ColorUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */