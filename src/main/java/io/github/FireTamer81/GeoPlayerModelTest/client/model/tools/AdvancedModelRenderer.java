package io.github.FireTamer81.GeoPlayerModelTest.client.model.tools;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.TestRenderUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancedModelRenderer extends ModelRenderer {
    private static final float MINIMUM_SCALE = 0.000001f;

    public float defaultRotationX, defaultRotationY, defaultRotationZ;
    public float defaultPositionX, defaultPositionY, defaultPositionZ;
    public float scaleX = 1.0F, scaleY = 1.0F, scaleZ = 1.0F;
    public float opacity = 1.0F;
    public boolean scaleChildren;
    private final Model model;
    private AdvancedModelRenderer parent;
    private boolean doubleSided = true;
    private boolean hasLighting = true;
    private boolean isHidden = false;

    public ObjectList<ModelPart> cubeList;
    public ObjectList<ModelRenderer> childModels;
    public int textureOffsetX, textureOffsetY;
    private float textureWidth;
    private float textureHeight;

    private Matrix3f mat3Override;
    private Matrix4f mat4Override;

    public AdvancedModelRenderer(Model model) {
        this(model, 0, 0);
    }

    public AdvancedModelRenderer(Model model, int textureOffsetX, int textureOffsetY) {
        super(model, textureOffsetX, textureOffsetY);
        this.model = model;
        this.textureWidth = model.texWidth;
        this.textureHeight = model.texHeight;
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        this.cubeList = new ObjectArrayList<>();
        this.childModels = new ObjectArrayList<>();
    }

    public AdvancedModelRenderer(AdvancedModelRenderer copyFrom) {
        this(copyFrom.getAdvancedModel(), copyFrom.textureOffsetX, copyFrom.textureOffsetY);
        //Was originally .xRot (Respective to the 3 letters)
        this.xRot = copyFrom.xRot;
        this.yRot = copyFrom.yRot;
        this.zRot = copyFrom.zRot;
        this.xRot = copyFrom.xRot;
        this.yRot = copyFrom.yRot;
        this.zRot = copyFrom.zRot;
        this.scaleX = copyFrom.scaleX;
        this.scaleY = copyFrom.scaleY;
        this.scaleZ = copyFrom.scaleZ;
        this.defaultPositionX = copyFrom.defaultPositionX;
        this.defaultPositionY = copyFrom.defaultPositionY;
        this.defaultPositionZ = copyFrom.defaultPositionZ;
        this.defaultRotationX = copyFrom.defaultRotationX;
        this.defaultRotationY = copyFrom.defaultRotationY;
        this.defaultRotationZ = copyFrom.defaultRotationZ;

        this.scaleChildren = copyFrom.scaleChildren;
        this.cubeList.addAll(copyFrom.cubeList);
        this.childModels = copyFrom.childModels;
    }

    @Override
    public ModelRenderer setTexSize(int textureWidthIn, int textureHeightIn) {
        textureWidth = textureWidthIn;
        textureHeight = textureHeightIn;
        return super.setTexSize(textureWidthIn, textureHeightIn);
    }

    @Override
    public ModelRenderer texOffs(int x, int y) {
        textureOffsetX = x;
        textureOffsetY = y;
        return super.texOffs(x, y);
    }

    /*public AdvancedModelRenderer add3DTexture(float posX, float posY, float posZ, int width, int height) {
        this.cubeList.add(new Model3DTexture(this, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, width, height));
        return this;
    }*/

    public AdvancedModelRenderer addBox(String partName, float x, float y, float z, int width, int height, int depth, float delta, int texX, int texY) {
        this.setTexSize(texX, texY);
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, (float)width, (float)height, (float)depth, delta, delta, delta, this.mirror, false);
        return this;
    }

    public AdvancedModelRenderer addBox(float x, float y, float z, float width, float height, float depth) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, this.mirror, false);
        return this;
    }

    public AdvancedModelRenderer addBox(float x, float y, float z, float width, float height, float depth, boolean mirrorIn) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, mirrorIn, false);
        return this;
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float delta) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, this.mirror, false);
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, this.mirror, false);
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirrorIn) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirrorIn, false);
    }

    private void addBox(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirror, boolean p_228305_13_) {
        this.cubeList.add(new AdvancedModelRenderer.ModelBox(texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirror, this.textureWidth, this.textureHeight));
    }

    /**
     * If true, when using setScale, the children of this model part will be scaled as well as just this part. If false, just this part will be scaled.
     *
     * @param scaleChildren true if this parent should scale the children
     * @since 1.1.0
     */
    public void setShouldScaleChildren(boolean scaleChildren) {
        this.scaleChildren = scaleChildren;
    }

    /**
     * Sets the scale for this AdvancedModelRenderer to be rendered at. (Performs a call to GLStateManager.scale()).
     *
     * @param scaleX the x scale
     * @param scaleY the y scale
     * @param scaleZ the z scale
     * @since 1.1.0
     */
    public void setScale(float scaleX, float scaleY, float scaleZ) {
        setScaleX(scaleX);
        setScaleY(scaleY);
        setScaleZ(scaleZ);
    }

    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
        setScaleZ(scale);
    }

    public void setScaleX(float scaleX) {
        this.scaleX = Math.max(MINIMUM_SCALE, scaleX);
    }

    public void setScaleY(float scaleY) {
        this.scaleY = Math.max(MINIMUM_SCALE, scaleY);
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = Math.max(MINIMUM_SCALE, scaleZ);
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void setHasLighting(boolean hasLighting) {
        this.hasLighting = hasLighting;
    }

    public void setDoubleSided(boolean doubleSided) {
        this.doubleSided = doubleSided;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public float getTextureWidth() {
        return textureWidth;
    }

    public float getTextureHeight() {
        return textureHeight;
    }

    /**
     * Sets this ModelRenderer's default pose to the current pose.
     */
    public void updateDefaultPose() {
        this.defaultRotationX = this.xRot;
        this.defaultRotationY = this.yRot;
        this.defaultRotationZ = this.zRot;

        this.defaultPositionX = this.xRot;
        this.defaultPositionY = this.yRot;
        this.defaultPositionZ = this.zRot;
    }

    /**
     * Sets the current pose to the previously set default pose.
     */
    public void resetToDefaultPose() {
        this.xRot = this.defaultRotationX;
        this.yRot = this.defaultRotationY;
        this.zRot = this.defaultRotationZ;

        this.xRot = this.defaultPositionX;
        this.yRot = this.defaultPositionY;
        this.zRot = this.defaultPositionZ;
    }

    @Override
    public void addChild(ModelRenderer renderer) {
        super.addChild(renderer);
        this.childModels.add(renderer);
        if (renderer instanceof AdvancedModelRenderer) {
            AdvancedModelRenderer advancedChild = (AdvancedModelRenderer) renderer;
            advancedChild.setParent(this);
        }
    }

    /**
     * @return the parent of this box
     */
    public AdvancedModelRenderer getParent() {
        return this.parent;
    }

    /**
     * Sets the parent of this box
     *
     * @param parent the new parent
     */
    public void setParent(AdvancedModelRenderer parent) {
        this.parent = parent;
    }


    @Override
    public void translateAndRotate(MatrixStack matrixStackIn) {
        AdvancedModelRenderer parent = getParent();
        if (parent != null && !parent.scaleChildren) {
            matrixStackIn.scale(1.f / parent.scaleX, 1.f / parent.scaleY, 1.f / parent.scaleZ);
        }
        super.translateAndRotate(matrixStackIn);
        matrixStackIn.scale(scaleX, scaleY, scaleZ);
    }

    // Copied from parent class
    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.visible) {
            if (!this.cubeList.isEmpty() || !this.childModels.isEmpty()) {
                matrixStackIn.pushPose();

                this.translateAndRotate(matrixStackIn);
                if (!isHidden) this.doRender(matrixStackIn.last(), bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha * opacity);

                // Render children
                for(ModelRenderer modelrenderer : this.childModels) {
                    modelrenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }

                matrixStackIn.popPose();
            }
        }
    }

    // Copied from parent class
    protected void doRender(MatrixStack.Entry matrixEntryIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrixEntryIn.pose();
        Matrix3f matrix3f = matrixEntryIn.normal();
        if (mat3Override != null) matrix3f = mat3Override;
        if (mat4Override != null) matrix4f = mat4Override;

        for(AdvancedModelRenderer.ModelPart modelrenderer$modelbox : this.cubeList) {
            modelrenderer$modelbox.render(matrix4f, matrix3f, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    public AdvancedModelBase getAdvancedModel() {
        return (AdvancedModelBase) this.model;
    }

    private float calculateRotation(float speed, float degree, boolean invert, float offset, float weight, float f, float f1) {
        float movementScale = this.model instanceof AdvancedModelBase ? ((AdvancedModelBase<?>)this.model).getMovementScale() : 1;
        float rotation = (MathHelper.cos(f * (speed * movementScale) + offset) * (degree * movementScale) * f1) + (weight * f1);
        return invert ? -rotation : rotation;
    }

    /**
     * Rotates this box back and forth (xRot). Useful for arms and legs.
     *
     * @param speed      is how fast the animation runs
     * @param degree     is how far the box will rotate;
     * @param invert     will invert the rotation
     * @param offset     will offset the timing of the animation
     * @param weight     will make the animation favor one direction more based on how fast the mob is moving
     * @param walk       is the walked distance
     * @param walkAmount is the walk speed
     */
    public void walk(float speed, float degree, boolean invert, float offset, float weight, float walk, float walkAmount) {
        this.xRot += this.calculateRotation(speed, degree, invert, offset, weight, walk, walkAmount);
    }

    /**
     * Rotates this box up and down (zRot). Useful for wing and ears.
     *
     * @param speed      is how fast the animation runs
     * @param degree     is how far the box will rotate;
     * @param invert     will invert the rotation
     * @param offset     will offset the timing of the animation
     * @param weight     will make the animation favor one direction more based on how fast the mob is moving
     * @param flap       is the flapped distance
     * @param flapAmount is the flap speed
     */
    public void flap(float speed, float degree, boolean invert, float offset, float weight, float flap, float flapAmount) {
        this.zRot += this.calculateRotation(speed, degree, invert, offset, weight, flap, flapAmount);
    }

    /**
     * Rotates this box side to side (yRot).
     *
     * @param speed       is how fast the animation runs
     * @param degree      is how far the box will rotate;
     * @param invert      will invert the rotation
     * @param offset      will offset the timing of the animation
     * @param weight      will make the animation favor one direction more based on how fast the mob is moving
     * @param swing       is the swung distance
     * @param swingAmount is the swing speed
     */
    public void swing(float speed, float degree, boolean invert, float offset, float weight, float swing, float swingAmount) {
        this.yRot += this.calculateRotation(speed, degree, invert, offset, weight, swing, swingAmount);
    }

    /**
     * Moves this box up and down (yRot). Useful for bodies.
     *
     * @param speed  is how fast the animation runs;
     * @param degree is how far the box will move;
     * @param bounce will make the box bounce;
     * @param f      is the walked distance;
     * @param f1     is the walk speed.
     */
    public void bob(float speed, float degree, boolean bounce, float f, float f1) {
        float movementScale = this.model instanceof AdvancedModelBase ? ((AdvancedModelBase<?>)this.model).getMovementScale() : 1;
        degree *= movementScale;
        speed *= movementScale;
        float bob = (float) (Math.sin(f * speed) * f1 * degree - f1 * degree);
        if (bounce) {
            bob = (float) -Math.abs((Math.sin(f * speed) * f1 * degree));
        }
        this.yRot += bob;
    }

    public void transitionTo(AdvancedModelRenderer to, float timer, float maxTime) {
        this.xRot += ((to.xRot - this.xRot) / maxTime) * timer;
        this.yRot += ((to.yRot - this.yRot) / maxTime) * timer;
        this.zRot += ((to.zRot - this.zRot) / maxTime) * timer;

        this.xRot += ((to.xRot - this.xRot) / maxTime) * timer;
        this.yRot += ((to.yRot - this.yRot) / maxTime) * timer;
        this.zRot += ((to.zRot - this.zRot) / maxTime) * timer;
    }

    public void setMatrixOverrides(Matrix3f mat3Override, Matrix4f mat4Override) {
        this.mat4Override = mat4Override;
        this.mat3Override = mat3Override;
    }

    public void clearMatrixOverrides() {
        mat3Override = null;
        mat4Override = null;
    }

    public void getMatrixStack(MatrixStack matrixStack) {
        AdvancedModelRenderer parent = getParent();
        if (parent != null) parent.getMatrixStack(matrixStack);
        translateAndRotate(matrixStack);
    }

    public Vector3d getWorldPos(Entity entity, float delta) {
        MatrixStack matrixStack = new MatrixStack();
        float dx = (float) (entity.xOld + (entity.getX() - entity.xOld) * delta);
        float dy = (float) (entity.yOld + (entity.getY() - entity.yOld) * delta);
        float dz = (float) (entity.zOld + (entity.getZ() - entity.zOld) * delta);
        matrixStack.translate(dx, dy, dz);
        float dYaw = MathHelper.rotLerp(delta, entity.yRotO, entity.yRot);
        matrixStack.mulPose(new Quaternion(0, -dYaw + 180, 0, true));
        matrixStack.scale(-1, -1, 1);
        matrixStack.translate(0, -1.5f, 0);
        TestRenderUtils.matrixStackFromModel(matrixStack, this);
        MatrixStack.Entry matrixEntry = matrixStack.last();
        Matrix4f matrix4f = matrixEntry.pose();

        Vector4f vec = new Vector4f(0, 0, 0, 1);
        vec.transform(matrix4f);
        return new Vector3d(vec.x(), vec.y(), vec.z());
    }

    public void setWorldPos(Entity entity, Vector3d worldPos, float delta) {
        MatrixStack matrixStack = new MatrixStack();
        float dx = (float) (entity.xOld + (entity.getX() - entity.xOld) * delta);
        float dy = (float) (entity.yOld + (entity.getY() - entity.yOld) * delta);
        float dz = (float) (entity.zOld + (entity.getZ() - entity.zOld) * delta);        matrixStack.translate(dx, dy, dz);
        float dYaw = MathHelper.rotLerp(delta, entity.yRotO, entity.yRot);
        matrixStack.mulPose(new Quaternion(0, -dYaw + 180, 0, true));
        matrixStack.scale(-1, -1, 1);
        matrixStack.translate(0, -1.5f, 0);
        MatrixStack.Entry matrixEntry = matrixStack.last();
        Matrix4f matrix4f = matrixEntry.pose();
        matrix4f.invert();

        Vector4f vec = new Vector4f((float) worldPos.x(), (float) worldPos.y(), (float) worldPos.z(), 1);
        vec.transform(matrix4f);
        xRot = vec.x() * 16;
        yRot = vec.y() * 16;
        zRot = vec.z() * 16;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract static class ModelPart {
        public void render(Matrix4f mat4, Matrix3f mat3, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        }
    }

    // From parent class. Copied to avoid using reflection to access private data
    @OnlyIn(Dist.CLIENT)
    public static class ModelBox extends ModelPart {
        protected final AdvancedModelRenderer.TexturedQuad[] quads;
        public final float posX1;
        public final float posY1;
        public final float posZ1;
        public final float posX2;
        public final float posY2;
        public final float posZ2;

        public ModelBox(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirror, float texWidth, float texHeight) {
            this.posX1 = x;
            this.posY1 = y;
            this.posZ1 = z;
            this.posX2 = x + width;
            this.posY2 = y + height;
            this.posZ2 = z + depth;
            this.quads = new AdvancedModelRenderer.TexturedQuad[6];
            float f = x + width;
            float f1 = y + height;
            float f2 = z + depth;
            x = x - deltaX;
            y = y - deltaY;
            z = z - deltaZ;
            f = f + deltaX;
            f1 = f1 + deltaY;
            f2 = f2 + deltaZ;
            if (mirror) {
                float f3 = f;
                f = x;
                x = f3;
            }

            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex7 = new AdvancedModelRenderer.PositionTextureVertex(x, y, z, 0.0F, 0.0F);
            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = new AdvancedModelRenderer.PositionTextureVertex(f, y, z, 0.0F, 8.0F);
            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex1 = new AdvancedModelRenderer.PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex2 = new AdvancedModelRenderer.PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex3 = new AdvancedModelRenderer.PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex4 = new AdvancedModelRenderer.PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex5 = new AdvancedModelRenderer.PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
            AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex6 = new AdvancedModelRenderer.PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
            float f4 = (float)texOffX;
            float f5 = (float)texOffX + depth;
            float f6 = (float)texOffX + depth + width;
            float f7 = (float)texOffX + depth + width + width;
            float f8 = (float)texOffX + depth + width + depth;
            float f9 = (float)texOffX + depth + width + depth + width;
            float f10 = (float)texOffY;
            float f11 = (float)texOffY + depth;
            float f12 = (float)texOffY + depth + height;
            this.quads[2] = new AdvancedModelRenderer.TexturedQuad(new AdvancedModelRenderer.PositionTextureVertex[]{modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex}, f5, f10, f6, f11, texWidth, texHeight, mirror, Direction.DOWN);
            this.quads[3] = new AdvancedModelRenderer.TexturedQuad(new AdvancedModelRenderer.PositionTextureVertex[]{modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex2, modelrenderer$positiontexturevertex6, modelrenderer$positiontexturevertex5}, f6, f11, f7, f10, texWidth, texHeight, mirror, Direction.UP);
            this.quads[1] = new AdvancedModelRenderer.TexturedQuad(new AdvancedModelRenderer.PositionTextureVertex[]{modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex6, modelrenderer$positiontexturevertex2}, f4, f11, f5, f12, texWidth, texHeight, mirror, Direction.WEST);
            this.quads[4] = new AdvancedModelRenderer.TexturedQuad(new AdvancedModelRenderer.PositionTextureVertex[]{modelrenderer$positiontexturevertex, modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex2, modelrenderer$positiontexturevertex1}, f5, f11, f6, f12, texWidth, texHeight, mirror, Direction.NORTH);
            this.quads[0] = new AdvancedModelRenderer.TexturedQuad(new AdvancedModelRenderer.PositionTextureVertex[]{modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex, modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex5}, f6, f11, f8, f12, texWidth, texHeight, mirror, Direction.EAST);
            this.quads[5] = new AdvancedModelRenderer.TexturedQuad(new AdvancedModelRenderer.PositionTextureVertex[]{modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex5, modelrenderer$positiontexturevertex6}, f8, f11, f9, f12, texWidth, texHeight, mirror, Direction.SOUTH);
        }

        @Override
        public void render(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
            for(AdvancedModelRenderer.TexturedQuad modelrenderer$texturedquad : quads) {
                Vector3f vector3f = modelrenderer$texturedquad.normal.copy();
                vector3f.transform(matrix3f);
                float f = vector3f.x();
                float f1 = vector3f.y();
                float f2 = vector3f.z();

                for(int i = 0; i < 4; ++i) {
                    AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
                    float f3 = modelrenderer$positiontexturevertex.position.x() / 16.0F;
                    float f4 = modelrenderer$positiontexturevertex.position.y() / 16.0F;
                    float f5 = modelrenderer$positiontexturevertex.position.z() / 16.0F;
                    Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
                    vector4f.transform(matrix4f);
                    bufferIn.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, modelrenderer$positiontexturevertex.textureU, modelrenderer$positiontexturevertex.textureV, packedOverlayIn, packedLightIn, f, f1, f2);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class PositionTextureVertex {
        public final Vector3f position;
        public final float textureU;
        public final float textureV;

        public PositionTextureVertex(float x, float y, float z, float texU, float texV) {
            this(new Vector3f(x, y, z), texU, texV);
        }

        public AdvancedModelRenderer.PositionTextureVertex setTextureUV(float texU, float texV) {
            return new AdvancedModelRenderer.PositionTextureVertex(this.position, texU, texV);
        }

        public PositionTextureVertex(Vector3f posIn, float texU, float texV) {
            this.position = posIn;
            this.textureU = texU;
            this.textureV = texV;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class TexturedQuad {
        public final AdvancedModelRenderer.PositionTextureVertex[] vertexPositions;
        public final Vector3f normal;

        public TexturedQuad(AdvancedModelRenderer.PositionTextureVertex[] positionsIn, float u1, float v1, float u2, float v2, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn) {
            this.vertexPositions = positionsIn;
            float f = 0.0F / texWidth;
            float f1 = 0.0F / texHeight;
            positionsIn[0] = positionsIn[0].setTextureUV(u2 / texWidth - f, v1 / texHeight + f1);
            positionsIn[1] = positionsIn[1].setTextureUV(u1 / texWidth + f, v1 / texHeight + f1);
            positionsIn[2] = positionsIn[2].setTextureUV(u1 / texWidth + f, v2 / texHeight - f1);
            positionsIn[3] = positionsIn[3].setTextureUV(u2 / texWidth - f, v2 / texHeight - f1);
            if (mirrorIn) {
                int i = positionsIn.length;

                for(int j = 0; j < i / 2; ++j) {
                    AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = positionsIn[j];
                    positionsIn[j] = positionsIn[i - 1 - j];
                    positionsIn[i - 1 - j] = modelrenderer$positiontexturevertex;
                }
            }

            this.normal = directionIn.step();
            if (mirrorIn) {
                this.normal.mul(-1.0F, 1.0F, 1.0F);
            }
        }
    }
}
