/*    */ package com.hypixel.hytale.server.flock;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.store.StoredCodec;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StoredFlock
/*    */ {
/*    */   public static final BuilderCodec<StoredFlock> CODEC;
/*    */   @Nullable
/*    */   private Holder<EntityStore>[] members;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(StoredFlock.class, StoredFlock::new).append(new KeyedCodec("Members", (Codec)new ArrayCodec((Codec)new StoredCodec(EntityStore.HOLDER_CODEC_KEY), x$0 -> new Holder[x$0])), (flock, array) -> flock.members = (Holder<EntityStore>[])array, flock -> flock.members).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeNPCs(@Nonnull List<Ref<EntityStore>> refs, @Nonnull Store<EntityStore> store) {
/* 39 */     ComponentRegistry.Data<EntityStore> data = EntityStore.REGISTRY.getData();
/*    */     
/* 41 */     ObjectArrayList<Holder<EntityStore>> members = new ObjectArrayList();
/* 42 */     for (int i = 0; i < refs.size(); i++) {
/* 43 */       Ref<EntityStore> ref = refs.get(i);
/* 44 */       if (ref.isValid()) {
/*    */         
/* 46 */         Holder<EntityStore> holder = store.removeEntity(ref, RemoveReason.UNLOAD);
/*    */         
/* 48 */         if (holder.hasSerializableComponents(data))
/* 49 */           members.add(holder); 
/*    */       } 
/*    */     } 
/* 52 */     this.members = (Holder<EntityStore>[])members.toArray(x$0 -> new Holder[x$0]);
/*    */   }
/*    */   
/*    */   public boolean hasStoredNPCs() {
/* 56 */     return (this.members != null);
/*    */   }
/*    */   
/*    */   public void restoreNPCs(@Nonnull List<Ref<EntityStore>> output, @Nonnull Store<EntityStore> store) {
/* 60 */     for (Holder<EntityStore> member : this.members) {
/* 61 */       Ref<EntityStore> ref = store.addEntity(member, AddReason.LOAD);
/* 62 */       if (ref == null) {
/* 63 */         SpawningPlugin.get().getLogger().at(Level.WARNING).log("Failed to restore stored spawn marker member! " + String.valueOf(member));
/*    */       } else {
/*    */         
/* 66 */         output.add(ref);
/*    */       } 
/* 68 */     }  clear();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 72 */     this.members = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 79 */     return "StoredFlock{, members=" + Arrays.toString((Object[])this.members) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public StoredFlock clone() {
/* 87 */     StoredFlock storedFlock = new StoredFlock();
/* 88 */     if (this.members != null) {
/* 89 */       Holder[] arrayOfHolder = new Holder[this.members.length];
/* 90 */       for (int i = 0; i < arrayOfHolder.length; i++) {
/* 91 */         arrayOfHolder[i] = this.members[i].clone();
/*    */       }
/* 93 */       storedFlock.members = (Holder<EntityStore>[])arrayOfHolder;
/*    */     } 
/* 95 */     return storedFlock;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\StoredFlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */