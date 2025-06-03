package rest.model.request;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.List;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public Pet(Pet pet) {
        this.id = pet.id;
        this.category = pet.category;
        this.name = pet.name;
        this.photoUrls = pet.photoUrls;
        this.tags = pet.tags;
        this.status = pet.status;
    }

    public static Pet defaultOf() {
        return new Pet(
                0,
                new Category(0, "string"),
                "doggie",
                List.of("https://petstore.swagger.io/#/"),
                List.of(new Tag(0, "string")),
                "available"
        );
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        private Integer id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private Integer id;
        private String name;
    }
}
