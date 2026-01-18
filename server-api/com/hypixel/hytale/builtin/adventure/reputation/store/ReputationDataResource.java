/*    */ package com.hypixel.hytale.builtin.adventure.reputation.store;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.Object2IntMapCodec;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReputationDataResource
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<ReputationDataResource> CODEC;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ReputationDataResource.class, ReputationDataResource::new).append(new KeyedCodec("Stats", (Codec)new Object2IntMapCodec((Codec)Codec.STRING, Object2IntOpenHashMap::new, false)), (reputationDataResource, stringObject2IntMap) -> reputationDataResource.reputationStats = stringObject2IntMap, reputationDataResource -> reputationDataResource.reputationStats).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private Object2IntMap<String> reputationStats = (Object2IntMap<String>)new Object2IntOpenHashMap(0);
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
/*    */   @Nonnull
/*    */   public Object2IntMap<String> getReputationStats() {
/* 50 */     return this.reputationStats;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 56 */     ReputationDataResource resource = new ReputationDataResource();
/* 57 */     resource.reputationStats = (Object2IntMap<String>)new Object2IntOpenHashMap(this.reputationStats);
/* 58 */     return resource;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\store\ReputationDataResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */