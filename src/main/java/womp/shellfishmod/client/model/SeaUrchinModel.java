package womp.shellfishmod.client.model;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import womp.shellfishmod.client.states.ShellfishRenderState;
import womp.shellfishmod.entity.SeaUrchinEntity;
import womp.shellfishmod.entity.animations.ShellfishAnimations;

public class SeaUrchinModel extends EntityModel<ShellfishRenderState<SeaUrchinEntity.Variant>> {

    private final KeyframeAnimation moveAnimation;
    private final KeyframeAnimation idleAnimation;

    public SeaUrchinModel(ModelPart root) {
        super(root);
        this.moveAnimation = ShellfishAnimations.SEA_URCHIN_WALK.bake(root);
        this.idleAnimation = ShellfishAnimations.SEA_URCHIN_IDLE.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition sea_urchin = modelPartData.addOrReplaceChild("sea_urchin", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition sea_urchin1 = sea_urchin.addOrReplaceChild("sea_urchin1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spikes = sea_urchin1.addOrReplaceChild("spikes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spikegroup1 = spikes.addOrReplaceChild("spikegroup1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        spikegroup1.addOrReplaceChild("spike4_r1", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -1.0F, -2.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        spikegroup1.addOrReplaceChild("spike3_r1", CubeListBuilder.create().texOffs(4, 7).addBox(0.0F, -0.25F, -3.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.0F, 0.0F));

        spikegroup1.addOrReplaceChild("spike2_r1", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -0.25F, 1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2217F, 0.0F, 0.0F));

        spikegroup1.addOrReplaceChild("spike1_r1", CubeListBuilder.create().texOffs(8, 3).addBox(0.0F, -1.0F, 0.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition spikegroup2 = spikes.addOrReplaceChild("spikegroup2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        spikegroup2.addOrReplaceChild("spike4_r2", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, -1.5F, -3.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.7854F, 0.0F));

        spikegroup2.addOrReplaceChild("spike3_r2", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -1.5F, 1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, -0.7854F, 0.0F));

        spikegroup2.addOrReplaceChild("spike2_r2", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -1.5F, -3.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, -0.7854F, 0.0F));

        spikegroup2.addOrReplaceChild("spike1_r2", CubeListBuilder.create().texOffs(4, 6).addBox(0.0F, -1.5F, 1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.7854F, 0.0F));

        PartDefinition spikegroup3 = spikes.addOrReplaceChild("spikegroup3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spikegroupiii = spikegroup3.addOrReplaceChild("spikegroupiii", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        spikegroupiii.addOrReplaceChild("spike4_r3", CubeListBuilder.create().texOffs(4, 3).addBox(0.0F, -1.0F, -2.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        spikegroupiii.addOrReplaceChild("spike3_r3", CubeListBuilder.create().texOffs(4, 4).addBox(0.0F, -0.25F, -3.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.0F, 0.0F));

        spikegroupiii.addOrReplaceChild("spike2_r3", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -0.25F, 1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2217F, 0.0F, 0.0F));

        spikegroupiii.addOrReplaceChild("spike1_r3", CubeListBuilder.create().texOffs(4, 5).addBox(0.0F, -1.0F, 0.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition spikegroup4 = spikes.addOrReplaceChild("spikegroup4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spike1 = spikegroup4.addOrReplaceChild("spike1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legit2 = spike1.addOrReplaceChild("legit2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        legit2.addOrReplaceChild("spike1_r4", CubeListBuilder.create().texOffs(8, 5).addBox(0.0F, -0.75F, 2.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.75F, -1.0F, -1.2217F, 0.0F, 0.0F));

        spike1.addOrReplaceChild("fakeinvis2", CubeListBuilder.create().texOffs(0, 16).addBox(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spike2 = spikegroup4.addOrReplaceChild("spike2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legit = spike2.addOrReplaceChild("legit", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        legit.addOrReplaceChild("spike2_r4", CubeListBuilder.create().texOffs(8, 6).addBox(0.0F, -0.75F, -3.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.75F, 1.0F, 1.2217F, 0.0F, 0.0F));

        spike2.addOrReplaceChild("fakeinvis", CubeListBuilder.create().texOffs(0, 16).addBox(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spike3 = spikegroup4.addOrReplaceChild("spike3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legitone = spike3.addOrReplaceChild("legitone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        legitone.addOrReplaceChild("spike3_r4", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -0.75F, 2.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.75F, -1.0F, -1.2217F, 0.0F, 0.0F));

        spike3.addOrReplaceChild("fakeinvisone", CubeListBuilder.create().texOffs(0, 16).addBox(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spike4 = spikegroup4.addOrReplaceChild("spike4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legitone2 = spike4.addOrReplaceChild("legitone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        legitone2.addOrReplaceChild("spike4_r4", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.75F, -3.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.75F, 1.0F, 1.2217F, 0.0F, 0.0F));

        spike4.addOrReplaceChild("fakeinvisone2", CubeListBuilder.create().texOffs(0, 16).addBox(0.25F, -0.75F, -0.5F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 16, 16);
    }

    @Override
    public void setupAnim(ShellfishRenderState<SeaUrchinEntity.Variant> entity) {
        super.setupAnim(entity);
        this.moveAnimation.apply(entity.moveAnimationState, entity.ageInTicks, 1f);
        this.idleAnimation.apply(entity.idleAnimationState, entity.ageInTicks, 1f);
    }
}
