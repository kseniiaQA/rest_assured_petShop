package rest.model.response.order;

import lombok.*;
import lombok.experimental.Accessors;
import rest.model.request.Order;
import java.time.OffsetDateTime;


@Accessors(chain = true)
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse extends Order {

    private long id;
    private long petId;

    @EqualsAndHashCode.Exclude
    private int quantity;
    private OffsetDateTime shipDate;
    private String status;
    private boolean complete;

}
