package com.ofbizian.service.buildconfig.dev;

import java.util.HashMap;
import java.util.Map;

import io.fabric8.kubernetes.api.model.KubernetesListBuilder;
import io.fabric8.kubernetes.api.model.ObjectReference;
import io.fabric8.openshift.api.model.BuildTriggerPolicy;
import io.fabric8.openshift.api.model.ImageChangeTrigger;

public class BuildConfigKubernetesModelProcessor {

    public void on(KubernetesListBuilder builder) {
        builder.addNewBuildConfigItem()
                .withNewMetadata()
                    .withName("service-template")
                    .withLabels(getLabels())
                .endMetadata()
                .withNewSpec()
                    .withTriggers(getTriggers())
                    .withNewSource()
                        .withNewGit()
                            .withUri("https://github.com/bibryam/service-build/")
                            .withRef("custom-build")
                        .endGit()
                        .withType("Git")
                    .endSource()
                    .withNewStrategy()
                        .withNewSourceStrategy()
                            .withNewFrom()
                                .withKind("ImageStreamTag")
                                .withName("fis-java-openshift:latest")
                                .withNamespace("openshift")
                            .endFrom()
                            .withIncremental(true)
                            .addNewEnv().withName("NEXUS_URL").withValue("${NEXUS_URL}").endEnv()
                            .addNewEnv().withName("REPOSITORY_NAME").withValue("${REPOSITORY_NAME}").endEnv()
                            .addNewEnv().withName("GROUP_ID").withValue("${GROUP_ID}").endEnv()
                            .addNewEnv().withName("ARTIFACT_ID").withValue("${ARTIFACT_ID}").endEnv()
                            .addNewEnv().withName("ARTIFACT_VERSION").withValue("${ARTIFACT_VERSION}").endEnv()
                            .addNewEnv().withName("CLASSIFIER").withValue("${CLASSIFIER}").endEnv()
                            .addNewEnv().withName("EXTENSION").withValue("${EXTENSION}").endEnv()
                        .endSourceStrategy()
                        .withType("Source")
                    .endStrategy()
                    .withNewOutput()
                        .withNewTo()
                            .withKind("ImageStreamTag")
                            .withName("service-template:latest")
                        .endTo()
                    .endOutput()
                .endSpec()
            .endBuildConfigItem()
            .build();
    }

    private BuildTriggerPolicy getTriggers() {
        ObjectReference from = new ObjectReference();
        from.setName("fis-java-openshift:latest");
        from.setKind("ImageStreamTag");
        from.setNamespace("openshift");

        ImageChangeTrigger imageChangeTrigger = new ImageChangeTrigger();
        imageChangeTrigger.setFrom(from);

        BuildTriggerPolicy policy = new BuildTriggerPolicy();
        policy.setType("ImageChange");
        policy.setImageChange(imageChangeTrigger);

        return policy;
    }

    private Map<String, String> getLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("app", "service-template");
        labels.put("artifact", "service-template");
        labels.put("version", "1.0-SNAPSHOT");
        labels.put("group", "com.ofbizian");

        return labels;
    }
}
