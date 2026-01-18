/*    */ package com.hypixel.hytale.server.core.modules.interaction.suppliers;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.itemrepair.ItemRepairPage;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemContext;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ItemRepairPageSupplier
/*    */   implements OpenCustomUIInteraction.CustomPageSupplier {
/*    */   public static final BuilderCodec<ItemRepairPageSupplier> CODEC;
/*    */   protected double repairPenalty;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ItemRepairPageSupplier.class, ItemRepairPageSupplier::new).appendInherited(new KeyedCodec("RepairPenalty", (Codec)Codec.DOUBLE), (data, o) -> data.repairPenalty = o.doubleValue(), data -> Double.valueOf(data.repairPenalty), (data, parent) -> data.repairPenalty = parent.repairPenalty).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomUIPage tryCreate(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull PlayerRef playerRef, @Nonnull InteractionContext context) {
/* 32 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 33 */     assert playerComponent != null;
/*    */     
/* 35 */     ItemContext itemContext = context.createHeldItemContext();
/* 36 */     if (itemContext == null) return null;
/*    */     
/* 38 */     return (CustomUIPage)new ItemRepairPage(playerRef, (ItemContainer)playerComponent.getInventory().getCombinedArmorHotbarUtilityStorage(), this.repairPenalty, itemContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\suppliers\ItemRepairPageSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */