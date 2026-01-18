/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.SolidMaterial;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NVoxelBufferView;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class NTestPropStage
/*     */   implements NStage
/*     */ {
/*  21 */   private static final Class<NVoxelBuffer> bufferClass = NVoxelBuffer.class;
/*  22 */   private static final Class<SolidMaterial> solidMaterialClass = SolidMaterial.class;
/*     */   
/*  24 */   private final int CONTEXT_DEPENDENCY_RANGE_BUFFER_GRID = 0;
/*  25 */   private final Bounds3i inputBounds_bufferGrid = new Bounds3i(new Vector3i(0, 0, 0), new Vector3i(1, 40, 1));
/*     */ 
/*     */   
/*     */   private final NParametrizedBufferType inputBufferType;
/*     */ 
/*     */   
/*     */   private final NParametrizedBufferType outputBufferType;
/*     */   
/*     */   private final SolidMaterial floorMaterial;
/*     */   
/*     */   private final SolidMaterial anchorMaterial;
/*     */   
/*     */   private final SolidMaterial propMaterial;
/*     */ 
/*     */   
/*     */   public NTestPropStage(@Nonnull NBufferType inputBufferType, @Nonnull NBufferType outputBufferType, @Nonnull SolidMaterial floorMaterial, @Nonnull SolidMaterial anchorMaterial, @Nonnull SolidMaterial propMaterial) {
/*  41 */     assert inputBufferType instanceof NParametrizedBufferType;
/*  42 */     assert outputBufferType instanceof NParametrizedBufferType;
/*     */     
/*  44 */     this.inputBufferType = (NParametrizedBufferType)inputBufferType;
/*  45 */     this.outputBufferType = (NParametrizedBufferType)outputBufferType;
/*  46 */     assert this.outputBufferType.isValidType(bufferClass, solidMaterialClass);
/*     */     
/*  48 */     this.floorMaterial = floorMaterial;
/*  49 */     this.anchorMaterial = anchorMaterial;
/*  50 */     this.propMaterial = propMaterial;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(@Nonnull NStage.Context context) {
/*  55 */     NBufferBundle.Access.View inputAccess = context.bufferAccess.get(this.inputBufferType);
/*  56 */     NVoxelBufferView<SolidMaterial> inputView = new NVoxelBufferView(inputAccess, solidMaterialClass);
/*     */     
/*  58 */     NBufferBundle.Access.View outputAccess = context.bufferAccess.get(this.outputBufferType);
/*  59 */     NVoxelBufferView<SolidMaterial> outputView = new NVoxelBufferView(outputAccess, solidMaterialClass);
/*     */     
/*  61 */     outputView.copyFrom(inputView);
/*     */     
/*  63 */     Vector3i scanPosition = new Vector3i(0, 316, 0);
/*     */ 
/*     */     
/*  66 */     Random rand = new Random(Objects.hash(new Object[] { Integer.valueOf(outputView.minX() * 1000), Integer.valueOf(outputView.minZ()) }));
/*  67 */     scanPosition.setX(rand.nextInt(NVoxelBuffer.SIZE.x) + outputView.minX());
/*  68 */     scanPosition.setZ(rand.nextInt(NVoxelBuffer.SIZE.z) + outputView.minZ());
/*     */     
/*  70 */     for (; scanPosition.y >= 10; scanPosition.setY(scanPosition.y - 1)) {
/*  71 */       SolidMaterial floor = (SolidMaterial)inputView.getContent(scanPosition.clone().add(0, -1, 0));
/*  72 */       SolidMaterial anchor = (SolidMaterial)inputView.getContent(scanPosition);
/*     */       
/*  74 */       if (this.floorMaterial.equals(floor) && this.anchorMaterial.equals(anchor)) {
/*  75 */         placeProp(scanPosition, outputView);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeProp(@Nonnull Vector3i position, @Nonnull NVoxelBufferView<SolidMaterial> view) {
/*  81 */     int height = 5;
/*  82 */     Vector3i placePosition = position.clone();
/*  83 */     for (int i = 0; i < 5; i++) {
/*  84 */       view.set(this.propMaterial, placePosition);
/*  85 */       placePosition.setY(placePosition.getY() + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid() {
/*  92 */     return (Map)Map.of(this.inputBufferType, this.inputBounds_bufferGrid.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<NBufferType> getOutputTypes() {
/*  98 */     return (List)List.of(this.outputBufferType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/* 104 */     return "TestPropStage";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NTestPropStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */