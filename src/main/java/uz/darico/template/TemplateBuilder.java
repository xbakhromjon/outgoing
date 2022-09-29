package uz.darico.template;

import uz.darico.contentFile.ContentFile;

public class TemplateBuilder {
    private Long workPlaceID;
    private Long userID;
    private String content;
    private String createdPurpose;

    public TemplateBuilder setWorkPlaceID(Long workPlaceID) {
        this.workPlaceID = workPlaceID;
        return this;
    }

    public TemplateBuilder setUserID(Long userID) {
        this.userID = userID;
        return this;
    }

    public TemplateBuilder setFile(String content) {
        this.content = content;
        return this;
    }

    public TemplateBuilder setCreatedPurpose(String createdPurpose) {
        this.createdPurpose = createdPurpose;
        return this;
    }

    public Template build() {
        return new Template(workPlaceID, userID, content, createdPurpose);
    }
}