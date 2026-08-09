package womp.shellfishmod.client.renderer;

import womp.shellfishmod.client.model.MusselModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.MusselEntity;
import womp.shellfishmod.entity.MusselEntity.Variant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import womp.shellfishmod.client.ShellfishClient;

public class MusselRenderer extends MobRenderer<MusselEntity, ShellfishRenderState<MusselEntity.Variant>, MusselModel> {
    
    public MusselRenderer(Context ctx) {
        super(ctx, new MusselModel(ctx.bakeLayer(ShellfishClient.MUSSEL_MODEL)), 0.3f);
    }

    @Override
    public Identifier getTextureLocation(ShellfishRenderState<MusselEntity.Variant> var1) {
        int variant = var1.variant.getIndex();
        return Identifier.fromNamespaceAndPath("shellfish", "textures/entity/mussel/mussel_" + variant + ".png");
    }

    @Override
    public void submit(ShellfishRenderState<MusselEntity.Variant> entity, PoseStack poseStack,
            SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int variant = entity.variant.getIndex();                  
        if (variant == 1) entity.shellfish.scale(poseStack, 0.95f, 0.65f, 0.4f);
        else if (variant == 2) entity.shellfish.scale(poseStack, 1.1f, 0.8f, 0.4f);
        else if (variant == 3) entity.shellfish.scale(poseStack, 0.55f, 0.3f);
        else entity.shellfish.scale(poseStack, 0.8f, 0.4f);

        super.submit(entity, poseStack, orderedRenderCommandQueue, cameraRenderState);
    }

    @Override
    public ShellfishRenderState<Variant> createRenderState() {
        return new ShellfishRenderState<Variant>();
    }

    @Override
    public void extractRenderState(MusselEntity mussel, ShellfishRenderState<Variant> musselState, float f) {
        super.extractRenderState(mussel, musselState, f);
        musselState.variant = mussel.getVariant();
        musselState.shellfish = mussel;
        musselState.idleAnimationState.copyFrom(mussel.idleAnimationState);
        musselState.moveAnimationState.copyFrom(mussel.moveAnimationState);
    }
}
