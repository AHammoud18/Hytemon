/*    */ package com.hypixel.hytale.builtin.adventure.shop;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceElement;
/*    */ import com.hypixel.hytale.server.core.ui.LocalizableString;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShopElement
/*    */   extends ChoiceElement
/*    */ {
/*    */   public static final BuilderCodec<ShopElement> CODEC;
/*    */   protected int cost;
/*    */   protected String iconPath;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ShopElement.class, ShopElement::new, ChoiceElement.BASE_CODEC).append(new KeyedCodec("Cost", (Codec)Codec.INTEGER), (shopElement, integer) -> shopElement.cost = integer.intValue(), shopElement -> Integer.valueOf(shopElement.cost)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (shopElement, s) -> shopElement.iconPath = s, shopElement -> shopElement.iconPath).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addButton(@Nonnull UICommandBuilder commandBuilder, UIEventBuilder eventBuilder, String selector, PlayerRef playerRef) {
/* 40 */     commandBuilder.append("#ElementList", "Pages/ShopElementButton.ui");
/* 41 */     commandBuilder.set(selector + " #Icon.Background", this.iconPath);
/* 42 */     commandBuilder.setObject(selector + " #Name.Text", LocalizableString.fromMessageId(this.displayNameKey));
/* 43 */     commandBuilder.setObject(selector + " #Description.Text", LocalizableString.fromMessageId(this.descriptionKey));
/* 44 */     commandBuilder.set(selector + " #Cost.Text", "" + this.cost);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canFulfillRequirements(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
/* 50 */     return super.canFulfillRequirements(store, ref, playerRef);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\ShopElement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */