/*    */ package com.hypixel.hytale.builtin.blocktick.procedure;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktick.config.TickProcedure;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicChanceBlockGrowthProcedure
/*    */   extends TickProcedure
/*    */ {
/*    */   public static final BuilderCodec<BasicChanceBlockGrowthProcedure> CODEC;
/*    */   protected int chanceMin;
/*    */   protected int chance;
/*    */   protected String to;
/*    */   protected boolean nextTicking;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BasicChanceBlockGrowthProcedure.class, BasicChanceBlockGrowthProcedure::new, TickProcedure.BASE_CODEC).addField(new KeyedCodec("NextId", (Codec)Codec.STRING), (proc, v) -> proc.to = v, proc -> proc.to)).addField(new KeyedCodec("ChanceMin", (Codec)Codec.INTEGER), (proc, v) -> proc.chanceMin = v.intValue(), proc -> Integer.valueOf(proc.chanceMin))).addField(new KeyedCodec("Chance", (Codec)Codec.INTEGER), (proc, v) -> proc.chance = v.intValue(), proc -> Integer.valueOf(proc.chance))).addField(new KeyedCodec("NextTicking", (Codec)Codec.BOOLEAN), (proc, v) -> proc.nextTicking = v.booleanValue(), proc -> Boolean.valueOf(proc.nextTicking))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicChanceBlockGrowthProcedure() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicChanceBlockGrowthProcedure(int chanceMin, int chance, String to, boolean nextTicking) {
/* 46 */     this.chanceMin = chanceMin;
/* 47 */     this.chance = chance;
/* 48 */     this.to = to;
/* 49 */     this.nextTicking = nextTicking;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlockTickStrategy onTick(@Nonnull World world, WorldChunk wc, int worldX, int worldY, int worldZ, int blockId) {
/* 55 */     if (!runChance()) return BlockTickStrategy.CONTINUE; 
/* 56 */     if (executeToBlock(world, worldX, worldY, worldZ, this.to)) return BlockTickStrategy.CONTINUE; 
/* 57 */     return BlockTickStrategy.SLEEP;
/*    */   }
/*    */   
/*    */   protected boolean runChance() {
/* 61 */     return (getRandom().nextInt(this.chance) < this.chanceMin);
/*    */   }
/*    */   
/*    */   protected boolean executeToBlock(@Nonnull World world, int worldX, int worldY, int worldZ, String to) {
/* 65 */     world.setBlock(worldX, worldY, worldZ, to);
/* 66 */     return !this.nextTicking;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "BasicChanceBlockGrowthProcedure{chanceMin=" + this.chanceMin + ", chance=" + this.chance + ", to=" + this.to + ", nextTicking=" + this.nextTicking + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blocktick\procedure\BasicChanceBlockGrowthProcedure.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */