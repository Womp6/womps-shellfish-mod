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
import womp.shellfishmod.entity.LobsterEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

// The new model (1.2+) was revised with help from underapreciatedpigeon
public class LobsterModel extends EntityModel<ShellfishRenderState<LobsterEntity.Variant>> {
	
	public LobsterModel(ModelPart root) {
		super(root);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData lobster = modelPartData.addChild("lobster", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData body = lobster.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -2.0F));

		body.addChild("bodybody", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -2.75F, -2.0F, 7.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(18, 9).cuboid(-1.0F, -2.75F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		tail.addChild("tailtail", ModelPartBuilder.create().uv(0, 7).cuboid(-7.0F, -2.0F, -1.5F, 6.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

		tail.addChild("tailend", ModelPartBuilder.create().uv(14, 21).cuboid(-10.0F, -1.5F, -3.0F, 3.0F, 0.0F, 6.0F, new Dilation(0.0F))
		.uv(16, 25).cuboid(-7.0F, -1.5F, -2.5F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 23).cuboid(-7.0F, -1.5F, 1.5F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData claw = body.addChild("claw", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData claw1 = claw.addChild("claw1", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 0.0F, 0.0F));

		claw1.addChild("claw5_r1", ModelPartBuilder.create().uv(5, 27).cuboid(1.3F, -0.8F, 3.0F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -0.75F, -2.5F, 0.0F, 0.8279F, 0.1309F));

		claw1.addChild("claw5_r2", ModelPartBuilder.create().uv(23, 2).cuboid(8.0F, -0.56F, -0.25F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(27, 7).cuboid(7.0F, -0.56F, -1.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 15).cuboid(7.0F, -0.56F, -3.25F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -0.75F, -0.5F, 0.0F, 0.2182F, 0.0F));

		ModelPartData claw2 = claw.addChild("claw2", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 0.0F, 0.0F));

		claw2.addChild("claw8_r1", ModelPartBuilder.create().uv(23, 0).cuboid(8.0F, -0.56F, 0.25F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(28, 4).cuboid(7.0F, -0.56F, 0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 18).cuboid(7.0F, -0.56F, 1.25F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -0.75F, 0.5F, 0.0F, -0.2182F, 0.0F));

		claw2.addChild("claw4_r1", ModelPartBuilder.create().uv(5, 25).cuboid(1.3F, -0.8F, -4.0F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -0.75F, 2.5F, 0.0F, -0.8279F, 0.1309F));

		ModelPartData annetna = body.addChild("annetna", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 2.0F));

		ModelPartData antennaantenna = annetna.addChild("antennaantenna", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -2.0F));

		ModelPartData antenna = antennaantenna.addChild("antenna", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 5.25F, -10.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData antenna1 = antenna.addChild("antenna1", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		antenna1.addChild("antenna1_r1", ModelPartBuilder.create().uv(22, 28).mirrored().cuboid(-1.5F, -2.35F, -10.1F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0F, 0.0F, 1.0F, -0.109F, -0.3325F, -1.3515F));

		ModelPartData antennaantennatwo = annetna.addChild("antennaantennatwo", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -2.0F));

		ModelPartData antennatwo = antennaantennatwo.addChild("antennatwo", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 5.25F, -10.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData antenna2 = antennatwo.addChild("antenna2", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 0.0F, 1.0F));

		antenna2.addChild("antenna2_r1", ModelPartBuilder.create().uv(22, 30).cuboid(-2.5F, -2.35F, -10.1F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 0.0F, 0.0F, -0.109F, 0.3325F, 1.3515F));

		ModelPartData leg = lobster.addChild("leg", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData leg1 = leg.addChild("leg1", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 0.0F, 0.0F));

		leg1.addChild("leg1_r1", ModelPartBuilder.create().uv(0, 20).cuboid(3.75F, -0.125F, -3.75F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

		ModelPartData leg2 = leg.addChild("leg2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leg2.addChild("leg2_r1", ModelPartBuilder.create().uv(8, 20).cuboid(1.925F, -0.125F, -3.75F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

		ModelPartData leg3 = leg.addChild("leg3", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, 0.0F, 0.0F));

		leg3.addChild("leg3_r1", ModelPartBuilder.create().uv(0, 16).cuboid(0.1F, -0.125F, -3.75F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

		ModelPartData leg4 = leg.addChild("leg4", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 0.0F, 0.0F));

		leg4.addChild("leg6_r1", ModelPartBuilder.create().uv(8, 12).cuboid(3.75F, -0.125F, 0.75F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.0F, 1.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData leg5 = leg.addChild("leg5", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		leg5.addChild("leg5_r1", ModelPartBuilder.create().uv(0, 12).cuboid(1.925F, -0.125F, 0.75F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 1.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData leg6 = leg.addChild("leg6", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, 0.0F, 0.0F));

		leg6.addChild("leg4_r1", ModelPartBuilder.create().uv(8, 16).cuboid(0.1F, -0.125F, 0.75F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.0F, 1.0F, -0.3054F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(ShellfishRenderState<LobsterEntity.Variant> entity) {
		super.setAngles(entity);
		if (!entity.touchingWater) {
			this.animateWalking(MoreShellfishAnimations.LOBSTER_WALK, entity.limbSwingAnimationProgress, entity.limbSwingAmplitude, 12, 15f);
		}
		this.animate(entity.moveAnimationState, MoreShellfishAnimations.LOBSTER_WALK, entity.age, 2f);
		this.animate(entity.idleAnimationState, MoreShellfishAnimations.LOBSTER_IDLE, entity.age, 1f);
	}
}
