package womp.shellfishmod.client.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import womp.shellfishmod.entity.SeaUrchinEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class SeaUrchinModel extends SinglePartEntityModel<SeaUrchinEntity> {

    private final ModelPart sea_urchin;
	
	public SeaUrchinModel(ModelPart root) {
		this.sea_urchin = root.getChild("sea_urchin");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sea_urchin = modelPartData.addChild("sea_urchin", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData sea_urchin1 = sea_urchin.addChild("sea_urchin1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData spikes = sea_urchin1.addChild("spikes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData spikegroup1 = spikes.addChild("spikegroup1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		spikegroup1.addChild("spike4_r1", ModelPartBuilder.create().uv(0, 7).cuboid(0.0F, -1.0F, -2.75F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		spikegroup1.addChild("spike3_r1", ModelPartBuilder.create().uv(4, 7).cuboid(0.0F, -0.25F, -3.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.2217F, 0.0F, 0.0F));

		spikegroup1.addChild("spike2_r1", ModelPartBuilder.create().uv(0, 8).cuboid(0.0F, -0.25F, 1.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.2217F, 0.0F, 0.0F));

		spikegroup1.addChild("spike1_r1", ModelPartBuilder.create().uv(8, 3).cuboid(0.0F, -1.0F, 0.75F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData spikegroup2 = spikes.addChild("spikegroup2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		spikegroup2.addChild("spike4_r2", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, -1.5F, -3.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2182F, 0.7854F, 0.0F));

		spikegroup2.addChild("spike3_r2", ModelPartBuilder.create().uv(0, 3).cuboid(0.0F, -1.5F, 1.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2182F, -0.7854F, 0.0F));

		spikegroup2.addChild("spike2_r2", ModelPartBuilder.create().uv(0, 6).cuboid(0.0F, -1.5F, -3.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2182F, -0.7854F, 0.0F));

		spikegroup2.addChild("spike1_r2", ModelPartBuilder.create().uv(4, 6).cuboid(0.0F, -1.5F, 1.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2182F, 0.7854F, 0.0F));

		ModelPartData spikegroup3 = spikes.addChild("spikegroup3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData spikegroupiii = spikegroup3.addChild("spikegroupiii", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		spikegroupiii.addChild("spike4_r3", ModelPartBuilder.create().uv(4, 3).cuboid(0.0F, -1.0F, -2.75F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		spikegroupiii.addChild("spike3_r3", ModelPartBuilder.create().uv(4, 4).cuboid(0.0F, -0.25F, -3.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.2217F, 0.0F, 0.0F));

		spikegroupiii.addChild("spike2_r3", ModelPartBuilder.create().uv(0, 5).cuboid(0.0F, -0.25F, 1.5F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.2217F, 0.0F, 0.0F));

		spikegroupiii.addChild("spike1_r3", ModelPartBuilder.create().uv(4, 5).cuboid(0.0F, -1.0F, 0.75F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData spikegroup4 = spikes.addChild("spikegroup4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData spike1 = spikegroup4.addChild("spike1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legit2 = spike1.addChild("legit2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		legit2.addChild("spike1_r4", ModelPartBuilder.create().uv(8, 5).cuboid(0.0F, -0.75F, 2.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.75F, -1.0F, -1.2217F, 0.0F, 0.0F));

		spike1.addChild("fakeinvis2", ModelPartBuilder.create().uv(0, 16).cuboid(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData spike2 = spikegroup4.addChild("spike2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legit = spike2.addChild("legit", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		legit.addChild("spike2_r4", ModelPartBuilder.create().uv(8, 6).cuboid(0.0F, -0.75F, -3.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.75F, 1.0F, 1.2217F, 0.0F, 0.0F));

		spike2.addChild("fakeinvis", ModelPartBuilder.create().uv(0, 16).cuboid(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData spike3 = spikegroup4.addChild("spike3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legitone = spike3.addChild("legitone", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		legitone.addChild("spike3_r4", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -0.75F, 2.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.75F, -1.0F, -1.2217F, 0.0F, 0.0F));

		spike3.addChild("fakeinvisone", ModelPartBuilder.create().uv(0, 16).cuboid(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData spike4 = spikegroup4.addChild("spike4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legitone2 = spike4.addChild("legitone2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		legitone2.addChild("spike4_r4", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -0.75F, -3.5F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.75F, 1.0F, 1.2217F, 0.0F, 0.0F));

		spike4.addChild("fakeinvisone2", ModelPartBuilder.create().uv(0, 16).cuboid(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void setAngles(SeaUrchinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.moveAnimationState, ShellfishAnimations.SEA_URCHIN_WALK, ageInTicks, 1f);
		this.updateAnimation(entity.idleAnimationState, ShellfishAnimations.SEA_URCHIN_IDLE, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		sea_urchin.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    @Override
	public ModelPart getPart() {
		return sea_urchin;
	}
}
