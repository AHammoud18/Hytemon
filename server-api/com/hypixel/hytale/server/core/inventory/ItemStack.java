/*     */ package com.hypixel.hytale.server.core.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.ItemQuantity;
/*     */ import com.hypixel.hytale.protocol.ItemWithAllMetadata;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStack
/*     */   implements NetworkSerializable<ItemWithAllMetadata>
/*     */ {
/*     */   @Nonnull
/*  26 */   public static final ItemStack[] EMPTY_ARRAY = new ItemStack[0];
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
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ItemStack> CODEC;
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
/*  74 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemStack.class, ItemStack::new).append(new KeyedCodec("Id", (Codec)Codec.STRING), (itemStack, id) -> itemStack.itemId = id, itemStack -> itemStack.itemId).addValidator(Validators.nonNull()).addValidator((Validator)Item.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (itemStack, quantity) -> itemStack.quantity = quantity.intValue(), itemStack -> Integer.valueOf(itemStack.quantity)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("Durability", (Codec)Codec.DOUBLE), (itemStack, durability) -> itemStack.durability = durability.doubleValue(), itemStack -> Double.valueOf(itemStack.durability)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("MaxDurability", (Codec)Codec.DOUBLE), (itemStack, maxDurability) -> itemStack.maxDurability = maxDurability.doubleValue(), itemStack -> Double.valueOf(itemStack.maxDurability)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Metadata", (Codec)Codec.BSON_DOCUMENT), (itemStack, bsonDocument) -> itemStack.metadata = bsonDocument, itemStack -> itemStack.metadata).add()).append(new KeyedCodec("OverrideDroppedItemAnimation", (Codec)Codec.BOOLEAN), (itemStack, b) -> itemStack.overrideDroppedItemAnimation = b.booleanValue(), itemStack -> Boolean.valueOf(itemStack.overrideDroppedItemAnimation)).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  80 */   public static final ItemStack EMPTY = new ItemStack()
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String itemId;
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected int quantity = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double durability;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double maxDurability;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean overrideDroppedItemAnimation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected BsonDocument metadata;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ItemWithAllMetadata cachedPacket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack(@Nonnull String itemId, int quantity, @Nullable BsonDocument metadata) {
/* 131 */     if (quantity <= 0) {
/* 132 */       throw new IllegalArgumentException(String.format("quantity %s must be >0!", new Object[] { Integer.valueOf(quantity) }));
/*     */     }
/*     */ 
/*     */     
/* 136 */     if (itemId == null) {
/* 137 */       throw new IllegalArgumentException("itemId cannot be null!");
/*     */     }
/*     */ 
/*     */     
/* 141 */     if (itemId.equals("Empty")) throw new IllegalArgumentException("itemId cannot be BlockTypeKey.EMPTY!");
/*     */     
/* 143 */     this.itemId = itemId;
/* 144 */     this.quantity = quantity;
/*     */     
/* 146 */     double maxDurability = getItem().getMaxDurability();
/* 147 */     this.durability = maxDurability;
/* 148 */     this.maxDurability = maxDurability;
/*     */     
/* 150 */     this.metadata = metadata;
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
/*     */   public ItemStack(@Nonnull String itemId, int quantity, double durability, double maxDurability, @Nullable BsonDocument metadata) {
/* 167 */     this(itemId, quantity, metadata);
/* 168 */     this.durability = durability;
/* 169 */     this.maxDurability = maxDurability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack(@Nonnull String itemId) {
/* 178 */     this(itemId, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack(@Nonnull String itemId, int quantity) {
/* 188 */     this(itemId, quantity, null);
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
/*     */   @Nonnull
/*     */   public String getItemId() {
/* 204 */     return this.itemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQuantity() {
/* 211 */     return this.quantity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public BsonDocument getMetadata() {
/* 221 */     if (this.metadata == null) return null; 
/* 222 */     return this.metadata.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnbreakable() {
/* 231 */     return (this.maxDurability <= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBroken() {
/* 240 */     if (isUnbreakable()) return false; 
/* 241 */     return (this.durability == 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxDurability() {
/* 248 */     return this.maxDurability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDurability() {
/* 255 */     return this.durability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 264 */     return this.itemId.equals("Empty");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOverrideDroppedItemAnimation() {
/* 273 */     return this.overrideDroppedItemAnimation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverrideDroppedItemAnimation(boolean b) {
/* 282 */     this.overrideDroppedItemAnimation = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getBlockKey() {
/* 292 */     if (isEmpty()) return "Empty";
/*     */     
/* 294 */     Item item = getItem();
/* 295 */     if (item == null) return null; 
/* 296 */     return item.hasBlockType() ? item.getBlockId() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Item getItem() {
/* 304 */     Item item = (Item)Item.getAssetMap().getAsset(this.itemId);
/* 305 */     if (item != null) return item; 
/* 306 */     return Item.UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 315 */     return (isEmpty() || getItem() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack withDurability(double durability) {
/* 326 */     return new ItemStack(this.itemId, this.quantity, MathUtil.clamp(durability, 0.0D, this.maxDurability), this.maxDurability, this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack withMaxDurability(double maxDurability) {
/* 337 */     return new ItemStack(this.itemId, this.quantity, Math.min(this.durability, maxDurability), maxDurability, this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack withIncreasedDurability(double inc) {
/* 348 */     return withDurability(this.durability + inc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack withRestoredDurability(double maxDurability) {
/* 359 */     return new ItemStack(this.itemId, this.quantity, maxDurability, maxDurability, this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack withState(@Nonnull String state) {
/* 370 */     String newItemId = getItem().getItemIdForState(state);
/* 371 */     if (newItemId == null) throw new IllegalArgumentException("Invalid state: " + state); 
/* 372 */     return new ItemStack(newItemId, this.quantity, this.durability, this.maxDurability, this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStack withQuantity(int quantity) {
/* 383 */     if (quantity == 0) return null; 
/* 384 */     if (quantity == this.quantity) return this; 
/* 385 */     return new ItemStack(this.itemId, quantity, this.durability, this.maxDurability, this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack withMetadata(@Nullable BsonDocument metadata) {
/* 396 */     return new ItemStack(this.itemId, this.quantity, this.durability, this.maxDurability, metadata);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public <T> ItemStack withMetadata(@Nonnull KeyedCodec<T> keyedCodec, @Nullable T data) {
/* 401 */     return withMetadata(keyedCodec.getKey(), keyedCodec.getChildCodec(), data);
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
/*     */   @Nonnull
/*     */   public <T> ItemStack withMetadata(@Nonnull String key, @Nonnull Codec<T> codec, @Nullable T data) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield metadata : Lorg/bson/BsonDocument;
/*     */     //   4: ifnonnull -> 17
/*     */     //   7: new org/bson/BsonDocument
/*     */     //   10: dup
/*     */     //   11: invokespecial <init> : ()V
/*     */     //   14: goto -> 24
/*     */     //   17: aload_0
/*     */     //   18: getfield metadata : Lorg/bson/BsonDocument;
/*     */     //   21: invokevirtual clone : ()Lorg/bson/BsonDocument;
/*     */     //   24: astore #4
/*     */     //   26: aload_3
/*     */     //   27: ifnonnull -> 40
/*     */     //   30: aload #4
/*     */     //   32: aload_1
/*     */     //   33: invokevirtual remove : (Ljava/lang/Object;)Lorg/bson/BsonValue;
/*     */     //   36: pop
/*     */     //   37: goto -> 111
/*     */     //   40: aload_2
/*     */     //   41: aload_3
/*     */     //   42: invokeinterface encode : (Ljava/lang/Object;)Lorg/bson/BsonValue;
/*     */     //   47: astore #5
/*     */     //   49: aload #5
/*     */     //   51: invokevirtual isNull : ()Z
/*     */     //   54: ifne -> 80
/*     */     //   57: aload #5
/*     */     //   59: instanceof org/bson/BsonDocument
/*     */     //   62: ifeq -> 84
/*     */     //   65: aload #5
/*     */     //   67: checkcast org/bson/BsonDocument
/*     */     //   70: astore #7
/*     */     //   72: aload #7
/*     */     //   74: invokevirtual isEmpty : ()Z
/*     */     //   77: ifeq -> 84
/*     */     //   80: iconst_1
/*     */     //   81: goto -> 85
/*     */     //   84: iconst_0
/*     */     //   85: istore #6
/*     */     //   87: iload #6
/*     */     //   89: ifeq -> 102
/*     */     //   92: aload #4
/*     */     //   94: aload_1
/*     */     //   95: invokevirtual remove : (Ljava/lang/Object;)Lorg/bson/BsonValue;
/*     */     //   98: pop
/*     */     //   99: goto -> 111
/*     */     //   102: aload #4
/*     */     //   104: aload_1
/*     */     //   105: aload #5
/*     */     //   107: invokevirtual put : (Ljava/lang/String;Lorg/bson/BsonValue;)Lorg/bson/BsonValue;
/*     */     //   110: pop
/*     */     //   111: aload #4
/*     */     //   113: invokevirtual isEmpty : ()Z
/*     */     //   116: ifeq -> 122
/*     */     //   119: aconst_null
/*     */     //   120: astore #4
/*     */     //   122: new com/hypixel/hytale/server/core/inventory/ItemStack
/*     */     //   125: dup
/*     */     //   126: aload_0
/*     */     //   127: getfield itemId : Ljava/lang/String;
/*     */     //   130: aload_0
/*     */     //   131: getfield quantity : I
/*     */     //   134: aload_0
/*     */     //   135: getfield durability : D
/*     */     //   138: aload_0
/*     */     //   139: getfield maxDurability : D
/*     */     //   142: aload #4
/*     */     //   144: invokespecial <init> : (Ljava/lang/String;IDDLorg/bson/BsonDocument;)V
/*     */     //   147: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #406	-> 0
/*     */     //   #407	-> 26
/*     */     //   #408	-> 30
/*     */     //   #410	-> 40
/*     */     //   #411	-> 49
/*     */     //   #412	-> 87
/*     */     //   #413	-> 92
/*     */     //   #415	-> 102
/*     */     //   #418	-> 111
/*     */     //   #419	-> 119
/*     */     //   #421	-> 122
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   72	8	7	doc	Lorg/bson/BsonDocument;
/*     */     //   49	62	5	bsonValue	Lorg/bson/BsonValue;
/*     */     //   87	24	6	empty	Z
/*     */     //   0	148	0	this	Lcom/hypixel/hytale/server/core/inventory/ItemStack;
/*     */     //   0	148	1	key	Ljava/lang/String;
/*     */     //   0	148	2	codec	Lcom/hypixel/hytale/codec/Codec;
/*     */     //   0	148	3	data	Ljava/lang/Object;
/*     */     //   26	122	4	clonedMeta	Lorg/bson/BsonDocument;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	148	2	codec	Lcom/hypixel/hytale/codec/Codec<TT;>;
/*     */     //   0	148	3	data	TT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack withMetadata(@Nonnull String key, @Nullable BsonValue bsonValue) {
/* 426 */     BsonDocument clonedMeta = (this.metadata == null) ? new BsonDocument() : this.metadata.clone();
/* 427 */     if (bsonValue == null || bsonValue.isNull()) {
/* 428 */       clonedMeta.remove(key);
/*     */     } else {
/* 430 */       clonedMeta.put(key, bsonValue);
/*     */     } 
/* 432 */     return new ItemStack(this.itemId, this.quantity, this.durability, this.maxDurability, clonedMeta);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemWithAllMetadata toPacket() {
/* 437 */     if (this.cachedPacket != null) return this.cachedPacket;
/*     */     
/* 439 */     ItemWithAllMetadata packet = new ItemWithAllMetadata();
/* 440 */     packet.itemId = this.itemId.toString();
/* 441 */     packet.quantity = this.quantity;
/* 442 */     packet.durability = this.durability;
/* 443 */     packet.maxDurability = this.maxDurability;
/* 444 */     packet.overrideDroppedItemAnimation = this.overrideDroppedItemAnimation;
/* 445 */     packet.metadata = (this.metadata != null) ? this.metadata.toJson() : null;
/* 446 */     this.cachedPacket = packet;
/* 447 */     return this.cachedPacket;
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
/*     */   public boolean isStackableWith(@Nullable ItemStack itemStack) {
/* 459 */     if (itemStack == null) return false; 
/* 460 */     if (Double.compare(itemStack.durability, this.durability) != 0) return false; 
/* 461 */     if (Double.compare(itemStack.maxDurability, this.maxDurability) != 0) return false; 
/* 462 */     if (!this.itemId.equals(itemStack.itemId)) return false; 
/* 463 */     return (this.metadata != null) ? this.metadata.equals(itemStack.metadata) : ((itemStack.metadata == null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEquivalentType(@Nullable ItemStack itemStack) {
/* 473 */     if (itemStack == null) return false; 
/* 474 */     if (!this.itemId.equals(itemStack.itemId)) return false; 
/* 475 */     return (this.metadata != null) ? this.metadata.equals(itemStack.metadata) : ((itemStack.metadata == null));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T> T getFromMetadataOrNull(@Nonnull KeyedCodec<T> keyedCodec) {
/* 480 */     return (T)keyedCodec.getOrNull(this.metadata);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T> T getFromMetadataOrNull(@Nonnull String key, @Nonnull Codec<T> codec) {
/* 485 */     BsonValue bsonValue = (this.metadata == null) ? null : this.metadata.get(key);
/* 486 */     return (bsonValue == null) ? null : (T)codec.decode(bsonValue);
/*     */   }
/*     */   
/*     */   public <T> T getFromMetadataOrDefault(@Nonnull String key, @Nonnull BuilderCodec<T> codec) {
/* 490 */     BsonDocument clonedMeta = (this.metadata == null) ? new BsonDocument() : this.metadata.clone();
/* 491 */     if (clonedMeta == null) return (T)codec.getDefaultValue();
/*     */     
/* 493 */     BsonValue bsonValue = clonedMeta.get(key);
/* 494 */     return (bsonValue == null) ? 
/* 495 */       (T)codec.getDefaultValue() : 
/* 496 */       (T)codec.decode(bsonValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 501 */     if (this == o) return true; 
/* 502 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 504 */     ItemStack itemStack = (ItemStack)o;
/*     */     
/* 506 */     if (this.quantity != itemStack.quantity) return false; 
/* 507 */     if (Double.compare(itemStack.durability, this.durability) != 0) return false; 
/* 508 */     if (Double.compare(itemStack.maxDurability, this.maxDurability) != 0) return false; 
/* 509 */     if (!this.itemId.equals(itemStack.itemId)) return false; 
/* 510 */     return (this.metadata != null) ? this.metadata.equals(itemStack.metadata) : ((itemStack.metadata == null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 517 */     int result = this.itemId.hashCode();
/* 518 */     result = 31 * result + this.quantity;
/* 519 */     long temp = Double.doubleToLongBits(this.durability);
/* 520 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 521 */     temp = Double.doubleToLongBits(this.maxDurability);
/* 522 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 523 */     result = 31 * result + ((this.metadata != null) ? this.metadata.hashCode() : 0);
/* 524 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 530 */     return "ItemStack{itemId=" + this.itemId + ", quantity=" + this.quantity + ", maxDurability=" + this.maxDurability + ", durability=" + this.durability + ", metadata=" + String.valueOf(this.metadata) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmpty(@Nullable ItemStack itemFrom) {
/* 540 */     return (itemFrom == null || itemFrom.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStackableWith(@Nullable ItemStack a, ItemStack b) {
/* 545 */     return (a == b || (a != null && a.isStackableWith(b)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEquivalentType(@Nullable ItemStack a, ItemStack b) {
/* 550 */     return (a == b || (a != null && a.isEquivalentType(b)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSameItemType(@Nullable ItemStack a, @Nullable ItemStack b) {
/* 555 */     return (a == b || (a != null && b != null && a.itemId.equals(b.itemId)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ItemStack fromPacket(@Nullable ItemQuantity packet) {
/* 566 */     if (packet == null) return null;
/*     */     
/* 568 */     int quantity = packet.quantity;
/* 569 */     if (quantity <= 0) return null;
/*     */     
/* 571 */     return new ItemStack(packet.itemId, quantity, null);
/*     */   }
/*     */   
/*     */   protected ItemStack() {}
/*     */   
/*     */   public static class Metadata {
/*     */     public static final String BLOCK_STATE = "BlockState";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\ItemStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */