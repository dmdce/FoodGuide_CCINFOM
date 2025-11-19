public class ReservationPriceData {
    private int reservationId;
    private double initialPrice;

    public ReservationPriceData(int reservationId, double initialPrice) {
        this.reservationId = reservationId;
        this.initialPrice = initialPrice;
    }

    public int getReservationId() {
        return reservationId;
    }

    public double getInitialPrice() {
        return initialPrice;
    }
}
