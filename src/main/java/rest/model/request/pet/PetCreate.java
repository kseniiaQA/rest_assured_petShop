package rest.model.request.pet;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.List;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetCreate {

    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public PetCreate(PetCreate petCreate) {
        this.id = petCreate.id;
        this.category = petCreate.category;
        this.name = petCreate.name;
        this.photoUrls = petCreate.photoUrls;
        this.tags = petCreate.tags;
        this.status = petCreate.status;
    }



    public static PetCreate defaultOf() {
        return new PetCreate(
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
        private long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private long id;
        private String name;
    }
}