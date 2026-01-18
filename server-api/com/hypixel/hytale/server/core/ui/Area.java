/*    */ package com.hypixel.hytale.server.core.ui;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class Area
/*    */ {
/*    */   public static final BuilderCodec<Area> CODEC;
/*    */   private int x;
/*    */   private int y;
/*    */   private int width;
/*    */   private int height;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Area.class, Area::new).addField(new KeyedCodec("X", (Codec)Codec.INTEGER), (p, t) -> p.x = t.intValue(), p -> Integer.valueOf(p.x))).addField(new KeyedCodec("Y", (Codec)Codec.INTEGER), (p, t) -> p.y = t.intValue(), p -> Integer.valueOf(p.y))).addField(new KeyedCodec("Width", (Codec)Codec.INTEGER), (p, t) -> p.width = t.intValue(), p -> Integer.valueOf(p.width))).addField(new KeyedCodec("Height", (Codec)Codec.INTEGER), (p, t) -> p.height = t.intValue(), p -> Integer.valueOf(p.height))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Area setX(int x) {
/* 40 */     this.x = x;
/* 41 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Area setY(int y) {
/* 46 */     this.y = y;
/* 47 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Area setWidth(int width) {
/* 52 */     this.width = width;
/* 53 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Area setHeight(int height) {
/* 58 */     this.height = height;
/* 59 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\Area.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */