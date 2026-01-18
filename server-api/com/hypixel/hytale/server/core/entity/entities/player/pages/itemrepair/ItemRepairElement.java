/*    */ package com.hypixel.hytale.server.core.entity.entities.player.pages.itemrepair;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceElement;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ItemRepairElement
/*    */   extends ChoiceElement {
/*    */   protected ItemStack itemStack;
/*    */   
/*    */   public ItemRepairElement(ItemStack itemStack, RepairItemInteraction interaction) {
/* 17 */     this.itemStack = itemStack;
/* 18 */     this.interactions = new ChoiceInteraction[] { interaction };
/*    */   }
/*    */ 
/*    */   
/*    */   public void addButton(@Nonnull UICommandBuilder commandBuilder, UIEventBuilder eventBuilder, String selector, PlayerRef playerRef) {
/* 23 */     int durabilityPercentage = (int)Math.round(this.itemStack.getDurability() / this.itemStack.getMaxDurability() * 100.0D);
/*    */     
/* 25 */     commandBuilder.append("#ElementList", "Pages/ItemRepairElement.ui");
/* 26 */     commandBuilder.set(selector + " #Icon.ItemId", this.itemStack.getItemId().toString());
/* 27 */     commandBuilder.set(selector + " #Name.TextSpans", Message.translation(this.itemStack.getItem().getTranslationKey()));
/* 28 */     commandBuilder.set(selector + " #Durability.Text", "" + durabilityPercentage + "%");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\itemrepair\ItemRepairElement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */