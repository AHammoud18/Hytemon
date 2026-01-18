/*    */ package com.hypixel.hytale.builtin.blocktick.procedure;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.common.util.RandomUtil;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktick.config.TickProcedure;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.BsonValue;
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
/*    */ public class SplitChanceBlockGrowthProcedure
/*    */   extends BasicChanceBlockGrowthProcedure
/*    */ {
/*    */   public static final BuilderCodec<SplitChanceBlockGrowthProcedure> CODEC;
/*    */   protected int[] chances;
/*    */   protected String[] data;
/*    */   protected int sumChances;
/*    */   
/*    */   static {
/* 55 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SplitChanceBlockGrowthProcedure.class, SplitChanceBlockGrowthProcedure::new, TickProcedure.BASE_CODEC).append(new KeyedCodec("NextIds", (Codec)Codec.BSON_DOCUMENT), (proc, v, extraInfo) -> { proc.data = new String[v.size()]; proc.chances = new int[proc.data.length]; int i = 0; for (Map.Entry<String, BsonValue> entry : (Iterable<Map.Entry<String, BsonValue>>)v.entrySet()) { proc.data[i] = entry.getKey(); proc.chances[i] = Codec.INTEGER.decode((BsonValue)entry.getValue(), extraInfo).intValue(); proc.sumChances += proc.chances[i]; i++; }  }(proc, extraInfo) -> { if (proc.data == null || proc.chances == null) return null;  BsonDocument document = new BsonDocument(); for (int i = 0; i < proc.data.length; i++) document.append(proc.data[i], Codec.INTEGER.encode(Integer.valueOf(proc.chances[i]), extraInfo));  return (BiFunction)document; }).addValidator(Validators.nonNull()).add()).addField(new KeyedCodec("ChanceMin", (Codec)Codec.INTEGER), (proc, v) -> proc.chanceMin = v.intValue(), proc -> Integer.valueOf(proc.chanceMin))).addField(new KeyedCodec("Data", (Codec)Codec.INTEGER), (proc, v) -> proc.chance = v.intValue(), proc -> Integer.valueOf(proc.chance))).addField(new KeyedCodec("NextTicking", (Codec)Codec.BOOLEAN), (proc, v) -> proc.nextTicking = v.booleanValue(), proc -> Boolean.valueOf(proc.nextTicking))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SplitChanceBlockGrowthProcedure() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public SplitChanceBlockGrowthProcedure(int chanceMin, int chance, @Nonnull int[] chances, @Nonnull String[] data, boolean nextTicking) {
/* 65 */     super(chanceMin, chance, (String)null, nextTicking);
/* 66 */     this.chances = chances;
/* 67 */     this.data = data;
/* 68 */     if (chances.length != data.length) throw new IllegalArgumentException(String.valueOf(data.length)); 
/* 69 */     int localSumChances = 0;
/* 70 */     for (int c : chances) {
/* 71 */       if (c < 0) throw new IllegalArgumentException(String.valueOf(c)); 
/* 72 */       localSumChances += c;
/*    */     } 
/* 74 */     this.sumChances = localSumChances;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean executeToBlock(@Nonnull World world, int worldX, int worldY, int worldZ, String to) {
/* 79 */     String block = (String)RandomUtil.roll(getRandom().nextInt(this.sumChances), (Object[])this.data, this.chances);
/* 80 */     return super.executeToBlock(world, worldX, worldY, worldZ, block);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 86 */     return "SplitChanceBlockGrowthProcedure{chanceMin=" + this.chanceMin + ", chance=" + this.chance + ", to=" + this.to + ", nextTicking=" + this.nextTicking + ", chances=" + 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 91 */       Arrays.toString(this.chances) + ", data=" + 
/* 92 */       Arrays.toString((Object[])this.data) + ", sumChances=" + this.sumChances + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blocktick\procedure\SplitChanceBlockGrowthProcedure.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */