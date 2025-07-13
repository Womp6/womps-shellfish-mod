package womp.shellfishmod.client.model;

import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.AnimationDefinition;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import womp.shellfishmod.blocks.SeaLettuceBlockEntity;
import womp.shellfishmod.blocks.animations.BlockAnimations;
import womp.shellfishmod.util.config.ShellfishConfig;

public class SeaLettuceModel {

    public final ModelPart sea_lettuce;
	private final AnimationDefinition animation;

	private final List<String> boneNames = List.of(
    "leaf1", "leaf2", "leaf3", "leaf4", 
    "leaf5", "leaf6", "leaf7", "leaf8",
    "leaf9", "leaf10", "leaf11", "leaf12",
    "leaf13", "leaf14", "leaf15", "leaf16",
    "leaf17", "leaf18", "leaf19", "leaf20"
	);

    public SeaLettuceModel(ModelPart root) {
		this.sea_lettuce = root.getChild("sea_lettuce");
		this.animation = BlockAnimations.NATURAL;
    }
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sea_lettuce = modelPartData.addChild("sea_lettuce", ModelPartBuilder.create().uv(2, 16).cuboid(-0.5F, -0.2F, -0.5F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 15).cuboid(-1.0F, -0.1F, -1.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData leaf1 = sea_lettuce.addChild("leaf1", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf1.addChild("leaf1_r1", ModelPartBuilder.create().uv(12, 14).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		ModelPartData leaf2 = sea_lettuce.addChild("leaf2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf2.addChild("leaf2_r1", ModelPartBuilder.create().uv(6, 13).cuboid(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		ModelPartData leaf3 = sea_lettuce.addChild("leaf3", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf3.addChild("leaf3_r1", ModelPartBuilder.create().uv(4, 6).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData leaf4 = sea_lettuce.addChild("leaf4", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf4.addChild("leaf4_r1", ModelPartBuilder.create().uv(0, 6).cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData leaf5 = sea_lettuce.addChild("leaf5", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf5.addChild("leaf1_r2", ModelPartBuilder.create().uv(0, 13).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3655F, 0.7119F, 0.5299F));

		ModelPartData leaf6 = sea_lettuce.addChild("leaf6", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf6.addChild("leaf2_r2", ModelPartBuilder.create().uv(12, 12).cuboid(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3655F, -0.7119F, -0.5299F));

		ModelPartData leaf7 = sea_lettuce.addChild("leaf7", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf7.addChild("leaf3_r2", ModelPartBuilder.create().uv(12, 10).cuboid(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3655F, 0.7119F, -0.5299F));

		ModelPartData leaf8 = sea_lettuce.addChild("leaf8", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf8.addChild("leaf4_r2", ModelPartBuilder.create().uv(12, 8).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3655F, -0.7119F, 0.5299F));

		ModelPartData leaf9 = sea_lettuce.addChild("leaf9", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf9.addChild("leaf1_r3", ModelPartBuilder.create().uv(4, 3).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData leaf10 = sea_lettuce.addChild("leaf10", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf10.addChild("leaf2_r3", ModelPartBuilder.create().uv(4, 0).cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData leaf11 = sea_lettuce.addChild("leaf11", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf11.addChild("leaf3_r3", ModelPartBuilder.create().uv(6, 11).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		ModelPartData leaf12 = sea_lettuce.addChild("leaf12", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf12.addChild("leaf4_r3", ModelPartBuilder.create().uv(0, 11).cuboid(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		ModelPartData leaf13 = sea_lettuce.addChild("leaf13", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf13.addChild("leaf1_r4", ModelPartBuilder.create().uv(9, 6).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5713F, -0.5724F, 0.8706F));

		ModelPartData leaf14 = sea_lettuce.addChild("leaf14", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf14.addChild("leaf2_r4", ModelPartBuilder.create().uv(6, 9).cuboid(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5713F, 0.5724F, -0.8706F));

		ModelPartData leaf15 = sea_lettuce.addChild("leaf15", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf15.addChild("leaf3_r4", ModelPartBuilder.create().uv(9, 4).cuboid(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5713F, -0.5724F, -0.8706F));

		ModelPartData leaf16 = sea_lettuce.addChild("leaf16", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf16.addChild("leaf4_r4", ModelPartBuilder.create().uv(9, 2).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5713F, 0.5724F, 0.8706F));

		ModelPartData leaf17 = sea_lettuce.addChild("leaf17", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf17.addChild("leaf1_r5", ModelPartBuilder.create().uv(9, 0).cuboid(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

		ModelPartData leaf18 = sea_lettuce.addChild("leaf18", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf18.addChild("leaf2_r5", ModelPartBuilder.create().uv(0, 9).cuboid(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

		ModelPartData leaf19 = sea_lettuce.addChild("leaf19", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf19.addChild("leaf3_r5", ModelPartBuilder.create().uv(0, 3).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

		ModelPartData leaf20 = sea_lettuce.addChild("leaf20", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leaf20.addChild("leaf4_r5", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	public void applyBoneTransformation(String boneName, Vector3f rotation, MatrixStack matrices) {
        ModelPart bonePart = this.sea_lettuce.getChild(boneName);
        if (bonePart != null && rotation != null) {
			bonePart.pitch = rotation.x();
        	bonePart.yaw = rotation.y();
        	bonePart.roll = rotation.z();
        }
    }

	private void applyAnimationToModel(float animationTime, MatrixStack matrices) {
        for (Map.Entry<String, List<Transformation>> entry : this.animation.boneAnimations().entrySet()) {
            String boneName = entry.getKey();
            List<Transformation> transformations = entry.getValue();
            Transformation transformation = getCurrentTransformation(animationTime, transformations);
            if (transformation != null) {
                applyTransformationToModel(boneName, transformation, matrices);
            }
        }
    }

    private Transformation getCurrentTransformation(float animationTime, List<Transformation> transformations) {
        for (Transformation transformation : transformations) {
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

    private Transformation interpolateTransformation(Keyframe start, Keyframe end, float t) {
        Vector3f startRotation = start.target();
        Vector3f endRotation = end.target();
        Vector3f interpolatedRotation = new Vector3f(
                startRotation.x() + t * (endRotation.x() - startRotation.x()),
                startRotation.y() + t * (endRotation.y() - startRotation.y()),
                startRotation.z() + t * (endRotation.z() - startRotation.z())
        );
        return new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, interpolatedRotation, Transformation.Interpolations.LINEAR));
    }

    public void applyTransformationToModel(String boneName, Transformation transformation, MatrixStack matrices) {
        ModelPart bonePart = sea_lettuce.getChild(boneName);
        if (bonePart != null) {
            Vector3f rotation = transformation.keyframes()[0].target();
            applyBoneTransformation(boneName, rotation, matrices);
        }
    }

	public void render(SeaLettuceBlockEntity blockEntity, MatrixStack matrices, VertexConsumer var2, int var3, int var4, Vec3d vec) {
		resetTransformations();

		if(ShellfishConfig.getShellfishGraphics() == 2) {
			long currentTime = System.currentTimeMillis();
			float animationTime = ((currentTime - blockEntity.getAnimationStartTime()) / 1000.0f) % this.animation.lengthInSeconds();
        	applyAnimationToModel(animationTime, matrices);
		}

        sea_lettuce.render(matrices, var2, var3, var4);
    }

	public void resetTransformations() {
		for (String boneName : boneNames) {
			ModelPart bonePart = this.sea_lettuce.getChild(boneName);
			if (bonePart != null) {
				bonePart.pitch = 0.0F;
				bonePart.yaw = 0.0F;
				bonePart.roll = 0.0F;
			}
		}
	}
}