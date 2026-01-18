/*    */ package com.hypixel.hytale.server.core.asset.type.blocktick.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import java.util.Objects;
/*    */ import java.util.SplittableRandom;
/*    */ 
/*    */ public abstract class TickProcedure {
/* 12 */   public static final CodecMapCodec<TickProcedure> CODEC = new CodecMapCodec("Type");
/* 13 */   public static final BuilderCodec<TickProcedure> BASE_CODEC = BuilderCodec.abstractBuilder(TickProcedure.class)
/* 14 */     .build();
/*    */   
/* 16 */   protected static final SplittableRandom BASE_RANDOM = new SplittableRandom();
/* 17 */   protected static final ThreadLocal<SplittableRandom> RANDOM = ThreadLocal.withInitial(BASE_RANDOM::split); static { Objects.requireNonNull(BASE_RANDOM); }
/*    */ 
/*    */   
/*    */   public abstract BlockTickStrategy onTick(World paramWorld, WorldChunk paramWorldChunk, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */   
/*    */   protected SplittableRandom getRandom() {
/* 23 */     return RANDOM.get();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktick\config\TickProcedure.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */