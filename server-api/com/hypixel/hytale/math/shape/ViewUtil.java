/*    */ package com.hypixel.hytale.math.shape;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import java.awt.Graphics2D;
/*    */ 
/*    */ 
/*    */ public class ViewUtil
/*    */ {
/*    */   public static final int INSIDE = 0;
/*    */   public static final int LEFT = 1;
/*    */   
/*    */   private ViewUtil() {
/* 13 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public static final int RIGHT = 2;
/*    */   
/*    */   public static final int BOTTOM = 4;
/*    */   public static final int TOP = 8;
/*    */   
/*    */   private static int computeOutCode(double x, double y) {
/* 23 */     int code = 0;
/*    */     
/* 25 */     if (x < -1.0D) {
/* 26 */       code |= 0x1;
/* 27 */     } else if (x > 1.0D) {
/* 28 */       code |= 0x2;
/*    */     } 
/* 30 */     if (y < -1.0D) {
/* 31 */       code |= 0x4;
/* 32 */     } else if (y > 1.0D) {
/* 33 */       code |= 0x8;
/*    */     } 
/*    */     
/* 36 */     return code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void CohenSutherlandLineClipAndDraw(double x0, double y0, double x1, double y1, Graphics2D graphics2D, double width, double height) {
/* 44 */     int outcode0 = computeOutCode(x0, y0);
/* 45 */     int outcode1 = computeOutCode(x1, y1);
/* 46 */     boolean accept = false;
/*    */     while (true) {
/*    */       double x, y;
/* 49 */       if ((outcode0 | outcode1) == 0) {
/* 50 */         accept = true; break;
/*    */       } 
/* 52 */       if ((outcode0 & outcode1) != 0) {
/*    */         break;
/*    */       }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 60 */       int outcodeOut = (outcode0 != 0) ? outcode0 : outcode1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 67 */       if ((outcodeOut & 0x8) != 0) {
/* 68 */         x = x0 + (x1 - x0) * (1.0D - y0) / (y1 - y0);
/* 69 */         y = 1.0D;
/* 70 */       } else if ((outcodeOut & 0x4) != 0) {
/* 71 */         x = x0 + (x1 - x0) * (-1.0D - y0) / (y1 - y0);
/* 72 */         y = -1.0D;
/* 73 */       } else if ((outcodeOut & 0x2) != 0) {
/* 74 */         y = y0 + (y1 - y0) * (1.0D - x0) / (x1 - x0);
/* 75 */         x = 1.0D;
/* 76 */       } else if ((outcodeOut & 0x1) != 0) {
/* 77 */         y = y0 + (y1 - y0) * (-1.0D - x0) / (x1 - x0);
/* 78 */         x = -1.0D;
/*    */       } else {
/* 80 */         x = 0.0D;
/* 81 */         y = 0.0D;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 86 */       if (outcodeOut == outcode0) {
/* 87 */         x0 = x;
/* 88 */         y0 = y;
/* 89 */         outcode0 = computeOutCode(x0, y0); continue;
/*    */       } 
/* 91 */       x1 = x;
/* 92 */       y1 = y;
/* 93 */       outcode1 = computeOutCode(x1, y1);
/*    */     } 
/*    */ 
/*    */     
/* 97 */     if (accept) {
/* 98 */       Vector2d start = new Vector2d(x0, y0);
/* 99 */       Vector2d vector2d1 = new Vector2d(x1, y1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\ViewUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */