/*    */ package com.hypixel.hytale.server.core.modules.entityui.asset;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.EntityUIComponent;
/*    */ import com.hypixel.hytale.protocol.EntityUIType;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityStatUIComponent
/*    */   extends EntityUIComponent
/*    */ {
/*    */   public static final BuilderCodec<EntityStatUIComponent> CODEC;
/*    */   protected String entityStat;
/*    */   protected int entityStatIndex;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EntityStatUIComponent.class, EntityStatUIComponent::new, EntityUIComponent.ABSTRACT_CODEC).appendInherited(new KeyedCodec("EntityStat", (Codec)Codec.STRING), (config, s) -> config.entityStat = s, config -> config.entityStat, (config, parent) -> config.entityStat = parent.entityStat).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).addValidator(EntityStatType.VALIDATOR_CACHE.getValidator()).documentation("The entity stat to represent.").add()).afterDecode(config -> config.entityStatIndex = EntityStatType.getAssetMap().getIndex(config.entityStat))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected EntityUIComponent generatePacket() {
/* 34 */     EntityUIComponent packet = super.generatePacket();
/* 35 */     packet.type = EntityUIType.EntityStat;
/* 36 */     packet.entityStatIndex = this.entityStatIndex;
/* 37 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 43 */     return "EntityStatUIComponentConfig{entityStat='" + this.entityStat + "'entityStatIndex='" + this.entityStatIndex + "'} " + super
/*    */ 
/*    */       
/* 46 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\asset\EntityStatUIComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */