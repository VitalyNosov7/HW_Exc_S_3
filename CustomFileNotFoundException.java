public class CustomFileNotFoundException extends Exception{

    private final String fileName;

    public String getFileName() {
        return fileName;
    }

    public CustomFileNotFoundException(String message, String fileName) {
        super(message);
        this.fileName = fileName;
    }
}