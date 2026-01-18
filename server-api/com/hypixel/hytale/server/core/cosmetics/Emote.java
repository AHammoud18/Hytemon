/*    */ package com.hypixel.hytale.server.core.cosmetics;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ public class Emote
/*    */ {
/*    */   protected String id;
/*    */   protected String name;
/*    */   protected String animation;
/*    */   
/*    */   protected Emote(@Nonnull BsonDocument bson) {
/* 13 */     this.id = bson.getString("Id").getValue();
/* 14 */     this.name = bson.getString("Name").getValue();
/* 15 */     this.animation = bson.getString("Animation").getValue();
/*    */   }
/*    */   
/*    */   public String getId() {
/* 19 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getAnimation() {
/* 27 */     return this.animation;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "Emote{id='" + this.id + "', name='" + this.name + "', animation='" + this.animation + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\Emote.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */