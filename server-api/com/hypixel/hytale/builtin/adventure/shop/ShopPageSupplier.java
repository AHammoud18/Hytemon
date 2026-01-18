/*    */ package com.hypixel.hytale.builtin.adventure.shop;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ShopPageSupplier
/*    */   implements OpenCustomUIInteraction.CustomPageSupplier
/*    */ {
/*    */   public static final BuilderCodec<ShopPageSupplier> CODEC;
/*    */   protected String shopId;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ShopPageSupplier.class, ShopPageSupplier::new).appendInherited(new KeyedCodec("ShopId", (Codec)Codec.STRING), (data, o) -> data.shopId = o, data -> data.shopId, (data, parent) -> data.shopId = parent.shopId).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CustomUIPage tryCreate(Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor, @Nonnull PlayerRef playerRef, InteractionContext context) {
/* 31 */     return (CustomUIPage)new ShopPage(playerRef, this.shopId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\ShopPageSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */