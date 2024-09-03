import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDates {
    private String checkin;
    private String checkout;

    // Constructor para inicializar bookingdates en la creación de la instancia
    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }
}
