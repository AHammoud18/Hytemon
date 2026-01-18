/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.hypixel.hytale.server.npc.instructions.Instruction;
/*     */ import com.hypixel.hytale.server.npc.instructions.builders.BuilderInstructionReference;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InternalReferenceResolver {
/*  20 */   private final List<BuilderInstructionReference> builders = (List<BuilderInstructionReference>)new ObjectArrayList();
/*     */   
/*     */   @Nullable
/*  23 */   private Int2ObjectMap<String> nameMap = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/*     */   @Nullable
/*     */   private Object2IntMap<String> indexMap;
/*     */   @Nullable
/*     */   private IntSet recordedDependencies;
/*     */   
/*     */   public InternalReferenceResolver() {
/*  30 */     this.indexMap = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*  31 */     this.indexMap.defaultReturnValue(-2147483648);
/*     */   }
/*     */   
/*     */   public int getOrCreateIndex(String name) {
/*  35 */     int index = this.indexMap.getInt(name);
/*  36 */     if (index == Integer.MIN_VALUE) {
/*  37 */       index = this.builders.size();
/*  38 */       this.indexMap.put(name, index);
/*  39 */       this.nameMap.put(index, name);
/*  40 */       this.builders.add(null);
/*     */     } 
/*  42 */     if (this.recordedDependencies != null) this.recordedDependencies.add(index); 
/*  43 */     return index;
/*     */   }
/*     */   
/*     */   public void setRecordDependencies() {
/*  47 */     this.recordedDependencies = (IntSet)new IntOpenHashSet();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IntSet getRecordedDependenices() {
/*  52 */     return this.recordedDependencies;
/*     */   }
/*     */   
/*     */   public void stopRecordingDependencies() {
/*  56 */     this.recordedDependencies = null;
/*     */   }
/*     */   
/*     */   public void addBuilder(int index, BuilderInstructionReference builder) {
/*  60 */     Objects.requireNonNull(builder, "Builder cannot be null when adding as a reference");
/*  61 */     if (index < 0 || index >= this.builders.size()) throw new IllegalArgumentException("Slot for putting builder must be >= 0 and < the size of the list"); 
/*  62 */     if (this.builders.get(index) != null)
/*  63 */       throw new IllegalStateException(String.format("Duplicate internal reference builder with name: %s", new Object[] { this.nameMap.get(index) })); 
/*  64 */     this.builders.set(index, builder);
/*     */   }
/*     */   
/*     */   public void validateInternalReferences(String configName, @Nonnull List<String> errors) {
/*  68 */     for (int i = 0; i < this.builders.size(); i++) {
/*  69 */       BuilderInstructionReference builder = this.builders.get(i);
/*  70 */       if (builder == null) {
/*  71 */         errors.add(configName + ": Internal reference builder: " + configName + " doesn't exist");
/*     */       } else {
/*     */         
/*     */         try {
/*  75 */           validateNoCycles(builder, i, new IntArrayList());
/*  76 */         } catch (IllegalArgumentException e) {
/*  77 */           errors.add(configName + ": " + configName);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void validateNoCycles(@Nonnull BuilderInstructionReference builder, int index, @Nonnull IntArrayList path) {
/*  83 */     if (path.contains(index)) {
/*  84 */       throw new IllegalArgumentException("Cyclic reference detected for internal component reference: " + (String)this.nameMap.get(index));
/*     */     }
/*  86 */     path.add(index);
/*  87 */     for (IntIterator i = builder.getInternalDependencies().iterator(); i.hasNext(); ) {
/*  88 */       int dependency = i.nextInt();
/*  89 */       BuilderInstructionReference nextBuilder = this.builders.get(dependency);
/*  90 */       if (nextBuilder == null)
/*  91 */         throw new IllegalStateException("Reference to internal reference builder: " + (String)this.nameMap.get(dependency) + " which doesn't exist"); 
/*  92 */       validateNoCycles(nextBuilder, dependency, path);
/*     */     } 
/*  94 */     path.removeInt(path.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Builder<T> getBuilder(int index, Class<?> classType) {
/*  99 */     if (classType != Instruction.class) throw new IllegalArgumentException("Internal references are currently only supported for instruction list"); 
/* 100 */     return (Builder<T>)this.builders.get(index);
/*     */   }
/*     */   
/*     */   public void optimise() {
/* 104 */     this.indexMap = null;
/* 105 */     this.nameMap = null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\InternalReferenceResolver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */