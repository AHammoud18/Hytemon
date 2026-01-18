/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
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
/*    */ class PropertyCodecRegistryImpl
/*    */   implements PropertyCodecRegistry
/*    */ {
/*    */   private final List<PropertyCodecProvider> propertyCodecProviders;
/*    */   private final ConcurrentHashMap<TypeWithTypeParameters<?>, Codec<?>> propertyCodecCache;
/*    */   
/*    */   PropertyCodecRegistryImpl(PojoCodec<?> pojoCodec, CodecRegistry codecRegistry, List<PropertyCodecProvider> propertyCodecProviders) {
/* 32 */     List<PropertyCodecProvider> augmentedProviders = new ArrayList<>();
/* 33 */     if (propertyCodecProviders != null) {
/* 34 */       augmentedProviders.addAll(propertyCodecProviders);
/*    */     }
/* 36 */     augmentedProviders.add(new CollectionPropertyCodecProvider());
/* 37 */     augmentedProviders.add(new MapPropertyCodecProvider());
/* 38 */     augmentedProviders.add(new EnumPropertyCodecProvider(codecRegistry));
/* 39 */     augmentedProviders.add(new FallbackPropertyCodecProvider(pojoCodec, codecRegistry));
/* 40 */     this.propertyCodecProviders = augmentedProviders;
/* 41 */     this.propertyCodecCache = new ConcurrentHashMap<>();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <S> Codec<S> get(TypeWithTypeParameters<S> typeWithTypeParameters) {
/* 47 */     if (this.propertyCodecCache.containsKey(typeWithTypeParameters)) {
/* 48 */       return (Codec<S>)this.propertyCodecCache.get(typeWithTypeParameters);
/*    */     }
/*    */     
/* 51 */     for (PropertyCodecProvider propertyCodecProvider : this.propertyCodecProviders) {
/* 52 */       Codec<S> codec = propertyCodecProvider.get(typeWithTypeParameters, this);
/* 53 */       if (codec != null) {
/* 54 */         this.propertyCodecCache.put(typeWithTypeParameters, codec);
/* 55 */         return codec;
/*    */       } 
/*    */     } 
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyCodecRegistryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */