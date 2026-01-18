/*    */ package com.hypixel.hytale.builtin.asseteditor.data;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.common.util.PathUtil;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetInfo;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetPath;
/*    */ import java.nio.file.Path;
/*    */ import java.time.Instant;
/*    */ import java.util.UUID;
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
/*    */ public class ModifiedAsset
/*    */ {
/*    */   public static final BuilderCodec<ModifiedAsset> CODEC;
/*    */   @Nullable
/*    */   public Path dataFile;
/*    */   public Path path;
/*    */   @Nullable
/*    */   public Path oldPath;
/*    */   
/*    */   static {
/* 67 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModifiedAsset.class, ModifiedAsset::new).append(new KeyedCodec("File", (Codec)Codec.PATH), (asset, s) -> asset.dataFile = s, asset -> asset.dataFile).add()).append(new KeyedCodec("Path", (Codec)Codec.PATH), (asset, s) -> asset.path = s, asset -> asset.path).add()).append(new KeyedCodec("OldPath", (Codec)Codec.PATH), (asset, s) -> asset.oldPath = s, asset -> asset.oldPath).add()).append(new KeyedCodec("IsNew", (Codec)Codec.BOOLEAN), (asset, s) -> asset.state = s.booleanValue() ? AssetState.NEW : asset.state, asset -> null).add()).append(new KeyedCodec("IsDeleted", (Codec)Codec.BOOLEAN), (asset, s) -> asset.state = s.booleanValue() ? AssetState.DELETED : asset.state, asset -> null).add()).append(new KeyedCodec("State", (Codec)new EnumCodec(AssetState.class)), (asset, s) -> asset.state = s, asset -> asset.state).add()).append(new KeyedCodec("LastModificationTimestamp", (Codec)Codec.INSTANT, true), (asset, s) -> asset.lastModificationTimestamp = s, asset -> asset.lastModificationTimestamp).add()).append(new KeyedCodec("LastModificationPlayerUuid", (Codec)Codec.UUID_STRING, true), (asset, s) -> asset.lastModificationPlayerUuid = s, asset -> asset.lastModificationPlayerUuid).add()).append(new KeyedCodec("LastModificationUsername", (Codec)Codec.STRING, true), (asset, s) -> asset.lastModificationUsername = s, asset -> asset.lastModificationUsername).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   public AssetState state = AssetState.CHANGED;
/*    */   public Instant lastModificationTimestamp;
/*    */   public UUID lastModificationPlayerUuid;
/*    */   public String lastModificationUsername;
/*    */   
/*    */   public void markEditedBy(@Nonnull EditorClient editorClient) {
/* 80 */     this.lastModificationTimestamp = Instant.now();
/* 81 */     this.lastModificationUsername = editorClient.getUsername();
/* 82 */     this.lastModificationPlayerUuid = editorClient.getUuid();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public AssetInfo toAssetInfoPacket(String assetPack) {
/* 87 */     return new AssetInfo(new AssetPath(assetPack, 
/* 88 */           PathUtil.toUnixPathString(this.path)), 
/* 89 */         (this.oldPath != null) ? new AssetPath(assetPack, PathUtil.toUnixPathString(this.oldPath)) : null, (this.state == AssetState.DELETED), (this.state == AssetState.NEW), this.lastModificationTimestamp
/*    */ 
/*    */         
/* 92 */         .toEpochMilli(), this.lastModificationUsername);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\data\ModifiedAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */