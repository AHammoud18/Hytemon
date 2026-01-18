/*    */ package com.hypixel.hytale.math.hitdetection;
/*    */ 
/*    */ import com.hypixel.hytale.math.shape.Quad4d;
/*    */ import com.hypixel.hytale.math.shape.Triangle4d;
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.math.vector.Vector4d;
/*    */ import java.util.Random;
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
/*    */ 
/*    */ public class HitDetectionBuffer
/*    */ {
/* 33 */   public Random random = (Random)new FastRandom();
/* 34 */   public Vector4d hitPosition = new Vector4d();
/* 35 */   public Vector4d tempHitPosition = new Vector4d();
/* 36 */   public Vector4d transformedPoint = new Vector4d();
/* 37 */   public Quad4d transformedQuad = new Quad4d();
/* 38 */   public Triangle4d visibleTriangle = new Triangle4d();
/* 39 */   public Vector4dBufferList vertexList1 = new Vector4dBufferList(16);
/* 40 */   public Vector4dBufferList vertexList2 = new Vector4dBufferList(16);
/*    */   public boolean containsFully = false;
/*    */   private static final int VECTOR_BUFFER_SIZE = 16;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\hitdetection\HitDetectionBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */