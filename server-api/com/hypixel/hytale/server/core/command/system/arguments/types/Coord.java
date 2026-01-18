/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Coord
/*    */ {
/*    */   private final double value;
/*    */   private final boolean height;
/*    */   private final boolean relative;
/*    */   private final boolean chunk;
/*    */   
/*    */   public Coord(double value, boolean height, boolean relative, boolean chunk) {
/* 24 */     this.value = value;
/* 25 */     this.height = height;
/* 26 */     this.relative = relative;
/* 27 */     this.chunk = chunk;
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 31 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean isNotBase() {
/* 35 */     return (!this.height && !this.relative && !this.chunk);
/*    */   }
/*    */   
/*    */   public boolean isHeight() {
/* 39 */     return this.height;
/*    */   }
/*    */   
/*    */   public boolean isRelative() {
/* 43 */     return this.relative;
/*    */   }
/*    */   
/*    */   public boolean isChunk() {
/* 47 */     return this.chunk;
/*    */   }
/*    */   
/*    */   public double resolveXZ(double base) {
/* 51 */     return resolve(base);
/*    */   }
/*    */   
/*    */   public double resolveYAtWorldCoords(double base, @Nonnull World world, double x, double z) throws GeneralCommandException {
/* 55 */     if (this.height) {
/* 56 */       WorldChunk worldCoords = world.getNonTickingChunk(ChunkUtil.indexChunkFromBlock(x, z));
/* 57 */       if (worldCoords == null) throw new GeneralCommandException(Message.raw("Failed to load chunk at (" + x + ", " + z + ")")); 
/* 58 */       return (worldCoords.getHeight(MathUtil.floor(x), MathUtil.floor(z)) + 1) + resolve(0.0D);
/*    */     } 
/* 60 */     return resolve(base);
/*    */   }
/*    */   
/*    */   protected double resolve(double base) {
/* 64 */     double val = this.chunk ? (this.value * 32.0D) : this.value;
/* 65 */     return this.relative ? (val + base) : val;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Coord parse(@Nonnull String str) {
/* 70 */     boolean height = false;
/* 71 */     boolean relative = false;
/* 72 */     boolean chunk = false;
/*    */     
/* 74 */     int index = 0;
/*    */     while (true)
/*    */     { String rest;
/* 77 */       switch (str.charAt(index))
/*    */       { case '_':
/* 79 */           index++;
/* 80 */           height = true;
/* 81 */           if (str.length() == index) return new Coord(0.0D, true, relative, chunk); 
/*    */           continue;
/*    */         case '~':
/* 84 */           index++;
/* 85 */           relative = true;
/* 86 */           if (str.length() == index) return new Coord(0.0D, height, true, chunk); 
/*    */           continue;
/*    */         case 'c':
/* 89 */           index++;
/* 90 */           chunk = true;
/* 91 */           if (str.length() == index) return new Coord(0.0D, height, relative, true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 97 */           rest = str.substring(index);
/* 98 */           return new Coord(Double.parseDouble(rest), height, relative, chunk); }  break; }  String str1 = str.substring(index); return new Coord(Double.parseDouble(str1), height, relative, chunk);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\Coord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */