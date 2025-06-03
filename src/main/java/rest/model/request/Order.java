package rest.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Accessors(chain = true)
@Data
@AllArgsConstructor
public class Order {

    private long id;
    private long petId;
    private int quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private OffsetDateTime shipDate;

    private String status;
    private boolean complete;

    public Order() {
    }

    public static Order defaultOf() {
        return new Order(
                0L,
                0L,
                0,
                OffsetDateTime.parse("2025-06-03T12:26:59.478Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                "placed",
                true
        );
    }
}
