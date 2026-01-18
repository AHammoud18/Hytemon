/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.core.asset.type.portalworld.PortalType;
/*    */ import java.util.function.Supplier;
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
/*    */ public class PortalKey
/*    */ {
/*    */   public static final BuilderCodec<PortalKey> CODEC;
/*    */   private String portalTypeId;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PortalKey.class, PortalKey::new).appendInherited(new KeyedCodec("PortalType", (Codec)Codec.STRING), (portalKey, o) -> portalKey.portalTypeId = o, portalKey -> portalKey.portalTypeId, (portalKey, parent) -> portalKey.portalTypeId = parent.portalTypeId).documentation("The ID of of the PortalType that this key opens.").addValidator(Validators.nonNull()).addValidator(PortalType.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("TimeLimitSeconds", (Codec)Codec.INTEGER), (portalKey, o) -> portalKey.timeLimitSeconds = o.intValue(), portalKey -> Integer.valueOf(portalKey.timeLimitSeconds), (portalKey, parent) -> portalKey.timeLimitSeconds = parent.timeLimitSeconds).add()).build();
/*    */   }
/*    */   
/* 31 */   private int timeLimitSeconds = -1;
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPortalTypeId() {
/* 36 */     return this.portalTypeId;
/*    */   }
/*    */   
/*    */   public int getTimeLimitSeconds() {
/* 40 */     return this.timeLimitSeconds;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return "PortalKey{instanceId='" + this.portalTypeId + "', timeLimitSeconds=" + this.timeLimitSeconds + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\PortalKey.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */