/*     */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ConnectedBlockFaceTags
/*     */ {
/*     */   public static final BuilderCodec<ConnectedBlockFaceTags> CODEC;
/*     */   
/*     */   static {
/* 105 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ConnectedBlockFaceTags.class, ConnectedBlockFaceTags::new).append(new KeyedCodec("North", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0]), false), (o, tags) -> { HashSet<String> strings = new HashSet<>(tags.length); strings.addAll(Arrays.asList(tags)); o.blockFaceTags.put(Vector3i.NORTH, strings); }o -> o.blockFaceTags.containsKey(Vector3i.NORTH) ? (String[])((HashSet)o.blockFaceTags.get(Vector3i.NORTH)).toArray(()) : new String[0]).add()).append(new KeyedCodec("East", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0]), false), (o, tags) -> { HashSet<String> strings = new HashSet<>(tags.length); strings.addAll(Arrays.asList(tags)); o.blockFaceTags.put(Vector3i.EAST, strings); }o -> o.blockFaceTags.containsKey(Vector3i.EAST) ? (String[])((HashSet)o.blockFaceTags.get(Vector3i.EAST)).toArray(()) : new String[0]).add()).append(new KeyedCodec("South", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0]), false), (o, tags) -> { HashSet<String> strings = new HashSet<>(tags.length); strings.addAll(Arrays.asList(tags)); o.blockFaceTags.put(Vector3i.SOUTH, strings); }o -> o.blockFaceTags.containsKey(Vector3i.SOUTH) ? (String[])((HashSet)o.blockFaceTags.get(Vector3i.SOUTH)).toArray(()) : new String[0]).add()).append(new KeyedCodec("West", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0]), false), (o, tags) -> { HashSet<String> strings = new HashSet<>(tags.length); strings.addAll(Arrays.asList(tags)); o.blockFaceTags.put(Vector3i.WEST, strings); }o -> o.blockFaceTags.containsKey(Vector3i.WEST) ? (String[])((HashSet)o.blockFaceTags.get(Vector3i.WEST)).toArray(()) : new String[0]).add()).append(new KeyedCodec("Up", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0]), false), (o, tags) -> { HashSet<String> strings = new HashSet<>(tags.length); strings.addAll(Arrays.asList(tags)); o.blockFaceTags.put(Vector3i.UP, strings); }o -> o.blockFaceTags.containsKey(Vector3i.UP) ? (String[])((HashSet)o.blockFaceTags.get(Vector3i.UP)).toArray(()) : new String[0]).add()).append(new KeyedCodec("Down", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0]), false), (o, tags) -> { HashSet<String> strings = new HashSet<>(tags.length); strings.addAll(Arrays.asList(tags)); o.blockFaceTags.put(Vector3i.DOWN, strings); }o -> o.blockFaceTags.containsKey(Vector3i.DOWN) ? (String[])((HashSet)o.blockFaceTags.get(Vector3i.DOWN)).toArray(()) : new String[0]).add()).build();
/*     */   }
/* 107 */   public static final ConnectedBlockFaceTags EMPTY = new ConnectedBlockFaceTags();
/*     */   @Nonnull
/* 109 */   private final Map<Vector3i, HashSet<String>> blockFaceTags = (Map<Vector3i, HashSet<String>>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */   
/*     */   public boolean contains(Vector3i direction, String blockFaceTag) {
/* 113 */     return (this.blockFaceTags.containsKey(direction) && ((HashSet)this.blockFaceTags.get(direction)).contains(blockFaceTag));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<Vector3i, HashSet<String>> getBlockFaceTags() {
/* 118 */     return this.blockFaceTags;
/*     */   }
/*     */   
/*     */   public Set<String> getBlockFaceTags(Vector3i direction) {
/* 122 */     if (this.blockFaceTags.containsKey(direction)) {
/* 123 */       return this.blockFaceTags.get(direction);
/*     */     }
/* 125 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<Vector3i> getDirections() {
/* 130 */     return this.blockFaceTags.keySet();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\ConnectedBlockFaceTags.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */