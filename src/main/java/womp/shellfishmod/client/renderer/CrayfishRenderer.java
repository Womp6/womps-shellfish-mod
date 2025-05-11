package womp.shellfishmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import womp.shellfishmod.client.ShellfishClient;
import womp.shellfishmod.client.model.CrayfishModel;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.CrayfishEntity;

public class CrayfishRenderer extends MobRenderer<CrayfishEntity, ShellfishRenderState<CrayfishEntity.Variant>,  CrayfishModel> {

    public CrayfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrayfishModel(renderManager.bakeLayer(ShellfishClient.CRAYFISH_MODEL)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellfishRenderState<CrayfishEntity.Variant> animatable) {
        int variant = animatable.variant.getIndex();
        if (animatable.customName != null && "Supercrayfish".equals(animatable.customName.getString())) {
            return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/crayfish/supercrayfish.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath("shellfish", "textures/entity/crayfish/crayfish_" + variant + ".png");
        }
    }

    @Override
    public void render(ShellfishRenderState<CrayfishEntity.Variant> entity, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        if(!entity.isBaby) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }

        super.render(entity, poseStack, bufferSource, packedLight);
    }

    @Override
    public ShellfishRenderState<CrayfishEntity.Variant> createRenderState() {
        return new ShellfishRenderState<>();
    }

    @Override
    public void extractRenderState(CrayfishEntity crayfish, ShellfishRenderState<CrayfishEntity.Variant> crayfishState, float f) {
        super.extractRenderState(crayfish, crayfishState, f);
        crayfishState.variant = crayfish.getVariant();
        crayfishState.idleAnimationState.copyFrom(crayfish.idleAnimationState);
        crayfishState.moveAnimationState.copyFrom(crayfish.moveAnimationState);
    }
}
