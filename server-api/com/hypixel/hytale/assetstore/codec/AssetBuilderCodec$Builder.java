/*     */ package com.hypixel.hytale.assetstore.codec;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.JsonAsset;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder<K, T extends JsonAsset<K>>
/*     */   extends BuilderCodec.BuilderBase<T, AssetBuilderCodec.Builder<K, T>>
/*     */ {
/*     */   @Nonnull
/*     */   protected final KeyedCodec<K> idCodec;
/*     */   protected final BiConsumer<T, K> idSetter;
/*     */   protected final BiConsumer<T, AssetExtraInfo.Data> dataSetter;
/*     */   @Nonnull
/*     */   protected final Function<T, AssetExtraInfo.Data> dataGetter;
/*     */   
/*     */   public Builder(Class<T> tClass, Supplier<T> supplier, Codec<K> idCodec, BiConsumer<T, K> idSetter, Function<T, K> idGetter, BiConsumer<T, AssetExtraInfo.Data> dataSetter, @Nonnull Function<T, AssetExtraInfo.Data> dataGetter) {
/* 162 */     super(tClass, supplier);
/* 163 */     this.idCodec = new KeyedCodec("Id", idCodec);
/* 164 */     this.idSetter = idSetter;
/* 165 */     this.dataSetter = dataSetter;
/* 166 */     this.dataGetter = dataGetter;
/*     */     
/* 168 */     appendInherited(AssetBuilderCodec.TAGS_CODEC, (t, tags) -> ((AssetExtraInfo.Data)dataGetter.apply(t)).putTags(tags), t -> {
/*     */           AssetExtraInfo.Data data = dataGetter.apply(t);
/*     */           
/*     */           return (data != null) ? data.getRawTags() : null;
/*     */         }(t, parent) -> {
/*     */           AssetExtraInfo.Data data = dataGetter.apply(t);
/*     */           AssetExtraInfo.Data parentData = dataGetter.apply(parent);
/*     */           if (data != null && parentData != null) {
/*     */             data.putTags(parentData.getRawTags());
/*     */           }
/* 178 */         }).documentation("Tags are a general way to describe an asset that can be interpreted by other systems in a way they see fit.\n\nFor example you could tag something with a **Material** tag with the values **Solid** and **Stone**, And another single tag **Ore**.\n\nTags will be expanded into a single list of tags automatically. Using the above example with **Material** and **Ore** the end result would be the following list of tags: **Ore**, **Material**, **Solid**, **Stone**, **Material=Solid** and **Material=Stone**.").add();
/*     */   }
/*     */   
/*     */   public Builder(Class<T> tClass, Supplier<T> supplier, BuilderCodec<? super T> parentCodec, Codec<K> idCodec, BiConsumer<T, K> idSetter, Function<T, K> idGetter, BiConsumer<T, AssetExtraInfo.Data> dataSetter, @Nonnull Function<T, AssetExtraInfo.Data> dataGetter) {
/* 182 */     super(tClass, supplier, parentCodec);
/* 183 */     this.idCodec = new KeyedCodec("Id", idCodec);
/* 184 */     this.idSetter = idSetter;
/* 185 */     this.dataSetter = dataSetter;
/* 186 */     this.dataGetter = dataGetter;
/*     */     
/* 188 */     appendInherited(AssetBuilderCodec.TAGS_CODEC, (t, tags) -> ((AssetExtraInfo.Data)dataGetter.apply(t)).putTags(tags), t -> {
/*     */           AssetExtraInfo.Data data = dataGetter.apply(t);
/*     */           
/*     */           return (data != null) ? data.getRawTags() : null;
/*     */         }(t, parent) -> {
/*     */           AssetExtraInfo.Data data = dataGetter.apply(t);
/*     */           AssetExtraInfo.Data parentData = dataGetter.apply(parent);
/*     */           if (data != null && parentData != null) {
/*     */             data.putTags(parentData.getRawTags());
/*     */           }
/* 198 */         }).documentation("Tags are a general way to describe an asset that can be interpreted by other systems in a way they see fit.\n\nFor example you could tag something with a **Material** tag with the values **Solid** and **Stone**, And another single tag **Ore**.\n\nTags will be expanded into a single list of tags automatically. Using the above example with **Material** and **Ore** the end result would be the following list of tags: **Ore**, **Material**, **Solid**, **Stone**, **Material=Solid** and **Material=Stone**.").add();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetBuilderCodec<K, T> build() {
/* 204 */     return new AssetBuilderCodec<>(this);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\codec\AssetBuilderCodec$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */