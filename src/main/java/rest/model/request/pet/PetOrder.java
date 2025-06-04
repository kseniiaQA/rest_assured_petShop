package rest.model.request.pet;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.List;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetOrder {

    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public PetOrder(PetOrder petOrder) {
        this.id = petOrder.id;
        this.category = petOrder.category;
        this.name = petOrder.name;
        this.photoUrls = petOrder.photoUrls;
        this.tags = petOrder.tags;
        this.status = petOrder.status;
    }



    public static PetOrder defaultOf() {
        return new PetOrder(
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