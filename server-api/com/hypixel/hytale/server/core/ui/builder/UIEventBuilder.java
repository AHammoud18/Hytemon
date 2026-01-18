/*    */ package com.hypixel.hytale.server.core.ui.builder;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBinding;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIEventBuilder
/*    */ {
/* 18 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/* 19 */   public static final CustomUIEventBinding[] EMPTY_EVENT_BINDING_ARRAY = new CustomUIEventBinding[0];
/*    */   @Nonnull
/* 21 */   private final List<CustomUIEventBinding> events = (List<CustomUIEventBinding>)new ObjectArrayList();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UIEventBuilder addEventBinding(CustomUIEventBindingType type, String selector) {
/* 26 */     return addEventBinding(type, selector, (EventData)null);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public UIEventBuilder addEventBinding(CustomUIEventBindingType type, String selector, boolean locksInterface) {
/* 31 */     return addEventBinding(type, selector, null, locksInterface);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public UIEventBuilder addEventBinding(CustomUIEventBindingType type, String selector, EventData data) {
/* 36 */     return addEventBinding(type, selector, data, true);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public UIEventBuilder addEventBinding(CustomUIEventBindingType type, String selector, @Nullable EventData data, boolean locksInterface) {
/* 41 */     String dataString = null;
/* 42 */     if (data != null) {
/* 43 */       ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 44 */       dataString = MapCodec.STRING_HASH_MAP_CODEC.encode(data.events(), extraInfo).asDocument().toJson();
/* 45 */       extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*    */     } 
/* 47 */     this.events.add(new CustomUIEventBinding(type, selector, dataString, locksInterface));
/* 48 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CustomUIEventBinding[] getEvents() {
/* 53 */     return (CustomUIEventBinding[])this.events.toArray(x$0 -> new CustomUIEventBinding[x$0]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\builder\UIEventBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */