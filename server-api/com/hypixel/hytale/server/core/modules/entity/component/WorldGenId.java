/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenId
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final int NON_WORLD_GEN_ID = 0;
/*    */   public static final BuilderCodec<WorldGenId> CODEC;
/*    */   private int worldGenId;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(WorldGenId.class, WorldGenId::new).append(new KeyedCodec("WorldGenId", (Codec)Codec.INTEGER), (worldGenId, i) -> worldGenId.worldGenId = i.intValue(), worldGenId -> Integer.valueOf(worldGenId.worldGenId)).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, WorldGenId> getComponentType() {
/* 27 */     return EntityModule.get().getWorldGenIdComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldGenId(int worldGenId) {
/* 33 */     this.worldGenId = worldGenId;
/*    */   }
/*    */ 
/*    */   
/*    */   private WorldGenId() {}
/*    */   
/*    */   public int getWorldGenId() {
/* 40 */     return this.worldGenId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 47 */     return new WorldGenId(this.worldGenId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\WorldGenId.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */