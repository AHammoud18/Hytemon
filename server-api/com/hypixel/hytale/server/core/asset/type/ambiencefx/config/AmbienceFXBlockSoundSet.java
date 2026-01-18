/*    */ package com.hypixel.hytale.server.core.asset.type.ambiencefx.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.AmbienceFXBlockSoundSet;
/*    */ import com.hypixel.hytale.protocol.Rangef;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AmbienceFXBlockSoundSet
/*    */   implements NetworkSerializable<AmbienceFXBlockSoundSet>
/*    */ {
/*    */   public static final BuilderCodec<AmbienceFXBlockSoundSet> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AmbienceFXBlockSoundSet.class, AmbienceFXBlockSoundSet::new).append(new KeyedCodec("BlockSoundSetId", (Codec)Codec.STRING), (ambienceFXBlockSoundSet, s) -> ambienceFXBlockSoundSet.blockSoundSetId = s, ambienceFXBlockSoundSet -> ambienceFXBlockSoundSet.blockSoundSetId).addValidator(Validators.nonNull()).addValidator(BlockSoundSet.VALIDATOR_CACHE.getValidator()).add()).addField(new KeyedCodec("Percent", (Codec)ProtocolCodecs.RANGEF), (ambienceFXBlockSoundSet, o) -> ambienceFXBlockSoundSet.percent = o, ambienceFXBlockSoundSet -> ambienceFXBlockSoundSet.percent)).afterDecode(AmbienceFXBlockSoundSet::processConfig)).build();
/*    */   }
/* 29 */   public static final Rangef DEFAULT_PERCENT = new Rangef(0.0F, 0.0F);
/*    */   
/*    */   protected String blockSoundSetId;
/*    */   protected transient int blockSoundSetIndex;
/* 33 */   protected Rangef percent = DEFAULT_PERCENT;
/*    */   
/*    */   public AmbienceFXBlockSoundSet(String blockSoundSetId, Rangef percent) {
/* 36 */     this.blockSoundSetId = blockSoundSetId;
/* 37 */     this.percent = percent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AmbienceFXBlockSoundSet toPacket() {
/* 46 */     AmbienceFXBlockSoundSet packet = new AmbienceFXBlockSoundSet();
/* 47 */     packet.blockSoundSetIndex = this.blockSoundSetIndex;
/* 48 */     packet.percent = this.percent;
/* 49 */     return packet;
/*    */   }
/*    */   
/*    */   public String getBlockSoundSetId() {
/* 53 */     return this.blockSoundSetId;
/*    */   }
/*    */   
/*    */   public Rangef getPercent() {
/* 57 */     return this.percent;
/*    */   }
/*    */   
/*    */   protected void processConfig() {
/* 61 */     this.blockSoundSetIndex = BlockSoundSet.getAssetMap().getIndex(this.blockSoundSetId);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 67 */     return "AmbienceFXBlockSoundSet{blockSoundSetId='" + this.blockSoundSetId + "'blockSoundSetIndex='" + this.blockSoundSetIndex + "', percent=" + String.valueOf(this.percent) + "}";
/*    */   }
/*    */   
/*    */   protected AmbienceFXBlockSoundSet() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\ambiencefx\config\AmbienceFXBlockSoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */