/*     */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.DeathConfig;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class DeathComponent
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   public static final BuilderCodec<DeathComponent> CODEC;
/*     */   private String deathCause;
/*     */   @Nullable
/*     */   private Message deathMessage;
/*     */   
/*     */   public static ComponentType<EntityStore, DeathComponent> getComponentType() {
/*  34 */     return DamageModule.get().getDeathComponentType();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  80 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DeathComponent.class, DeathComponent::new).append(new KeyedCodec("DeathCause", (Codec)Codec.STRING), (o, i) -> o.deathCause = i, o -> o.deathCause).add()).append(new KeyedCodec("DeathMessage", (Codec)Message.CODEC), (deathComponent, message) -> deathComponent.deathMessage = message, deathComponent -> deathComponent.deathMessage).add()).append(new KeyedCodec("ShowDeathMenu", (Codec)BuilderCodec.BOOLEAN), (deathComponent, showDeathMenu) -> deathComponent.showDeathMenu = showDeathMenu.booleanValue(), deathComponent -> Boolean.valueOf(deathComponent.showDeathMenu)).add()).append(new KeyedCodec("ItemsLostOnDeath", (Codec)new ArrayCodec((Codec)ItemStack.CODEC, x$0 -> new ItemStack[x$0])), (deathComponent, itemStacks) -> deathComponent.itemsLostOnDeath = itemStacks, deathComponent -> deathComponent.itemsLostOnDeath).add()).append(new KeyedCodec("ItemsAmountLossPercentage", (Codec)Codec.DOUBLE), (deathComponent, aDouble) -> deathComponent.itemsAmountLossPercentage = aDouble.doubleValue(), deathComponent -> Double.valueOf(deathComponent.itemsAmountLossPercentage)).add()).append(new KeyedCodec("ItemsDurabilityLossPercentage", (Codec)Codec.DOUBLE), (deathComponent, aDouble) -> deathComponent.itemsDurabilityLossPercentage = aDouble.doubleValue(), deathComponent -> Double.valueOf(deathComponent.itemsDurabilityLossPercentage)).add()).append(new KeyedCodec("DisplayDataOnDeathScreen", (Codec)Codec.BOOLEAN), (deathComponent, aBoolean) -> deathComponent.displayDataOnDeathScreen = aBoolean.booleanValue(), deathComponent -> Boolean.valueOf(deathComponent.displayDataOnDeathScreen)).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean showDeathMenu = true;
/*     */   
/*     */   private ItemStack[] itemsLostOnDeath;
/*     */   
/*     */   private double itemsAmountLossPercentage;
/*     */   
/*     */   private double itemsDurabilityLossPercentage;
/*     */   
/*     */   private boolean displayDataOnDeathScreen;
/*     */   @Nullable
/*     */   private Damage deathInfo;
/*  95 */   private DeathConfig.ItemsLossMode itemsLossMode = DeathConfig.ItemsLossMode.ALL;
/*     */   
/*     */   @Nullable
/*     */   private InteractionChain interactionChain;
/*     */   
/*     */   protected DeathComponent(@Nonnull Damage deathInfo) {
/* 101 */     this.deathInfo = deathInfo;
/* 102 */     this.deathCause = deathInfo.getCause().getId();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DamageCause getDeathCause() {
/* 119 */     return (DamageCause)((IndexedLookupTableAssetMap)AssetRegistry.getAssetStore(DamageCause.class).getAssetMap()).getAsset(this.deathCause);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Message getDeathMessage() {
/* 124 */     return this.deathMessage;
/*     */   }
/*     */   
/*     */   public void setDeathMessage(@Nullable Message deathMessage) {
/* 128 */     this.deathMessage = deathMessage;
/*     */   }
/*     */   
/*     */   public boolean isShowDeathMenu() {
/* 132 */     return this.showDeathMenu;
/*     */   }
/*     */   
/*     */   public void setShowDeathMenu(boolean showDeathMenu) {
/* 136 */     this.showDeathMenu = showDeathMenu;
/*     */   }
/*     */   
/*     */   public ItemStack[] getItemsLostOnDeath() {
/* 140 */     return this.itemsLostOnDeath;
/*     */   }
/*     */   
/*     */   public void setItemsLostOnDeath(List<ItemStack> itemsLostOnDeath) {
/* 144 */     this.itemsLostOnDeath = (ItemStack[])itemsLostOnDeath.toArray(x$0 -> new ItemStack[x$0]);
/*     */   }
/*     */   
/*     */   public double getItemsAmountLossPercentage() {
/* 148 */     return this.itemsAmountLossPercentage;
/*     */   }
/*     */   
/*     */   public void setItemsAmountLossPercentage(double itemsAmountLossPercentage) {
/* 152 */     this.itemsAmountLossPercentage = itemsAmountLossPercentage;
/*     */   }
/*     */   
/*     */   public double getItemsDurabilityLossPercentage() {
/* 156 */     return this.itemsDurabilityLossPercentage;
/*     */   }
/*     */   
/*     */   public void setItemsDurabilityLossPercentage(double itemsDurabilityLossPercentage) {
/* 160 */     this.itemsDurabilityLossPercentage = itemsDurabilityLossPercentage;
/*     */   }
/*     */   
/*     */   public boolean displayDataOnDeathScreen() {
/* 164 */     return this.displayDataOnDeathScreen;
/*     */   }
/*     */   
/*     */   public void setDisplayDataOnDeathScreen(boolean displayDataOnDeathScreen) {
/* 168 */     this.displayDataOnDeathScreen = displayDataOnDeathScreen;
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
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Damage getDeathInfo() {
/* 183 */     return this.deathInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeathConfig.ItemsLossMode getItemsLossMode() {
/* 192 */     return this.itemsLossMode;
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
/*     */ 
/*     */   
/*     */   public void setItemsLossMode(DeathConfig.ItemsLossMode itemsLossMode) {
/* 206 */     this.itemsLossMode = itemsLossMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeathItemLoss getDeathItemLoss() {
/* 216 */     return new DeathItemLoss(this.itemsLossMode, this.itemsLostOnDeath, this.itemsAmountLossPercentage, this.itemsDurabilityLossPercentage);
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
/*     */   @Nullable
/*     */   public InteractionChain getInteractionChain() {
/* 229 */     return this.interactionChain;
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
/*     */   public void setInteractionChain(@Nullable InteractionChain interactionChain) {
/* 241 */     this.interactionChain = interactionChain;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 247 */     DeathComponent death = new DeathComponent();
/* 248 */     death.deathCause = this.deathCause;
/* 249 */     death.deathMessage = this.deathMessage;
/* 250 */     death.showDeathMenu = this.showDeathMenu;
/* 251 */     death.itemsLostOnDeath = this.itemsLostOnDeath;
/* 252 */     death.itemsAmountLossPercentage = this.itemsAmountLossPercentage;
/* 253 */     death.itemsDurabilityLossPercentage = this.itemsDurabilityLossPercentage;
/* 254 */     death.displayDataOnDeathScreen = this.displayDataOnDeathScreen;
/*     */     
/* 256 */     death.deathInfo = this.deathInfo;
/* 257 */     death.itemsLossMode = this.itemsLossMode;
/* 258 */     return death;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void tryAddComponent(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref, @Nonnull Damage damage) {
/* 269 */     if (commandBuffer.getArchetype(ref).contains(getComponentType()))
/* 270 */       return;  commandBuffer.run(store -> tryAddComponent(store, ref, damage));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void tryAddComponent(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Damage damage) {
/* 280 */     if (store.getArchetype(ref).contains(getComponentType()))
/* 281 */       return;  store.addComponent(ref, getComponentType(), new DeathComponent(damage));
/*     */   }
/*     */   
/*     */   protected DeathComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\DeathComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */