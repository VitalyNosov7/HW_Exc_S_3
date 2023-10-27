public class CustomDataTypeException extends CustomInputException{

    private String dataName;
	
    public CustomDataTypeException(String message, String dataName) {
        super(message);
        this.dataName = dataName;
    }

    public String getDataName() {
        return dataName;
    }
}