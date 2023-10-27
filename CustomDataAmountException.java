public class CustomDataAmountException extends CustomInputException{

    private int dataLength;

    public int getDataLength() {
        return dataLength;
    }

    public CustomDataAmountException(String message, int dataLength) {
        super(message);
        this.dataLength = dataLength;
    }
}