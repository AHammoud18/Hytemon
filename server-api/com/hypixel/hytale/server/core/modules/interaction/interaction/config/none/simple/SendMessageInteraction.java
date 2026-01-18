/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.simple;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.receiver.IMessageReceiver;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.logging.Level;
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
/*    */ public class SendMessageInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<SendMessageInteraction> CODEC;
/*    */   private String key;
/*    */   private String message;
/*    */   
/*    */   static {
/* 42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SendMessageInteraction.class, SendMessageInteraction::new, SimpleInstantInteraction.CODEC).documentation("Debug interaction that sends a message on use.")).appendInherited(new KeyedCodec("Message", (Codec)Codec.STRING), (interaction, s) -> interaction.message = s, interaction -> interaction.message, (interaction, parent) -> interaction.message = parent.message).add()).appendInherited(new KeyedCodec("Key", (Codec)Codec.STRING), (o, v) -> o.key = v, o -> o.key, (o, p) -> o.key = p.key).add()).build();
/*    */   }
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
/*    */   public SendMessageInteraction(@Nonnull String id, @Nonnull String message) {
/* 61 */     super(id);
/* 62 */     this.message = message;
/* 63 */     this.unknown = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public SendMessageInteraction() {}
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 71 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 72 */     assert commandBuffer != null;
/*    */     
/* 74 */     Ref<EntityStore> ref = context.getOwningEntity();
/* 75 */     Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer);
/*    */     
/* 77 */     if (entity instanceof IMessageReceiver) { IMessageReceiver messageReceiver = (IMessageReceiver)entity;
/* 78 */       if (this.key != null) {
/* 79 */         messageReceiver.sendMessage(Message.translation(this.key));
/*    */       } else {
/* 81 */         messageReceiver.sendMessage(Message.raw(this.message));
/*    */       }  }
/*    */     else
/* 84 */     { HytaleLogger.getLogger().at(Level.INFO).log("SendMessageInteraction: %s for %s", (this.message != null) ? this.message : this.key, entity); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 91 */     return "SendMessageInteraction{message=" + this.message + "} " + super
/*    */       
/* 93 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\simple\SendMessageInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */