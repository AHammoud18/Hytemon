/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*    */ import com.hypixel.hytale.protocol.ItemTranslationProperties;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ public class ItemTranslationProperties
/*    */   implements NetworkSerializable<ItemTranslationProperties>
/*    */ {
/*    */   public static final BuilderCodec<ItemTranslationProperties> CODEC;
/*    */   @Nullable
/*    */   private String name;
/*    */   @Nullable
/*    */   private String description;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemTranslationProperties.class, ItemTranslationProperties::new).appendInherited(new KeyedCodec("Name", (Codec)Codec.STRING), (data, s) -> data.name = s, data -> data.name, (o, p) -> o.name = p.name).documentation("The translation key for the name of this item.").metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("server.items.{assetId}.name", true))).add()).appendInherited(new KeyedCodec("Description", (Codec)Codec.STRING), (data, s) -> data.description = s, data -> data.description, (o, p) -> o.description = p.description).documentation("The translation key for the description of this item.").metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("server.items.{assetId}.description"))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ItemTranslationProperties() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemTranslationProperties(@Nonnull String name, @Nonnull String description) {
/* 59 */     this.name = name;
/* 60 */     this.description = description;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getName() {
/* 65 */     return this.name;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getDescription() {
/* 70 */     return this.description;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemTranslationProperties toPacket() {
/* 76 */     ItemTranslationProperties packet = new ItemTranslationProperties();
/* 77 */     packet.name = this.name;
/* 78 */     packet.description = this.description;
/* 79 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 85 */     return "ItemTranslationProperties{name=" + this.name + ", description=" + this.description + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemTranslationProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */