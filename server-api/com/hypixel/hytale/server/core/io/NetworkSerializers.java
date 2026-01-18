/*   */ package com.hypixel.hytale.server.core.io;
/*   */ import com.hypixel.hytale.math.shape.Box;
/*   */ import com.hypixel.hytale.protocol.Hitbox;
/*   */ 
/*   */ public interface NetworkSerializers {
/*   */   static {
/* 7 */     BOX = (t -> {
/*   */         Hitbox packet = new Hitbox();
/*   */         packet.minX = (float)t.getMin().getX();
/*   */         packet.minY = (float)t.getMin().getY();
/*   */         packet.minZ = (float)t.getMin().getZ();
/*   */         packet.maxX = (float)t.getMax().getX();
/*   */         packet.maxY = (float)t.getMax().getY();
/*   */         packet.maxZ = (float)t.getMax().getZ();
/*   */         return packet;
/*   */       });
/*   */   }
/*   */   
/*   */   public static final NetworkSerializer<Box, Hitbox> BOX;
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\NetworkSerializers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */