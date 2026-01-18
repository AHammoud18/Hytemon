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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FromWorldGen
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<FromWorldGen> CODEC;
/*    */   private int worldGenId;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(FromWorldGen.class, FromWorldGen::new).append(new KeyedCodec("WorldGenId", (Codec)Codec.INTEGER), (fromWorldGen, i) -> fromWorldGen.worldGenId = i.intValue(), fromWorldGen -> Integer.valueOf(fromWorldGen.worldGenId)).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, FromWorldGen> getComponentType() {
/* 32 */     return EntityModule.get().getFromWorldGenComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private FromWorldGen() {}
/*    */ 
/*    */   
/*    */   public FromWorldGen(int worldGenId) {
/* 41 */     this.worldGenId = worldGenId;
/*    */   }
/*    */   
/*    */   public int getWorldGenId() {
/* 45 */     return this.worldGenId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 52 */     return new FromWorldGen(this.worldGenId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\FromWorldGen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */