/*     */ package com.hypixel.hytale.server.core.entity.entities.player.pages;
/*     */ 
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPage;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*     */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InteractiveCustomUIPage<T>
/*     */   extends CustomUIPage
/*     */ {
/*     */   @Nonnull
/*  28 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final BuilderCodec<T> eventDataCodec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractiveCustomUIPage(@Nonnull PlayerRef playerRef, @Nonnull CustomPageLifetime lifetime, @Nonnull BuilderCodec<T> eventDataCodec) {
/*  44 */     super(playerRef, lifetime);
/*  45 */     this.eventDataCodec = eventDataCodec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull T data) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendUpdate(@Nullable UICommandBuilder commandBuilder, @Nullable UIEventBuilder eventBuilder, boolean clear) {
/*  66 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  67 */     if (ref == null)
/*     */       return; 
/*  69 */     Store<EntityStore> store = ref.getStore();
/*  70 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  72 */     world.execute(() -> {
/*     */           if (!ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           playerComponent.getPageManager().updateCustomPage(new CustomPage(getClass().getName(), false, clear, this.lifetime, (commandBuilder != null) ? commandBuilder.getCommands() : UICommandBuilder.EMPTY_COMMAND_ARRAY, (eventBuilder != null) ? eventBuilder.getEvents() : UIEventBuilder.EMPTY_EVENT_BINDING_ARRAY));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, String rawData) {
/*     */     T data;
/*  87 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*     */     
/*     */     try {
/*  90 */       data = (T)this.eventDataCodec.decodeJson(new RawJsonReader(rawData.toCharArray()), extraInfo);
/*  91 */     } catch (IOException e) {
/*  92 */       throw new RuntimeException(e);
/*     */     } 
/*  94 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*  95 */     handleDataEvent(ref, store, data);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendUpdate(@Nullable UICommandBuilder commandBuilder, boolean clear) {
/* 100 */     sendUpdate(commandBuilder, (UIEventBuilder)null, clear);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\InteractiveCustomUIPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */