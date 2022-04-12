package io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.RigUtils;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class GeckoTestBone extends GeoBone {
    private Matrix4f modelSpaceXForm;
    private boolean trackXForm;
    public Matrix4f rotMatrix;

    private Matrix4f worldSpaceXForm;
    private Matrix3f worldSpaceNormal;

    public GeckoTestBone() {
        super();
        modelSpaceXForm = new Matrix4f();
        modelSpaceXForm.setIdentity();

        trackXForm = false;
        rotMatrix = null;

        worldSpaceXForm = new Matrix4f();
        worldSpaceXForm.setIdentity();

        worldSpaceNormal = new Matrix3f();
        worldSpaceNormal.setIdentity();
    }

    public GeckoTestBone getParent() {
        return (GeckoTestBone) parent;
    }

    public boolean isTrackingXForm() {
        return trackXForm;
    }

    public void setTrackXForm(boolean newTrackXForm) {
        this.trackXForm = newTrackXForm;
    }

    public Matrix4f getModelSpaceXForm() {
        setTrackXForm(true);
        return modelSpaceXForm;
    }

    public Vector3d getModelPosition() {
        Matrix4f matrix = getModelSpaceXForm();
        Vector4f vec = new Vector4f(0, 0, 0, 1);
        vec.transform(matrix);
        return new Vector3d(-vec.x() * 16f, vec.y() * 16f, vec.z() * 16f);
    }

    public void setModelPosition(Vector3d pos) {
        GeckoTestBone parent = getParent();
        Matrix4f identity = new Matrix4f();
        identity.setIdentity();
        Matrix4f matrix = parent == null ? identity : parent.getModelSpaceXForm().copy();
        matrix.invert();
        Vector4f vec = new Vector4f(-(float) pos.x() / 16f, (float) pos.y() / 16f, (float) pos.z() / 16f, 1);
        vec.transform(matrix);
        setPosition(-vec.x() * 16f, vec.y() * 16f, vec.z() * 16f);
    }

    public Matrix4f getModelRotationMat() {
        Matrix4f matrix = getModelSpaceXForm().copy();
        RigUtils.removeMatrixTranslation(matrix);
        return matrix;
    }

    public void setModelRotationMat(Matrix4f mat) {
        rotMatrix = mat;
    }

    public void setWorldSpaceNormal(Matrix3f worldSpaceNormal) {
        this.worldSpaceNormal = worldSpaceNormal;
    }

    public Matrix3f getWorldSpaceNormal() {
        return worldSpaceNormal;
    }

    public void setWorldSpaceXForm(Matrix4f worldSpaceXForm) {
        this.worldSpaceXForm = worldSpaceXForm;
    }

    public Matrix4f getWorldSpaceXForm() {
        return worldSpaceXForm;
    }



    /******************************************************************************************************************/
    // Position utils
    /******************************************************************************************************************/

    public void addPosition(Vector3d vec) {
        addPosition((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void addPosition(float x, float y, float z) {
        addPositionX(x);
        addPositionY(y);
        addPositionZ(z);
    }

    public void addPositionX(float x) {
        setPositionX(getPositionX() + x);
    }

    public void addPositionY(float y) {
        setPositionY(getPositionY() + y);
    }

    public void addPositionZ(float z) {
        setPositionZ(getPositionZ() + z);
    }

    public void setPosition(Vector3d vec) {
        setPosition((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void setPosition(float x, float y, float z) {
        setPositionX(x);
        setPositionY(y);
        setPositionZ(z);
    }

    public Vector3d getPosition() {
        return new Vector3d(getPositionX(), getPositionY(), getPositionZ());
    }

    // Rotation utils
    public void addRotation(Vector3d vec) {
        addRotation((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void addRotation(float x, float y, float z) {
        addRotationX(x);
        addRotationY(y);
        addRotationZ(z);
    }

    public void addRotationX(float x) {
        setRotationX(getRotationX() + x);
    }

    public void addRotationY(float y) {
        setRotationY(getRotationY() + y);
    }

    public void addRotationZ(float z) {
        setRotationZ(getRotationZ() + z);
    }

    public void setRotation(Vector3d vec) {
        setRotation((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void setRotation(float x, float y, float z) {
        setRotationX(x);
        setRotationY(y);
        setRotationZ(z);
    }

    public Vector3d getRotation() {
        return new Vector3d(getRotationX(), getRotationY(), getRotationZ());
    }

    // Scale utils
    public void multiplyScale(Vector3d vec) {
        multiplyScale((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void multiplyScale(float x, float y, float z) {
        setScaleX(getScaleX() * x);
        setScaleY(getScaleY() * y);
        setScaleZ(getScaleZ() * z);
    }

    public void setScale(Vector3d vec) {
        setScale((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void setScale(float x, float y, float z) {
        setScaleX(x);
        setScaleY(y);
        setScaleZ(z);
    }

    public Vector3d getScale() {
        return new Vector3d(getScaleX(), getScaleY(), getScaleZ());
    }

    public void addRotationOffsetFromBone(GeckoTestBone source) {
        setRotationX(getRotationX() + source.getRotationX() - source.getInitialSnapshot().rotationValueX);
        setRotationY(getRotationY() + source.getRotationY() - source.getInitialSnapshot().rotationValueY);
        setRotationZ(getRotationZ() + source.getRotationZ() - source.getInitialSnapshot().rotationValueZ);
    }








}
