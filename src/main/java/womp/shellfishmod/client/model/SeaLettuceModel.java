package womp.shellfishmod.client.model;

import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.blocks.animations.BlockAnimations;
import womp.shellfishmod.util.config.ShellfishConfig;

public class SeaLettuceModel {

    public final ModelPart sea_lettuce;
    private final AnimationDefinition animation;

    public SeaLettuceModel(ModelPart root) {
        this.sea_lettuce = root.getChild("sea_lettuce");
        this.animation = BlockAnimations.NATURAL;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition sea_lettuce = modelPartData.addOrReplaceChild("sea_lettuce", CubeListBuilder.create().texOffs(2, 16).addBox(-0.5F, -0.2F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-1.0F, -0.1F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leaf1 = sea_lettuce.addOrReplaceChild("leaf1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf1.addOrReplaceChild("leaf1_r1", CubeListBuilder.create().texOffs(12, 14).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition leaf2 = sea_lettuce.addOrReplaceChild("leaf2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf2.addOrReplaceChild("leaf2_r1", CubeListBuilder.create().texOffs(6, 13).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition leaf3 = sea_lettuce.addOrReplaceChild("leaf3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf3.addOrReplaceChild("leaf3_r1", CubeListBuilder.create().texOffs(4, 6).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition leaf4 = sea_lettuce.addOrReplaceChild("leaf4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf4.addOrReplaceChild("leaf4_r1", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition leaf5 = sea_lettuce.addOrReplaceChild("leaf5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf5.addOrReplaceChild("leaf1_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3655F, 0.7119F, 0.5299F));

        PartDefinition leaf6 = sea_lettuce.addOrReplaceChild("leaf6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf6.addOrReplaceChild("leaf2_r2", CubeListBuilder.create().texOffs(12, 12).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3655F, -0.7119F, -0.5299F));

        PartDefinition leaf7 = sea_lettuce.addOrReplaceChild("leaf7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf7.addOrReplaceChild("leaf3_r2", CubeListBuilder.create().texOffs(12, 10).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3655F, 0.7119F, -0.5299F));

        PartDefinition leaf8 = sea_lettuce.addOrReplaceChild("leaf8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf8.addOrReplaceChild("leaf4_r2", CubeListBuilder.create().texOffs(12, 8).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3655F, -0.7119F, 0.5299F));

        PartDefinition leaf9 = sea_lettuce.addOrReplaceChild("leaf9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf9.addOrReplaceChild("leaf1_r3", CubeListBuilder.create().texOffs(4, 3).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition leaf10 = sea_lettuce.addOrReplaceChild("leaf10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf10.addOrReplaceChild("leaf2_r3", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition leaf11 = sea_lettuce.addOrReplaceChild("leaf11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf11.addOrReplaceChild("leaf3_r3", CubeListBuilder.create().texOffs(6, 11).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition leaf12 = sea_lettuce.addOrReplaceChild("leaf12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf12.addOrReplaceChild("leaf4_r3", CubeListBuilder.create().texOffs(0, 11).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leaf13 = sea_lettuce.addOrReplaceChild("leaf13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf13.addOrReplaceChild("leaf1_r4", CubeListBuilder.create().texOffs(9, 6).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5713F, -0.5724F, 0.8706F));

        PartDefinition leaf14 = sea_lettuce.addOrReplaceChild("leaf14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf14.addOrReplaceChild("leaf2_r4", CubeListBuilder.create().texOffs(6, 9).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5713F, 0.5724F, -0.8706F));

        PartDefinition leaf15 = sea_lettuce.addOrReplaceChild("leaf15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf15.addOrReplaceChild("leaf3_r4", CubeListBuilder.create().texOffs(9, 4).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5713F, -0.5724F, -0.8706F));

        PartDefinition leaf16 = sea_lettuce.addOrReplaceChild("leaf16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf16.addOrReplaceChild("leaf4_r4", CubeListBuilder.create().texOffs(9, 2).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5713F, 0.5724F, 0.8706F));

        PartDefinition leaf17 = sea_lettuce.addOrReplaceChild("leaf17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf17.addOrReplaceChild("leaf1_r5", CubeListBuilder.create().texOffs(9, 0).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition leaf18 = sea_lettuce.addOrReplaceChild("leaf18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf18.addOrReplaceChild("leaf2_r5", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

        PartDefinition leaf19 = sea_lettuce.addOrReplaceChild("leaf19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf19.addOrReplaceChild("leaf3_r5", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition leaf20 = sea_lettuce.addOrReplaceChild("leaf20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        leaf20.addOrReplaceChild("leaf4_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    public void applyBoneTransformation(String boneName, Vector3f rotation, PoseStack matrices) {
        ModelPart bonePart = this.sea_lettuce.getChild(boneName);
        if (bonePart != null && rotation != null) {
            bonePart.xRot = rotation.x();
            bonePart.yRot = rotation.y();
            bonePart.zRot = rotation.z();
        }
    }

    private void applyAnimationToModel(float animationTime, PoseStack matrices) {
        for (Map.Entry<String, List<AnimationChannel>> entry : this.animation.boneAnimations().entrySet()) {
            String boneName = entry.getKey();
            List<AnimationChannel> transformations = entry.getValue();
            AnimationChannel transformation = getCurrentTransformation(animationTime, transformations);
            if (transformation != null) {
                applyTransformationToModel(boneName, transformation, matrices);
            }
        }
    }

    private AnimationChannel getCurrentTransformation(float animationTime, List<AnimationChannel> transformations) {
        for (AnimationChannel transformation : transformations) {
            Keyframe[] keyframes = transformation.keyframes();
            for (int i = 0; i < keyframes.length - 1; i++) {
                Keyframe current = keyframes[i];
                Keyframe next = keyframes[i + 1];
                if (current.timestamp() <= animationTime && animationTime <= next.timestamp()) {
                    float t = (animationTime - current.timestamp()) / (next.timestamp() - current.timestamp());
                    return interpolateTransformation(current, next, t);
                }
            }
        }
        return null;
    }

    private AnimationChannel interpolateTransformation(Keyframe start, Keyframe end, float t) {
        Vector3f startRotation = start.target();
        Vector3f endRotation = end.target();
        Vector3f interpolatedRotation = new Vector3f(
                startRotation.x() + t * (endRotation.x() - startRotation.x()),
                startRotation.y() + t * (endRotation.y() - startRotation.y()),
                startRotation.z() + t * (endRotation.z() - startRotation.z())
        );
        return new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, interpolatedRotation, AnimationChannel.Interpolations.LINEAR));
    }

    public void applyTransformationToModel(String boneName, AnimationChannel transformation, PoseStack matrices) {
        ModelPart bonePart = sea_lettuce.getChild(boneName);
        if (bonePart != null) {
            Vector3f rotation = transformation.keyframes()[0].target();
            applyBoneTransformation(boneName, rotation, matrices);
        }
    }

    public void render(SeaLettuceBlockEntity blockEntity, PoseStack matrices, VertexConsumer var2, int var3, int var4, Vec3 vec) {
        resetTransformations();

        if(ShellfishConfig.getShellfishGraphics() == 2) {
            long currentTime = System.currentTimeMillis();
            float animationTime = ((currentTime - blockEntity.getAnimationStartTime()) / 1000.0f) % this.animation.lengthInSeconds();
            applyAnimationToModel(animationTime, matrices);
        }

        sea_lettuce.render(matrices, var2, var3, var4);
    }

    private final List<String> boneNames = List.of(
            "leaf1", "leaf2", "leaf3", "leaf4",
            "leaf5", "leaf6", "leaf7", "leaf8",
            "leaf9", "leaf10", "leaf11", "leaf12",
            "leaf13", "leaf14", "leaf15", "leaf16",
            "leaf17", "leaf18", "leaf19", "leaf20"
    );

    public void resetTransformations() {
        for (String boneName : boneNames) {
            ModelPart bonePart = this.sea_lettuce.getChild(boneName);
            if (bonePart != null) {
                bonePart.xRot = 0.0F;
                bonePart.yRot = 0.0F;
                bonePart.zRot = 0.0F;
            }
        }
    }
}
