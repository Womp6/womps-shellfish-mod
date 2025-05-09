package womp.shellfishmod.client.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrayfishEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class CrayfishModel extends EntityModel<ShellfishRenderState<CrayfishEntity.Variant>> {
	
	public CrayfishModel(ModelPart root) {
		super(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData crayfish = modelPartData.addChild("crayfish", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = crayfish.addChild("body", ModelPartBuilder.create().uv(0, 9).cuboid(-2.0F, -3.0F, -5.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F))
		.uv(10, 17).cuboid(-2.0F, -3.0F, 1.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(1, 1).cuboid(-1.5F, -2.25F, 2.0F, 3.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		tail.addChild("cube_r1", ModelPartBuilder.create().uv(1, 10).cuboid(-3.0F, -1.25F, 7.75F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(1, 9).cuboid(1.0F, -1.25F, 7.75F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(13, 1).cuboid(-4.0F, -1.25F, 8.75F, 8.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(13, 0).cuboid(-4.5F, -1.25F, 9.75F, 9.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.5F, 0.0F));

		ModelPartData claws = body.addChild("claws", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData claw1 = claws.addChild("claw1", ModelPartBuilder.create().uv(0, 24).cuboid(-5.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(14, 2).cuboid(-3.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(8, 21).cuboid(-4.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		claw1.addChild("cube_r6", ModelPartBuilder.create().uv(13, 6).cuboid(-7.0F, -1.57F, -2.0F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1447F, -0.6855F, -0.2262F));

		ModelPartData claw2 = claws.addChild("claw2", ModelPartBuilder.create().uv(10, 24).cuboid(4.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(14, 9).cuboid(2.0F, -1.0F, -8.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 21).cuboid(3.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		claw2.addChild("cube_r5", ModelPartBuilder.create().uv(13, 7).cuboid(3.0F, -1.57F, -2.0F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1447F, 0.6855F, 0.2262F));

		ModelPartData antennas = body.addChild("antennas", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData antenna1 = antennas.addChild("antenna1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		antenna1.addChild("cube_r7", ModelPartBuilder.create().uv(0, 0).cuboid(0.3F, -0.3F, -9.3F, 1.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2411F, -0.2821F, -1.5048F));

		ModelPartData antenna2 = antennas.addChild("antenna2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		antenna2.addChild("cube_r8", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -9.3F, 1.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2411F, 0.2821F, 1.6032F));

		ModelPartData legs = crayfish.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leg_1 = legs.addChild("leg_1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		leg_1.addChild("leg1", ModelPartBuilder.create().uv(0, 6).cuboid(-4.3F, -1.65F, -3.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData leg_2 = legs.addChild("leg_2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		leg_2.addChild("leg2", ModelPartBuilder.create().uv(0, 4).cuboid(-4.3F, -1.65F, -1.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData leg_3 = legs.addChild("leg_3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

		leg_3.addChild("leg3", ModelPartBuilder.create().uv(0, 5).cuboid(-4.3F, -1.65F, 1.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData leg_4 = legs.addChild("leg_4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		leg_4.addChild("leg4", ModelPartBuilder.create().uv(0, 7).cuboid(1.3F, -1.65F, -3.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		ModelPartData leg_5 = legs.addChild("leg_5", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		leg_5.addChild("leg5", ModelPartBuilder.create().uv(13, 13).cuboid(1.3F, -1.65F, -1.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.3491F));

		ModelPartData leg_6 = legs.addChild("leg_6", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

		leg_6.addChild("leg6", ModelPartBuilder.create().uv(13, 14).cuboid(1.3F, -1.65F, 1.0F, 3.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, 0.3491F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(ShellfishRenderState<CrayfishEntity.Variant> entity) {
		super.setAngles(entity);
		if (!entity.touchingWater) {
			this.animateWalking(MoreShellfishAnimations.CRAYFISH_WALK, entity.limbFrequency, entity.limbAmplitudeMultiplier, 12, 15f);
		}
		this.animate(entity.moveAnimationState, MoreShellfishAnimations.CRAYFISH_WALK, entity.age, 2f);
		this.animate(entity.idleAnimationState, MoreShellfishAnimations.CRAYFISH_IDLE, entity.age, 1f);
	}
}
