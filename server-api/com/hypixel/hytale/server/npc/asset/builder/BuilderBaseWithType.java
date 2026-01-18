/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringNotEmptyValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringValidator;
/*    */ import com.hypixel.hytale.server.spawning.ISpawnable;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public abstract class BuilderBaseWithType<T>
/*    */   extends BuilderBase<T>
/*    */   implements ISpawnable
/*    */ {
/*    */   private String type;
/*    */   
/*    */   public Builder<T> readCommonConfig(JsonElement data) {
/* 17 */     return super.readCommonConfig(data);
/*    */   }
/*    */   
/*    */   protected void readTypeKey(@Nonnull JsonElement data, String typeKey) {
/* 21 */     requireString(data, typeKey, s -> this.type = s, (StringValidator)StringNotEmptyValidator.get(), BuilderDescriptorState.Stable, "Type field", (String)null);
/*    */   }
/*    */   
/*    */   protected void readTypeKey(@Nonnull JsonElement data) {
/* 25 */     readTypeKey(data, "Type");
/*    */   }
/*    */   
/*    */   public String getType() {
/* 29 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderBaseWithType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */