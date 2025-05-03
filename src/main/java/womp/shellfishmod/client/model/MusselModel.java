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
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.animations.MoreShellfishAnimations;

public class MusselModel extends SinglePartEntityModel<MusselEntity> {

    private final ModelPart mussel;

	public MusselModel(ModelPart root) {
		this.mussel = root.getChild("mussel");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mussel = modelPartData.addChild("mussel", ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, 24.0F, 0.0F));

		mussel.addChild("topshell", ModelPartBuilder.create().uv(0, 7).cuboid(-2.25F, -2.0F, -3.5F, 4.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.75F, 0.0F, 0.0F));

		mussel.addChild("bottomshell", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.0F, -3.5F, 4.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		mussel.addChild("innards", ModelPartBuilder.create().uv(0, 14).cuboid(-1.5F, -1.5F, -2.5F, 3.0F, 1.0F, 4.99F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -0.25F, 0.0F));

		mussel.addChild("bodyend", ModelPartBuilder.create().uv(14, 0).cuboid(-1.5F, -1.5F, 2.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(MusselEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.moveAnimationState, MoreShellfishAnimations.MUSSEL_MOVE, ageInTicks, 1f);
		this.updateAnimation(entity.idleAnimationState, MoreShellfishAnimations.MUSSEL_IDLE, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		mussel.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    @Override
    public ModelPart getPart() {
       return mussel;
    }
    
}
