package com.ofbizian.service.buildconfig.dev;

import java.util.HashMap;
import java.util.Map;

import io.fabric8.kubernetes.api.model.KubernetesListBuilder;

public class ImageStreamKubernetesModelProcessor {

    public void on(KubernetesListBuilder builder) {
		        builder.addNewImageStreamItem()
			        .withNewMetadata()
			            .withName("service-template")
			            .withLabels(getLabels())
			        .endMetadata()
			        .withNewSpec()
			            .withDockerImageRepository("")
			        .endSpec()
			        .endImageStreamItem()
		       .build();
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
