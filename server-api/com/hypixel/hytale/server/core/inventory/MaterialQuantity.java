/*     */ package com.hypixel.hytale.server.core.inventory;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.MaterialQuantity;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class MaterialQuantity implements NetworkSerializable<MaterialQuantity> {
/*  16 */   public static final MaterialQuantity[] EMPTY_ARRAY = new MaterialQuantity[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<MaterialQuantity> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String itemId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String resourceTypeId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String tag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  50 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MaterialQuantity.class, MaterialQuantity::new).addField(new KeyedCodec("ItemId", (Codec)Codec.STRING), (craftingMaterial, blockTypeKey) -> craftingMaterial.itemId = blockTypeKey, craftingMaterial -> craftingMaterial.itemId)).addField(new KeyedCodec("ResourceTypeId", (Codec)Codec.STRING), (craftingMaterial, s) -> craftingMaterial.resourceTypeId = s, craftingMaterial -> craftingMaterial.resourceTypeId)).addField(new KeyedCodec("ItemTag", (Codec)Codec.STRING), (materialQuantity, s) -> materialQuantity.tag = s, materialQuantity -> materialQuantity.tag)).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (craftingMaterial, s) -> craftingMaterial.quantity = s.intValue(), craftingMaterial -> Integer.valueOf(craftingMaterial.quantity)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).addField(new KeyedCodec("Metadata", (Codec)Codec.BSON_DOCUMENT), (craftingMaterial, s) -> craftingMaterial.metadata = s, craftingMaterial -> craftingMaterial.metadata)).afterDecode((materialQuantity, extraInfo) -> { if (materialQuantity.tag != null) materialQuantity.tagIndex = AssetRegistry.getOrCreateTagIndex(materialQuantity.tag);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   protected int tagIndex = Integer.MIN_VALUE;
/*  58 */   protected int quantity = 1;
/*     */   @Nullable
/*     */   protected BsonDocument metadata;
/*     */   
/*     */   public MaterialQuantity(@Nullable String itemId, @Nullable String resourceTypeId, @Nullable String tag, int quantity, BsonDocument metadata) {
/*  63 */     if (itemId == null && resourceTypeId == null && tag == null) throw new IllegalArgumentException("itemId, resourceTypeId and tag cannot all be null!"); 
/*  64 */     if (quantity <= 0) throw new IllegalArgumentException("quantity " + quantity + " must be >0!");
/*     */     
/*  66 */     this.itemId = itemId;
/*  67 */     this.resourceTypeId = resourceTypeId;
/*  68 */     this.tag = tag;
/*  69 */     this.quantity = quantity;
/*  70 */     this.metadata = metadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getItemId() {
/*  78 */     return this.itemId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getResourceTypeId() {
/*  83 */     return this.resourceTypeId;
/*     */   }
/*     */   
/*     */   public int getTagIndex() {
/*  87 */     return this.tagIndex;
/*     */   }
/*     */   
/*     */   public int getQuantity() {
/*  91 */     return this.quantity;
/*     */   }
/*     */   
/*     */   public BsonDocument getMetadata() {
/*  95 */     return this.metadata;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public MaterialQuantity clone(int quantity) {
/* 100 */     return new MaterialQuantity(this.itemId, this.resourceTypeId, this.tag, quantity, this.metadata);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ItemStack toItemStack() {
/* 105 */     if (this.itemId == null) return null; 
/* 106 */     if (this.itemId.equals("Empty")) return ItemStack.EMPTY; 
/* 107 */     return new ItemStack(this.itemId, this.quantity, this.metadata);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ResourceQuantity toResource() {
/* 112 */     if (this.resourceTypeId == null) return null; 
/* 113 */     return new ResourceQuantity(this.resourceTypeId, this.quantity);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MaterialQuantity toPacket() {
/* 119 */     MaterialQuantity packet = new MaterialQuantity();
/* 120 */     if (this.itemId != null) packet.itemId = this.itemId.toString(); 
/* 121 */     packet.itemTag = this.tagIndex;
/* 122 */     packet.resourceTypeId = this.resourceTypeId;
/* 123 */     packet.quantity = this.quantity;
/* 124 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 129 */     if (this == o) return true; 
/* 130 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 132 */     MaterialQuantity that = (MaterialQuantity)o;
/*     */     
/* 134 */     if (this.quantity != that.quantity) return false; 
/* 135 */     if ((this.itemId != null) ? !this.itemId.equals(that.itemId) : (that.itemId != null)) return false; 
/* 136 */     if ((this.resourceTypeId != null) ? !this.resourceTypeId.equals(that.resourceTypeId) : (that.resourceTypeId != null)) return false; 
/* 137 */     if ((this.tag != null) ? !this.tag.equals(that.tag) : (that.tag != null)) return false; 
/* 138 */     return (this.metadata != null) ? this.metadata.equals(that.metadata) : ((that.metadata == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     int result = (this.itemId != null) ? this.itemId.hashCode() : 0;
/* 144 */     result = 31 * result + ((this.resourceTypeId != null) ? this.resourceTypeId.hashCode() : 0);
/* 145 */     result = 31 * result + ((this.tag != null) ? this.tag.hashCode() : 0);
/* 146 */     result = 31 * result + this.quantity;
/* 147 */     result = 31 * result + ((this.metadata != null) ? this.metadata.hashCode() : 0);
/* 148 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 154 */     return "MaterialQuantity{itemId=" + this.itemId + ", resourceTypeId='" + this.resourceTypeId + "', tag='" + this.tag + "', quantity=" + this.quantity + ", metadata=" + String.valueOf(this.metadata) + "}";
/*     */   }
/*     */   
/*     */   protected MaterialQuantity() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\MaterialQuantity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */